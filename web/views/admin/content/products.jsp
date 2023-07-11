<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    td {
        text-align: center;
        vertical-align: center;
    }

    .product__pagination,
    .blog__pagination {
        padding-top: 10px;
        display: flex;
    }

    .product__pagination a,
    .blog__pagination a {
        width: 30px;
        height: 30px;
        border: 1px solid #b2b2b2;
        font-size: 14px;
        color: #b2b2b2;
        font-weight: 700;
        line-height: 28px;
        text-align: center;
        margin-right: 16px;
        -webkit-transition: all, 0.3s;
        -moz-transition: all, 0.3s;
        -ms-transition: all, 0.3s;
        -o-transition: all, 0.3s;
        transition: all, 0.3s;
    }

    .product__pagination a:hover,
    .blog__pagination a:hover {
        background: orange;
        border-color: orange;
        color: #ffffff;
    }

    .product__pagination a:last-child,
    .blog__pagination a:last-child {
        margin-right: 0;
    }
</style>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Product Tables</h1>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="card">
                        <!-- /.card-header -->
                        <div class="card-header">
                            <form action="" method="get">
                                <div class="input-group">
                                    <input type="search" class="form-control form-control-lg"
                                           placeholder="Type your shoes name here" name="q"
                                           value="${sessionScope.q == null ? "" :sessionScope.q }">
                                    <input name="page" value="${data['currentPage']}" hidden>
                                    <div class="input-group-append">
                                        <button type="submit" class="btn btn-lg btn-default">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-body">
                            <table
                                    id="example2"
                                    class="table table-bordered table-hover"
                            >
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Image</th>
                                    <th>Price</th>
                                    <th>
                                        <a class="btn btn-primary" href="./admin-add-products">
                                            + Add Products
                                        </a>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${data['products']}">
                                    <tr>
                                        <td><a href="./product-detail?id=${item.id}">${item.name}</a>
                                        </td>
                                        <td>
                                            <img
                                                    style="width: 150px; aspect-ratio: 1"
                                                    src="img/products/${item.images[0].image_path}"
                                                    alt=""/>
                                        </td>
                                        <td>
                                            $${item.price}
                                        </td>
                                        <td class="project-actions text-right">
                                            <a class="btn btn-info btn-sm"
                                               href="./admin-edit-products?shoes_id=${item.id}">
                                                <i class="fas fa-pencil-alt"> </i>
                                                Edit
                                            </a>
                                            <a class="btn btn-danger btn-sm delete-product" href="#" data-delete="${item.id}">
                                                <i class="fas fa-trash"> </i>
                                                Delete
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.card-body -->
                        <div class="card-footer">
                            <div class="product__pagination">
                                <c:forEach var="item" items="${data['links']}">
                                    ${item}
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </section>
    <!-- /.content -->
</div>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script>
    $(".delete-product").click((event) => {
        let itemId = $(event.currentTarget).attr("data-delete");
        let rowElement = $(event.currentTarget).closest("tr");
        $.ajax({
            url: './admin-products?id=' + itemId,
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
