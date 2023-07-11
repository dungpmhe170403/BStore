package model.repository;

import model.entity.ProductImage;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImageRepository extends Repository<ProductImage> {
    private static ProductImageRepository productImageRepository;

    public static ProductImageRepository getInstance() {
        if (productImageRepository == null) {
            productImageRepository = new ProductImageRepository();

        }
        return productImageRepository;
    }

    private ProductImageRepository() {
        table("shoes_images");
        fillable("image_path", "shoes_id");
        this.init();
    }

    @Override
    public ProductImage mapper(ResultSet rs) throws SQLException {
        return ProductImage.builder().
                image_id(rs.getInt("image_id"))
                .image_path(rs.getString("image_path"))
                .shoes_id(rs.getInt("shoes_id"))
                .build();
    }

    public ProductImage saveImage(String imagePath, Integer shoes_id) {
        ProductImage image = ProductImage.builder()
                .image_path(imagePath)
                .shoes_id(shoes_id)
                .build();
        return this.save(image);
    }

    public int deleteImage(int id) {
        queryHelper.destroy();
        queryHelper.delete().where().condition("shoes_id = " + id).endCondition();
        return queryExecutor.updateQuery(queryHelper.build());
    }
}
