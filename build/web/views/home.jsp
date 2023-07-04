<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="featured spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="section-title">
                    <h2>Latest Shoes</h2>
                </div>
            </div>
        </div>
        <div class="row featured__filter">
            <c:forEach var="item" items="${data['latestProducts']}">
                <div class="col-lg-3 col-md-4 col-sm-6 mix oranges fresh-meat">
                    <div class="featured__item">
                        <div class="featured__item__pic set-bg" data-setbg="img/products/${item.images[0].image_path}">
                            <ul class="featured__item__pic__hover">
                                <li><a href="./product-detail?id=${item.id}"><i class="fa fa-shopping-cart"></i></a></li>
                            </ul>
                        </div>
                        <div class="featured__item__text">
                            <h6><a href="./product-detail?id=${item.id}">${item.name}</a></h6>
                            <h5>$${item.price}</h5>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</section>