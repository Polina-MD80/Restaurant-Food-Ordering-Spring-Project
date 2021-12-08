package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.service.ProductServiceModel;
import softuni.restaurant.model.view.ProductEditView;
import softuni.restaurant.model.view.ProductViewModel;
import softuni.restaurant.repository.ProductRepository;
import softuni.restaurant.service.AllergenService;
import softuni.restaurant.service.ProductService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
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
        return productEntities.stream().map(productEntity -> modelMapper.map(productEntity, ProductViewModel.class)).collect(Collectors.toList());
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
        ProductEntity productEntity = modelMapper.map(productServiceModel, ProductEntity.class);
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

    @Override
    public void initializeProducts() {
        if (productRepository.count() == 0) {
            ProductEntity p1 = new ProductEntity();
            p1.setName("tomato");
            ProductEntity p2 = new ProductEntity();
            p2.setName("cheese");
            p2.setAllergens(Set.of(allergenService.findByName("MILK")));
            ProductEntity p3 = new ProductEntity();
            p3.setName("peanuts");
            p3.setAllergens(Set.of(allergenService.findByName("NUT")));
            ProductEntity p4 = new ProductEntity();
            p4.setName("honey");
            p4.setAllergens(Set.of(allergenService.findByName("BEE")));
            ProductEntity p5 = new ProductEntity();
            p5.setName("coffee");
            p5.setAllergens(Set.of(allergenService.findByName("NUT")));
            ProductEntity p6 = new ProductEntity();
            p6.setName("flour");
            p6.setAllergens(Set.of(allergenService.findByName("WHEAT")));
            ProductEntity p7 = new ProductEntity();
            p7.setName("cream");
            p7.setAllergens(Set.of(allergenService.findByName("MILK"),
                    allergenService.findByName("WHEAT")));
            ProductEntity p8 = new ProductEntity();
            p8.setName("shrimps");
            p8.setAllergens(Set.of(allergenService.findByName("MOLLUSCS"),
                    allergenService.findByName("FISH")));
            ProductEntity p9 = new ProductEntity();
            p9.setName("sausage")
                    .setAllergens(Set.of(allergenService.findByName("WHEAT"),
                            allergenService.findByName("SOY")));

            ProductEntity p10 = new ProductEntity();
            p10.setName("potatoes");

            ProductEntity p11 = new ProductEntity();
            p11.setName("cucumbers");
            ProductEntity p12 = new ProductEntity();
            p12.setName("peppers");

            ProductEntity p13 = new ProductEntity();
            p13.setName("olive oil");

            ProductEntity p14 = new ProductEntity();
            p14.setName("lemon juice");

            ProductEntity p15 = new ProductEntity();
            p15.setName("salmon").setAllergens(Set.of(allergenService.findByName("FISH")));

            ProductEntity p16 = new ProductEntity();
            p16.setName("chicken");

            productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16));

        }


    }

    @Override
    public List<ProductEntity> getAllProductEntities() {

        return productRepository.findAll();
    }

    @Override
    @CacheEvict(value = "allItems", allEntries = true)
    public void deleteProduct(Long id) {

            ProductEntity productEntity = findProductEntityById(id);
        Set<ItemEntity> items = productEntity.getItems();
                    items.forEach(itemEntity -> itemEntity.removeProduct(productEntity));
            productRepository.delete(productEntity);

    }

    private ProductEntity findProductEntityById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product with id " + " is not found."));
    }


}
