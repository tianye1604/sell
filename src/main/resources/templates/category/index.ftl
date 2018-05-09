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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>类目名称</label>
                            <input name="categoryName" value="${(category.categoryName)!''}" type="text"
                                   class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label>类目编号</label>
                            <input name="categoryType" value="${(category.categoryType)!''}" type="number"
                                   class="form-control"/>
                        </div>

                        <input hidden name="categoryId" type="number" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>