package service;

import model.entity.Brands;
import model.repository.BrandRepository;

import java.util.ArrayList;

public class ViewService {

    private BrandRepository brandsBaseRepository;
    private static ViewService viewService;
    public static ArrayList<Brands> brands;

    public static ViewService getInstance() {
        if (viewService == null) {
            viewService = new ViewService();
        }
        return viewService;
    }

    private ViewService() {
        brandsBaseRepository = BrandRepository.getInstance();
    }

    public ArrayList<Brands> getAllBrands() {
        return brandsBaseRepository.getAllBrands();
    }
}
