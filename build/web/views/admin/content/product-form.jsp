<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>Add Product</h1>
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
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Quick Example</h3>
                        </div>
                        <form method="post" enctype="multipart/form-data" action="${data['form-action']}">
                            <div class="card-body">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Shoes Name</label>
                                    <input type="text" class="form-control" id="exampleInputEmail1" placeholder="Name"
                                           name="name"
                                           value="${data['product'].name== null ? "": data['product'].name}">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Price</label>
                                    <input type="text" class="form-control" id="exampleInputPassword1"
                                           placeholder="Price" name="price"
                                           value="${data['product'].price== null ? "":data['product'].price}">
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea class="form-control" rows="3" placeholder="Enter ..."
                                              style="height: 79px;" name="description">
                                        ${data['product'].description== null ? "": data['product'].description}
                                    </textarea>
                                </div>
                                <div class="form-group">
                                    <label for="exampleSelectRounded0">Brands</label>
                                    <select class="custom-select rounded-0" id="exampleSelectRounded0" name="brand">
                                        <c:forEach var="brand" items="${data['brands']}">
                                            <option ${data['product'].brand== null ? "": data['product'].brand == brand.brand_id ? "selected" :""}
                                                    value="${brand.brand_id}">${brand.brand_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <c:if test="${data['isEdit'] != null && data['isEdit'] ? false :true}">
                                    <div class="form-group">
                                        <label for="exampleInputFile">Images</label>
                                        <div class="input-group">
                                            <div class="image">
                                                <input type="file" class="file-input" id="exampleInputFile"
                                                       name="images[]" multiple accept="image/jpeg,image/jpg">
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                            </div>
                            <div class="card-footer">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </form>
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


