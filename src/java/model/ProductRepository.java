package model;

import com.example.demo.model.entity.Product;

public class ProductRepository extends BaseRepository <Product> {
    private static ProductRepository productRepository;

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();

        }
        return productRepository;
    }

    private ProductRepository() {
        super();
    }

}
