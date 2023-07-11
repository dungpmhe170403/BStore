package service;

import model.entity.Product;
import model.helper.type.FilterProduct;
import model.repository.ProductRepository;
import ultis.DBHelper.bound.Pagination;
import ultis.DBHelper.bound.PaginationResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductService {
    ProductRepository productRepository;

    public ProductService() {
        productRepository = ProductRepository.getInstance();
    }

    public ArrayList<Product> getLatestProduct() {
        return productRepository.getLatestProduct();
    }

    public PaginationResponse<Product> getProducts(int pageNumber, String path, HashMap<FilterProduct, String> condition) {
        Pagination<Product> products = productRepository.getPaginationProduct(pageNumber, condition);
        return new PaginationResponse<>(path, products);
    }


    public Product getProduct(int id) {
        return productRepository.getProduct(id);
    }

    public Product saveProduct(Product data, ArrayList<String> imagePaths) {
        return productRepository.saveProduct(data, imagePaths);
    }

    public int updateProduct(Product data, int id) {
        return productRepository.updateProduct(id, data);
    }

    public int delete(int del) {
        return productRepository.delete(del);
    }
}
