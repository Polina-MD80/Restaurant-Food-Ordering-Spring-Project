package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.restaurant.constants.RestaurantConstantImages;
import softuni.restaurant.model.entity.AllergenEntity;
import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.ProductEntity;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.view.AllergenViewModel;
import softuni.restaurant.model.view.ItemViewModel;
import softuni.restaurant.model.view.PictureViewModel;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.service.ProductService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper, PictureService pictureService, CategoryService categoryService, ProductService productService) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public List<ItemViewModel> getAllItems() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        return mapToItemViewModels(itemEntities);
    }

    private List<ItemViewModel> mapToItemViewModels(List<ItemEntity> itemEntities) {
        return itemEntities.stream()
                .map(itemEntity -> {
                    ItemViewModel itemViewModel = modelMapper.map(itemEntity, ItemViewModel.class);
                    Set<String> allergenImageUrls = itemEntity.getAllergens().stream().map(AllergenEntity::getImageUrl).collect(Collectors.toSet());
                    itemViewModel
                            .setCategories(itemEntity.getCategoriesNames())
                            .setProducts(itemEntity.getProductsByName())
                            .setAllergens(allergenImageUrls);

                    if (itemViewModel.getPicture() == null) {
                        itemViewModel.setPicture(new PictureViewModel().setUrl(RestaurantConstantImages.DEFAULT_CATEGORY_IMAGE));
                    }

                    return itemViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    public boolean addItem(ItemServiceModel itemServiceModel) {
        ItemEntity itemEntity = modelMapper.map(itemServiceModel, ItemEntity.class);

        Set<CategoryEntity> categoryEntities = itemServiceModel.getCategories().stream().map(categoryService::findCategoryByName)
                .collect(Collectors.toSet());
        Set<ProductEntity> productEntities = itemServiceModel.getProducts().stream().map(productService::findProductByName)
                .collect(Collectors.toSet());
        itemEntity
                .setCategories(categoryEntities)
                .setProducts(productEntities);
        Set<AllergenEntity> allergenEntities = itemEntity.getProducts().stream().flatMap(productEntity -> productEntity.getAllergens().stream()).collect(Collectors.toSet());
        itemEntity.setAllergens(allergenEntities);

        try {
            itemRepository.save(itemEntity);
        } catch (PersistenceException e) {
            System.err.println("HOHO Failed");
            return false;
        }
        return true;
    }

    @Override
    public boolean isItemNameFree(String name) {
        return !itemRepository.existsByName(name);
    }

    @Override
    public List<ItemViewModel> getAllFoods() {
        return mapToItemViewModels(itemRepository.allFoods());
    }

    @Override
    public List<ItemViewModel> getAllDrinks() {
        return mapToItemViewModels(itemRepository.allDrinks());
    }

    @Override
    public List<ItemViewModel> getAllOther() {
        return mapToItemViewModels(itemRepository.allOther());
    }

    @Override
    public List<ItemViewModel> getAllByCategoryName(String categoryName) {
        try {
            List<ItemEntity> itemEntities =categoryService.findCategoryByName(categoryName).getItems().stream().toList();
            return mapToItemViewModels(itemEntities);
        }catch (Exception e){
            throw  new ObjectNotFoundException("Category " +categoryName +" No Longer Exists" );
        }
    }
}
