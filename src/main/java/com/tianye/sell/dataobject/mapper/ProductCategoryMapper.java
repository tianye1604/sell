package com.tianye.sell.dataobject.mapper;

import com.tianye.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @Author:tianye
 * @Description:
 * @Date: 16:53 2018/5/15/015
 */
public interface ProductCategoryMapper {


    /**
     * xml配置方式查询
     *
     * @param categoryType
     * @return
     */
    ProductCategory selectByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_name", property = "categoryName"),
    })
    ProductCategory findByCategoryType(Integer categoryType);


    @Insert("insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR}, #{categoryType,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

}
