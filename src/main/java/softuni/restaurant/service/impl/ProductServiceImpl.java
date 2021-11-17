package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.service.ProductServiceModel;
import softuni.restaurant.model.view.AllergenViewModel;
import softuni.restaurant.model.view.ProductEditView;
import softuni.restaurant.model.view.ProductViewModel;
import softuni.restaurant.repository.ProductRepository;
import softuni.restaurant.service.AllergenService;
import softuni.restaurant.service.ProductService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AllergenService allergenService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, AllergenService allergenService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.allergenService = allergenService;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ProductViewModel> allProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream().map(productEntity -> {
                    ProductViewModel productViewModel = modelMapper.map(productEntity, ProductViewModel.class);
                    productViewModel
                            .setAllergens(productEntity.getAllergens().stream().map(allergenEntity -> modelMapper.map(allergenEntity, AllergenViewModel.class))
                                    .collect(Collectors.toSet()));

                    return productViewModel;

                }
        ).collect(Collectors.toList());
    }

    @Override
    public boolean addProduct(ProductServiceModel productServiceModel) {

        ProductEntity productEntity = mapToProduct(productServiceModel);

        try {
            productRepository.save(productEntity);
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    private ProductEntity mapToProduct(ProductServiceModel productServiceModel) {
        Set<AllergenEntity> allergenEntities = productServiceModel.getAllergens().stream().map(enumName -> allergenService.findByName(enumName.name())).collect(Collectors.toSet());
        ProductEntity productEntity = modelMapper.map(productServiceModel, ProductEntity.class);
        productEntity.setAllergens(allergenEntities);
        return productEntity;
    }

    @Override
    public ProductEditView findById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(null);
        return modelMapper.map(productEntity, ProductEditView.class);

    }

    @Override
    public boolean isCategoryNameFree(String name) {
        return !productRepository.existsByName(name);
    }

    @Override
    public List<String> allProductsByName() {
        return productRepository.findAll().stream().map(ProductEntity::getName).collect(Collectors.toList());
    }

    @Override
    public ProductEntity findProductByName(String name) {

        return productRepository.findByName(name).orElse(null);
    }


}
