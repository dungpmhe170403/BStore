<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="checkout spad" id="checkout">
    <div class="container">
        <div class="checkout__form">
            <h4>Billing Details</h4>
            <form action="./check-out" method="post">
                <div class="row">
                    <div class="col-lg-8 col-md-6">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="checkout__input">
                                    <p>Username<span>*</span></p>
                                    <input type="text" readonly value="${sessionScope.user.username}"/>
                                </div>
                            </div>
                        </div>
                        <div class="checkout__input">
                            <p>Address<span>*</span></p>
                            <input
                                    type="text"
                                    placeholder="Street Address"
                                    class="checkout__input__add"
                                    readonly
                                    value="${sessionScope.user.address}"
                            />
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="checkout__input">
                                    <p>Phone<span>*</span></p>
                                    <input type="text" name="phone" required/>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="checkout__input">
                                    <p>Email<span>*</span></p>
                                    <input type="text" name="email" value="${sessionScope.user.email}"/>
                                </div>
                            </div>
                        </div>
                        <div class="checkout__input">
                            <p>Order notes<span>*</span></p>
                            <input
                                    name="notes"
                                    type="text"
                                    placeholder="Notes about your order, e.g. special notes for delivery."
                            />
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="checkout__order">
                            <c:set var="totalCart" value="0"/>

                            <h4>Your Order</h4>
                            <div class="checkout__order__products">
                                Products <span>Total</span>
                            </div>
                            <input value="${data['cart'].cart_id}" name="cart_id" hidden/>
                            <ul>
                                <c:forEach var="item" items="${data['cart'].items}">
                                    <li>${item.product.name}<span>$${item.product.price * item.quantity}</span>
                                        <c:set var="totalCart"
                                               value="${item.product.price * item.quantity + totalCart}"/>
                                    </li>
                                </c:forEach>
                            </ul>
                            <div class="checkout__order__total">
                                Total <span>$${totalCart}</span>
                            </div>
                            <p>
                                Pay 50% to this Banking Account
                                <br/>
                                Name: Minh Dung
                                <br/>
                                Bank: Bank XX
                                <br/>
                                Bank ID : 123112761289
                                <br>
                                Your order result will be place! Notification will be send to your phone number
                            </p>
                            <button type="submit" class="site-btn">PLACE ORDER</button>
                            <div>
                                <% String orderStatus = (String) session.getAttribute("orderStatus"); %>
                                <%= orderStatus == null ? "" : orderStatus %>
                                <% session.removeAttribute("orderStatus"); %>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<script>
    document.getElementById("checkout").scrollIntoView();
</script>
