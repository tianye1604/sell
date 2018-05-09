<html>
<head>
    <meta charset="UTF-8"/>
    <title>卖家订单列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>地址</th>
                    <th>订单总金额</th>
                    <th>订单状态</th>
                    <th>支付状态</th>
                    <th>创建时间</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.getOrderStatusEnum().message}</td>
                        <td>${orderDTO.getPayStatusEnum().message}</td>
                        <td>${orderDTO.createTime}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                <tr>
                    <th>商品id</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                    <#list orderDTO.orderDetailList as order>
                    <tr>
                        <td>${order.productId}</td>
                        <td>${order.productName}</td>
                        <td>${order.productPrice}</td>
                        <td>${order.productQuantity}</td>
                        <td>${order.productPrice * order.productQuantity}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <#if orderDTO.getOrderStatusEnum().message == "新下单">
            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button"  class="btn btn-default btn-primary">完结订单</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
            </#if>
        </div>
    </div>
</div>
</body>

</html>