<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<!DOCTYPE html>--%>
<c:set var="user" value="${sessionScope.user}"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>MeetYourWish</title>
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="css/test.css">
    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css">

</head>
<body>
<%--header--%>
<div class="humberger__menu__overlay"></div>
<div class="humberger__menu__wrapper">
    <div class="humberger__menu__logo">
        <h3>MYW</h3>
    </div>
    <div class="humberger__menu__cart">
        <ul>
            <li><a href="#"><i class="fa fa-shopping-bag"></i></a></li>
        </ul>
    </div>
    <div class="humberger__menu__widget">
        <div class="header__top__right__auth">
            <c:choose>
                <c:when test="${sessionScope.loggedIn eq true}">
                    <div class="btn-group">
                        <button type="button" class="btn btn-warning dropdown-toggle"
                                data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-user">${user.username}</i>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#" data-toggle="modal"
                               data-target="#exampleModal">Edit Profile</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="./logout">Logout</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="./login"><i class="fa fa-user">Login</i></a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <nav class="humberger__menu__nav mobile-menu">
        <ul>
            <li class="active"><a href="./home">Home</a></li>
            <li><a href="./products">Shop</a></li>
            <li><a href="./shoping-cart.html">Shoping Cart</a></li>
            <li><a href="./checkout.html">Check Out</a></li>
            </li>
        </ul>
    </nav>
    <div id="mobile-menu-wrap"></div>
    <div class="header__top__right__social">
        <a href="#"><i class="fa fa-facebook"></i></a>
        <a href="#"><i class="fa fa-twitter"></i></a>
        <a href="#"><i class="fa fa-linkedin"></i></a>
        <a href="#"><i class="fa fa-pinterest-p"></i></a>
    </div>
    <div class="humberger__menu__contact">
        <ul>
            <li><i class="fa fa-envelope"></i> dung@fpt</li>
            <li>Free Shipping for all Order of $99</li>
        </ul>
    </div>
</div>
<!-- Humberger End -->

<!-- Header Section Begin -->
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="header__top__left">
                        <ul>
                            <li><i class="fa fa-envelope"></i> dung@fpt.com</li>
                            <li>Free Shipping for all Order of $99</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6">
                    <div class="header__top__right">
                        <div class="header__top__right__social">
                            <a href="#"><i class="fa fa-facebook"></i></a>
                            <a href="#"><i class="fa fa-twitter"></i></a>
                            <a href="#"><i class="fa fa-linkedin"></i></a>
                            <a href="#"><i class="fa fa-pinterest-p"></i></a>
                        </div>
                        <div class="header__top__right__auth">
                            <c:choose>
                                <c:when test="${sessionScope.loggedIn eq true}">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-warning dropdown-toggle"
                                                data-toggle="dropdown"
                                                aria-haspopup="true" aria-expanded="false">
                                            <i class="fa fa-user">${user.username}</i>
                                        </button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="#" data-toggle="modal"
                                               data-target="#exampleModal">Edit Profile</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="./logout">Logout</a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a href="./login"><i class="fa fa-user">Login</i></a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="header__logo">
                    <h3><b>MYW</b></h3>
                </div>
            </div>
            <div class="col-lg-6">
                <nav class="header__menu">
                    <ul>
                        <li class="active"><a href="./home">Home</a></li>
                        <li><a href="./products">Shop</a></li>
                        <li><a href="./shoping-cart.html">Shoping Cart</a></li>
                        <li><a href="./checkout.html">Check Out</a></li>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-3">
                <div class="header__cart">
                    <ul>
                        <li><a href="#"><i class="fa fa-shopping-bag"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="humberger__open">
            <i class="fa fa-bars"></i>
        </div>
    </div>
</header>
<section class="hero">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="hero__categories">
                    <div class="hero__categories__all">
                        <i class="fa fa-bars"></i>
                        <span>All Brands</span>
                    </div>
                    <ul>
                        <c:forEach var="brand" items="${data['brands']}">
                            <li><a href="./products?page=1&brands=${brand.brand_id}">${brand.brand_name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="col-lg-9">
                <div class="hero__search">
                    <div class="hero__search__form">
                        <form action="#">
                            <input type="text" placeholder="What do yo u need?">
                            <button type="submit" class="site-btn">SEARCH</button>
                        </form>
                    </div>
                    <div class="hero__search__phone">
                        <div class="hero__search__phone__icon">
                            <i class="fa fa-phone"></i>
                        </div>
                        <div class="hero__search__phone__text">
                            <h5>+841273678</h5>
                            <span>support 24/7 time</span>
                        </div>
                    </div>
                </div>
                <div class="hero__item set-bg" data-setbg="img/hero/banner.jpg">
                    <div class="hero__text">
                        <span>FRESH SHOES</span>
                        <h2>SHOES <br/>100% Authentic</h2>
                        <p>Free Pickup and Delivery Available</p>
                        <a href="./products" class="primary-btn">SHOP NOW</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="categories">
    <div class="container">
        <div class="row">
            <div class="categories__slider owl-carousel">
                <div class="col-lg-3">
                    <!-- TODO: brand Image click to go the filter by brand -->
                    <div class="categories__item set-bg" data-setbg="img/brands/Adidas.png">
                        <h5><a href="./products?page=1&brand=2">Adidas</a></h5>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="categories__item set-bg" data-setbg="img/brands/Nike.png">
                        <h5><a href="./products?page=1&brand=3">Nike</a></h5>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="categories__item set-bg" data-setbg="img/brands/Puma.png">
                        <h5><a href="./products?page=1&brand=4">Pulma</a></h5>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="categories__item set-bg" data-setbg="img/brands/Under-Armour.png">
                        <h5><a href="./products?page=1&brand=5">Under Amor</a></h5>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="categories__item set-bg" data-setbg="img/brands/New-Balance.png">
                        <h5><a href="./products?page=1&brand=6">New Blance</a></h5>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="views/${data['view']}"/>
<footer class="footer spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 col-sm-6">
                <div class="footer__about">
                    <div class="footer__about__logo">
                        <h3><b>MYW</b></h3>
                    </div>
                    <ul>
                        <li>Address: Hoa Lac Hanoi</li>
                        <li>Phone: +841231287</li>
                        <li>Email: dung@fpt</li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-4 col-md-12">
                <div class="footer__widget">
                    <h6>Join Our Newsletter Now</h6>
                    <p>Get E-mail updates about our latest shop and special offers.</p>
                    <form action="#">
                        <input type="text" placeholder="Enter your mail">
                        <button type="submit" class="site-btn">Subscribe</button>
                    </form>
                    <div class="footer__widget__social">
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-instagram"></i></a>
                        <a href="#"><i class="fa fa-twitter"></i></a>
                        <a href="#"><i class="fa fa-pinterest"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
<% String updatedStatus = (String) session.getAttribute("userEditResult"); %>
<% if (updatedStatus != null) { %>
<div style="position: absolute; top: 0; right: 0;">

    <!-- Then put toasts within -->
    <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="mr-auto">Update Status</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            <%= updatedStatus %>

        </div>
    </div>
</div>
<% session.removeAttribute("userEditResult"); %>
<% } %>

<!-- Position it -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Edit ${user.username}'s Profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" action="./edit-user">
                    <div class="form-group">
                        <input hidden value="${user.role}" name="role">
                        <input hidden value="${user.user_id}" name="user_id">
                        <label for="usernameedit" class="col-form-label">Username:</label>
                        <input type="text" class="form-control" value="${user.username}" id="usernameedit"
                               name="username">
                    </div>
                    <div class="form-group">
                        <label for="edit-email" class="col-form-label">Email:</label>
                        <input type="text" class="form-control" id="edit-email" readonly value="${user.email}"
                               name="email">
                    </div>
                    <div class="form-group">
                        <label for="address-text" class="col-form-label">Address:</label>
                        <input class="form-control" id="address-text" type="text"
                               value="${user.address == null ? "": user.address}" name="address">
                    </div>
                    <div class="form-group">
                        <label for="password-edit" class="col-form-label">Enter password to confirm:</label>
                        <input class="form-control" id="password-edit" type="text" name="password" required>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Submit</button>
                        <a href="./change-password">change your password ?</a>
                    </div>
                </form>
            </div>


        </div>
    </div>

</div>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/mixitup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>