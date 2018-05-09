package com.tianye.sell.controller;

import com.tianye.sell.dataobject.ProductCategory;
import com.tianye.sell.enums.ResultEnum;
import com.tianye.sell.form.CategoryForm;
import com.tianye.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description: 卖家端类目管理
 * @Date: 17:32 2018/5/9/009
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     *
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 类目展示
     *
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {

        ProductCategory category = new ProductCategory();
        if (!StringUtils.isEmpty(categoryId)) {
            category = categoryService.findOne(categoryId);
        }
        map.put("category", category);

        return new ModelAndView("category/index", map);
    }

    /**
     * 类目新增/更新
     *
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        String url = "/sell/seller/category/index";
        if (categoryForm.getCategoryId() != null) {
            url = url + "?categoryId=" + categoryForm.getCategoryId();
        }
        map.put("url", url);
        if (bindingResult.hasErrors()) {
            log.error("【类目新增/更新】参数信息不正确，categoryForm:{}", categoryForm);
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView("common/error", map);
        }

        ProductCategory category = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                category = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, category);
            categoryService.save(category);
        } catch (Exception e) {
            log.error("【类目新增/更新】失败，{}", e.getMessage());
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/category/list");
        map.put("msg", ResultEnum.CATEGORY_SAVE_SUCCESS.getMessage());
        return new ModelAndView("common/success", map);
    }
}
