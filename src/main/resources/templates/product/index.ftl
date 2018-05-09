<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label for="exampleInputEmail1">名称</label>
                            <input name="productName" value="${(productInfo.productName)!''}" type="text"
                                   class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">价格</label>
                            <input name="productPrice" value="${(productInfo.productPrice)!''}" type="text"
                                   class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">库存</label>
                            <input name="productStock" value="${(productInfo.productStock)!''}" type="number"
                                   class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">描述</label>
                            <input name="productDescription" value="${(productInfo.productDescription)!''}" type="text"
                                   class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">图片</label>
                            <img width="100" height="100" src="${(productInfo.productIcon)!''}">
                            <input name="productIcon" value="${(productInfo.productIcon)!''}" type="text"
                                   class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">类目</label>
                            <select name="categoryType" class="form-control">
                                <#list productCategoryList as categroy>
                                    <option value="${categroy.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == categroy.categoryType>
                                            selected
                                        </#if>
                                    >${categroy.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden name="productId" type="text" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>