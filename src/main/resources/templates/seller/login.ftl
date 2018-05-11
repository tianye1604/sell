<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/login">
                        <div class="form-group">
                            <label>openid</label>
                            <input name="openid" value="${openid!''}" type="text" class="form-control"/>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>