<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
    .page-active {
        background: orange;
        border-color: orange;
        color: #ffffff !important;
    }
</style>

<section class="product spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-5">
                <div class="sidebar">
                    <div class="sidebar__item">
                        <h4>Brands</h4>
                        <ul>
                            <c:forEach var="brand" items="${data['brands']}">
                                <li>
                                    <a href="./products?page=${data['currentPage']}&brand=${brand.brand_id}">${brand.brand_name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="sidebar__item">
                        <div class="latest-product__text">
                            <h4>Latest Products</h4>
                            <div class="latest-product__slider owl-carousel">
                                <c:forEach var="item" items="${data['latestProducts']}">
                                    <div class="latest-prdouct__slider__item">
                                        <a href="./product-detail?id=${item.id}" class="latest-product__item">
                                            <div class="latest-product__item__pic">
                                                <img src="img/products/${item.images[0].image_path}" alt=""/>
                                            </div>
                                            <div class="latest-product__item__text">
                                                <h6>${item.name}</h6>
                                                <span>$${item.price}</span>
                                            </div>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-9 col-md-7">
                <div class="filter__item">
                    <div class="row">
                        <div class="col-lg-4 col-md-5">
                            <div class="filter__sort" id="filter-wrapper">
                                <span>Sort Price By</span>
                                <select id="filter-price">
                                    <option></option>
                                    <option value="ASC">Asc</option>
                                    <option value="DESC">Desc</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach var="item" items="${data['products']}">
                        <div class="col-lg-4 col-md-6 col-sm-6">
                            <div class="product__item">
                                <div
                                        class="product__item__pic set-bg"
                                        data-setbg="img/products/${item.images[0].image_path}"
                                >
                                    <ul class="product__item__pic__hover">
                                        <li>
                                            <a href="./product-detail?id=${item.id}"><i class="fa fa-shopping-cart"></i></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="product__item__text">
                                    <h6><a href="./product-detail?id=${item.id}">${item.name}</a></h6>
                                    <h5>$${item.price}</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="product__pagination">
                    <c:forEach var="item" items="${data['links']}">
                        ${item}
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script !src="">
    $(document).ready(function () {
        // Get the "price" parameter value from the URL
        var urlParams = new URLSearchParams(window.location.search);
        var priceParam = urlParams.get("price");

        // Set the default selected option based on the "price" parameter value
        if (priceParam === "ASC") {
            $('#filter-price').val("ASC");
        } else if (priceParam === "DESC") {
            $('#filter-price').val("DESC");
        }

        // Handle change event of the select element
        $('#filter-price').change(function () {
            // Get the selected option value
            var selectedValue = $(this).val();

            // Create a new URL object
            var url = new URL(window.location.href);

            // Create a new URLSearchParams object from the URL's search parameters
            var params = new URLSearchParams(url.search);

            // Set the "price" parameter with the selected option value
            params.set("price", selectedValue);

            // Update the URL's search parameters with the modified values
            url.search = params.toString();

            // Replace the current URL with the updated URL
            window.location.href = url.toString();
        });
    })

</script>


