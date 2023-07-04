<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="product-details spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6">
                <div class="product__details__pic">
                    <div class="product__details__pic__item">
                        <img
                                class="product__details__pic__item--large"
                                src="img/products/${data['product'].images[0].image_path}"
                                alt=""
                        />
                    </div>
                    <div class="product__details__pic__slider owl-carousel">
                        <c:forEach
                                var="image" items="${data['product'].images}"
                        >
                            <img
                                    data-imgbigurl="img/products/${image.image_path}"
                                    src="img/products/${image.image_path}" alt=""
                                    alt=""
                            />
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6" id="product-detail">
                <div class="product__details__text" >
                    <h3>${data['product'].name}</h3>
                    <div class="product__details__rating">
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                        <i class="fa fa-star"></i>
                    </div>
                    <div class="product__details__price">$${data['product'].price}</div>
                    <p>
                        ${data['product'].description}
                    </p>
                    <div class="product__details__quantity">
                        <div class="quantity">
                            <div class="pro-qty">
                                <input type="text" value="1"/>
                            </div>
                        </div>
                    </div>
                    <a href="#" class="primary-btn">ADD TO CARD</a>
                    <a href="#" class="heart-icon"
                    ><span class="icon_heart_alt"></span
                    ></a>
                    <ul>
                        <li>
                            <b>Share on</b>
                            <div class="share">
                                <a href="#"><i class="fa fa-facebook"></i></a>
                                <a href="#"><i class="fa fa-twitter"></i></a>
                                <a href="#"><i class="fa fa-instagram"></i></a>
                                <a href="#"><i class="fa fa-pinterest"></i></a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="product__details__tab">
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="nav-item">
                            <a
                                    class="nav-link active"
                                    data-toggle="tab"
                                    href="#tabs-1"
                                    role="tab"
                                    aria-selected="true"
                            >Description</a
                            >
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tabs-1" role="tabpanel">
                            <div class="product__details__tab__desc">
                                <h6>Products Infomation</h6>
                                <p>
                                    ${data['product'].description}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script>
    $('.product__details__pic__slider img').on('click', function () {

        let imgurl = $(this).data('imgbigurl');
        let bigImg = $('.product__details__pic__item--large').attr('src');
        if (imgurl != bigImg) {
            $('.product__details__pic__item--large').attr({
                src: imgurl
            });
        }
    });
    // Retrieve the element ID from the request attribute

    // Scroll to the element with the specified ID
    document.getElementById("product-detail").scrollIntoView();
</script>