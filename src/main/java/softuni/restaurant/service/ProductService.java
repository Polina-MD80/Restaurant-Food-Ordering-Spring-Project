package softuni.restaurant.service;

import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.service.ProductServiceModel;
import softuni.restaurant.model.view.ProductEditView;
import softuni.restaurant.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    List<ProductViewModel> allProducts();

    boolean addProduct(ProductServiceModel productServiceModel);

    ProductEditView findById(Long id);

    boolean isCategoryNameFree(String name);

    List<String> allProductsByName();

    ProductEntity findProductByName(String name);

    void initializeProducts();

    List<ProductEntity> getAllProductEntities();

    void deleteProducts(Long id);
}
