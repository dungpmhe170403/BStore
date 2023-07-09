package model.repository;

import model.entity.Brands;
import ultis.DBHelper.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class BrandRepository extends Repository<Brands> {

    private static BrandRepository brandRepository;

    public static BrandRepository getInstance() {
        if (brandRepository == null) {
            brandRepository = new BrandRepository();

        }
        return brandRepository;

    }

    private BrandRepository() {
        table("brand");
        this.init();
    }

    public ArrayList<Brands> getAllBrands() {
        Optional<ArrayList<Brands>> records = this.queryExecutor.records(queryHelper.select("*").build(), this);
        return records.orElse(new ArrayList<>());
    }

    @Override
    public Brands mapper(ResultSet rs) throws SQLException {
        return Brands.builder()
                .brand_id(rs.getInt("brand_id"))
                .brand_name(rs.getString("brand_name"))
                .build();
    }
}
