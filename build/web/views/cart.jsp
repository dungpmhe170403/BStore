<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="shoping-cart spad">
    <div class="container">
        <form action="./cart" method="post">
            <div class="row">

                <div class="col-lg-12">

                    <div class="shoping__cart__table" id="shopping-cart">
                        <table>
                            <thead>
                            <tr>
                                <th class="shoping__product">Products</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Size</th>
                                <th>Total</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="totalCart" value="0"/>
                            <c:forEach var="item" items="${data['cart'].items}">
                                <input type="text" name="shoes_id" value="${item.product.id}" hidden>
                                <tr>
                                    <td class="shoping__cart__item">
                                        <img src="img/products/${item.product.images[0].image_path}" alt=""
                                             style="width: 130px; aspect-ratio: 1;">
                                        <h5><a href="./product-detail?id=${item.product.id}">${item.product.name}</a>
                                        </h5>
                                    </td>
                                    <td class="shoping__cart__price">
                                            ${item.product.price}
                                    </td>
                                    <td class="shoping__cart__quantity">
                                        <div class="quantity">
                                            <div class="pro-qty">
                                                <input type="text" value=${item.quantity} name="quantity" required>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <input type="number" name="size" value="${item.size}" required>
                                    </td>
                                    <td class="shoping__cart__total">
                                            ${item.product.price * item.quantity}
                                        <c:set var="totalCart"
                                               value="${item.product.price * item.quantity + totalCart}"/>
                                    </td>
                                    <td class="shoping__cart__item__close" data-delete="${item.order_item_id}">
                                        <span class="icon_close"></span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="shoping__cart__btns">
                        <a href="./products" class="primary-btn cart-btn">CONTINUE SHOPPING</a>
                        <input name="updateCart" hidden value="updateCart"/>
                        <button type="submit" class="primary-btn cart-btn cart-btn-right"><span
                                class="icon_loading"></span>
                            Upadate Cart
                        </button>
                    </div>
                </div>
                <div class="col-lg-6">
                </div>
                <div class="col-lg-6">
                    <div class="shoping__checkout">
                        <h5>Cart Total</h5>
                        <ul>
                            <li>Total <span>$${totalCart}</span></li>
                        </ul>
                        <a href="./check-out" class="primary-btn">PROCEED TO CHECKOUT</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
<script>
    document.getElementById("shopping-cart").scrollIntoView();
</script>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script>
    $(".shoping__cart__item__close").click((event) => {
        let itemId = $(event.currentTarget).attr("data-delete");
        let rowElement = $(event.currentTarget).closest("tr");
        $.ajax({
            url: './cart?id=' + itemId,
            method: 'DELETE',
            success: function (response) {
                rowElement.remove();
            },
            error: function (xhr, status, error) {
                // Handle the error response
                console.log('Error:', error);
            }
        });

    })

</script>