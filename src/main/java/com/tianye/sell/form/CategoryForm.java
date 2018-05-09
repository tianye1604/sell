package com.tianye.sell.form;

import lombok.Data;

/**
 * @Author:tianye
 * @Description: 类目表单
 * @Date: 17:49 2018/5/9/009
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /**
     * 类目名字
     */
    private String categoryName;

    /**
     * 类目编号
     */
    private Integer categoryType;
}
