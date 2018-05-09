package com.tianye.sell.controller;

import com.tianye.sell.dataobject.ProductCategory;
import com.tianye.sell.dataobject.ProductInfo;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.exception.SellException;
import com.tianye.sell.form.ProductForm;
import com.tianye.sell.service.CategoryService;
import com.tianye.sell.service.ProductService;
import com.tianye.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author:tianye
 * @Description: 卖家商品管理
 * @Date: 12:04 2018/5/9/009
 */

@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return new ModelAndView("product/list", map);
    }


    /**
     * 商品下架
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {

        try {
            productService.offSale(productId);
        } catch (SellException e) {
            log.error("【商品下架】发生异常，{}", e.getMessage());
            map.put("msg", ResultEnum.PRODUCT_DOWN_ERROR.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.PRODUCT_DOWN_SUCCESS.getMessage());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }


    /**
     * 商品上架
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {

        try {
            productService.onSale(productId);
        } catch (SellException e) {
            log.error("【商品上架】发生异常，{}", e.getMessage());
            map.put("msg", ResultEnum.PRODUCT_UP_ERROR.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.PRODUCT_UP_SUCCESS.getMessage());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品展示
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {

        ProductInfo productInfo = new ProductInfo();
        if (!StringUtils.isEmpty(productId)) {
            productInfo = productService.findOne(productId);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("productInfo", productInfo);
        map.put("productCategoryList", productCategoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 商品新增/更新
     *
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        String productId = productForm.getProductId() == null ? "" : productForm.getProductId();
        String url = "/sell/seller/product/index?productId=" + productId;
        map.put("url", url);
        if (bindingResult.hasErrors()) {
            log.error("【商品新增/更新】参数信息不正确，productForm:{}", productForm);
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(productId)) {
                productInfo = productService.findOne(productId);
            } else {
                productId = KeyUtil.getUniqueKey();
                productForm.setProductId(productId);
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        } catch (Exception e) {
            log.error("【商品新增/更新】失败，{}", e.getMessage());
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("msg", ResultEnum.PRODUCT_SAVE_SUCCESS.getMessage());
        return new ModelAndView("common/success", map);
    }

}
