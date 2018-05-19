package com.tianye.sell.controller;

import com.tianye.sell.VO.ProductInfoVO;
import com.tianye.sell.VO.ProductVO;
import com.tianye.sell.VO.ResultVO;
import com.tianye.sell.dataobject.ProductCategory;
import com.tianye.sell.dataobject.ProductInfo;
import com.tianye.sell.service.CategoryService;
import com.tianye.sell.service.ProductService;
import com.tianye.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @Author:tianye
* @Description: 商品信息
* @Date: 14:59 2018/4/3/003
*/

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "#sellerId", condition = "#sellerId.length() > 3", unless = "#result.getCode() != 0")
    public ResultVO list(@RequestParam("sellerId") String sellerId) {
        //1. 查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. 查询类目(一次性查询)
        //传统方法
//        Set<Integer> typeSet = new HashSet<>();
//        for (ProductInfo productInfo : productInfoList) {
//            typeSet.add(productInfo.getCategoryType());
//        }
        //精简方法(java8, lambda)
//        List<Integer> typeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
//        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(typeList);

        //使用set替代list
        Set<Integer> typeSet = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toSet());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(typeSet);


        //3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory category : categoryList){
            ProductVO productVo = new ProductVO();
            productVo.setCategoryName(category.getCategoryName());
            productVo.setCategoryType(category.getCategoryType());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType() == productVo.getCategoryType()){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVo.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVo);
        }
        return ResultVOUtil.success(productVOList);
    }
}
