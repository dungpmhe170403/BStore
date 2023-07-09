package model.repository;

import model.entity.Product;
import model.helper.type.FilterProduct;
import ultis.DBHelper.bound.Pagination;
import ultis.DBHelper.bound.SortOrder;
import ultis.DBHelper.executor.QueryExecutor;
import ultis.DBHelper.query.QueryHelper;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ProductRepository extends Repository<Product> {
    private static ProductRepository productRepository;
    HashMap<String, Product> productMap;
    ProductImageRepository productImageRepository;

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();

        }
        return productRepository;
    }

    private ProductRepository() {
        table("products");
        fillable("name", "price", "brand", "description");
        this.init();
        productMap = new HashMap<>();
        productImageRepository = ProductImageRepository.getInstance();
        // always join to get image
        this.queryHelper.select("*").innerJoin("shoes_images", "products.id = shoes_images.shoes_id").joinned();
        System.out.println(queryHelper.toString());
    }

    @Override
    public Product mapper(ResultSet rs) throws SQLException {
        return Product.builder().id(rs.getInt("id")).name(rs.getString("name")).brand(rs.getInt("brand")).price(rs.getFloat("price")).description(rs.getString("description")).images(new ArrayList<>()).build();
    }

    public Product joinMapper(ResultSet rs) {
        Product product;
        try {
            product = this.mapper(rs);
            String id = String.valueOf(product.getId());
            Product presentProduct = productMap.putIfAbsent(id, product);
            if (presentProduct != null) {
                presentProduct.getImages().add(productImageRepository.mapper(rs));
            }
            product.getImages().add(productImageRepository.mapper(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public Product getProduct(int id) {
        QueryHelper sqlHelper = queryHelper.copy();
        String sql = sqlHelper.where().condition("id =" + id).endCondition().build();
        queryExecutor.records(sql, this::joinMapper);
        System.out.println();
        return getProducts(sql).map(products -> products.get(0)).orElse(null);
    }

    public Pagination<Product> getPaginationProduct(int pageNumber, HashMap<FilterProduct, String> condition) {
        boolean hasFilter = false;
        Pagination<Product> pagination = new Pagination<>(pageNumber, condition);
        QueryHelper tempSql = queryHelper.copy();
        tempSql.where();
        if (condition.containsKey(FilterProduct.BRAND)) {
            hasFilter = true;
            tempSql.condition("brand = " + condition.get(FilterProduct.BRAND));
        }
        if (condition.containsKey(FilterProduct.KEY)) {
            hasFilter = true;
            tempSql.condition(String.format("name LIKE %%s%", condition.get(FilterProduct.KEY)));
        }
        if (!hasFilter) {
            tempSql.noWhere();
        }
        tempSql.endCondition();
        QueryHelper getCount = tempSql.copy();
        getCount.count();
        if (condition.containsKey(FilterProduct.PRICE)) {
            String order = condition.get(FilterProduct.PRICE);
            //end condition
            tempSql.orderBy("price", order.equalsIgnoreCase(SortOrder.ASC.toString()) ? SortOrder.ASC : SortOrder.DESC);
            System.out.println(queryHelper.queryBuilder.toString() + "Add orderBy");
        }
        QueryHelper paginationLimit = tempSql.offsetCount(pagination.offset * 3).limit(pagination.limit * 3);
        Optional<ArrayList<Integer>> rows = new QueryExecutor<Integer>().records(getCount.build(), rs -> rs.getInt(1));
        pagination.totalItems = rows.map(data -> data.get(0)).orElse(0);
        pagination.calculateTotalPages();
        ArrayList<Product> products = getProducts(paginationLimit.build()).orElse(new ArrayList<>());
        pagination.setData(products);
        return pagination;
    }

    public Optional<ArrayList<Product>> getProducts(String sql) {
        productMap = new HashMap<>();
        this.queryExecutor.records(sql, this::joinMapper);
        return Optional.of(new ArrayList<>(this.productMap.values()));
    }

    public ArrayList<Product> getLatestProduct() {
        QueryHelper sql = queryHelper.copy();
        sql.orderBy("created_at", SortOrder.DESC).limit(8 * 3);
        return getProducts(sql.build()).orElse(new ArrayList<>());
    }
}
