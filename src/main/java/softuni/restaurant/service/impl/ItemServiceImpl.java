package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import softuni.restaurant.constants.RestaurantConstantImages;
import softuni.restaurant.model.binding.ItemUpdateBindingModel;
import softuni.restaurant.model.entity.*;
import softuni.restaurant.model.entity.enums.TypeEnum;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.view.ItemViewModel;
import softuni.restaurant.model.view.PictureViewModel;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.service.ProductService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final CategoryService categoryService;


    @Override
    @Cacheable("allItems")
    public List<ItemViewModel> getAllItems() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        return mapToItemViewModels(itemEntities);
    }

    private final ProductService productService;

    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper, PictureService pictureService, CategoryService categoryService, ProductService productService) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    private List<ItemViewModel> mapToItemViewModels(List<ItemEntity> itemEntities) {
        return itemEntities.stream()
                .map(itemEntity -> {
                    ItemViewModel itemViewModel = modelMapper.map(itemEntity, ItemViewModel.class);
                    Set<String> allergenImageUrls = itemEntity.getAllergens().stream().map(AllergenEntity::getImageUrl).collect(Collectors.toSet());
                    itemViewModel
                            .setActive(itemEntity.isActive())
                            .setCategories(itemEntity.getCategoriesNames())
                            .setProducts(itemEntity.getProductsByName())
                            .setAllergens(allergenImageUrls);

                    if (itemViewModel.getPicture() == null) {
                        itemViewModel.setPicture(new PictureViewModel().setUrl(RestaurantConstantImages.DEFAULT_IMAGE));
                    }

                    return itemViewModel;

                }).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "allItems", allEntries = true)
    public boolean addItem(ItemServiceModel itemServiceModel) {
        ItemEntity itemEntity = modelMapper.map(itemServiceModel, ItemEntity.class);

        itemEntity.collectAllergens();

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

        return
                mapToItemViewModels(itemRepository.allFoods());
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
            List<ItemEntity> itemEntities = categoryService.findCategoryByName(categoryName).getItems().stream().toList();
            return mapToItemViewModels(itemEntities);
        } catch (Exception e) {
            throw new ObjectNotFoundException("Category " + categoryName + " No Longer Exists");
        }
    }

    @Override
    public ItemEntity findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> (new ObjectNotFoundException("Item is no longer available.")));
    }


    @Override
    public ItemUpdateBindingModel getItemUpdateBindingModel(Long id) {
        ItemEntity itemEntity = this.findById(id);
        return modelMapper.map(itemEntity, ItemUpdateBindingModel.class);
    }

    @Override
    @CacheEvict(value = "allItems", allEntries = true)
    public boolean itemUpdate(ItemServiceModel serviceModel) {
        ItemEntity itemEntity = this.findById(serviceModel.getId());
        itemEntity.setName(serviceModel.getName())
                .setDescription(serviceModel.getDescription())
                .setProducts(serviceModel.getProducts())
                .setCategories(serviceModel.getCategories())
                .setManufacturer(serviceModel.getManufacturer())
                .setVolume(serviceModel.getVolume())
                .setWeight(serviceModel.getWeight())
                .setType(serviceModel.getType())
                .setPrice(serviceModel.getPrice())
                .setActive(serviceModel.isActive())
                .collectAllergens();

        if (serviceModel.getPicture() != null) {
            String tempPublicId = "";
            Long tempPicId = 0L;

            if (itemEntity.getPicture() != null) {
                tempPublicId = itemEntity.getPicture().getPublicId();
                tempPicId = itemEntity.getPicture().getId();
            }
            PictureEntity pictureEntity = modelMapper.map(serviceModel.getPicture(), PictureEntity.class);
            itemEntity.setPicture(pictureEntity);
            try {
                pictureService.deletePicture(tempPublicId, tempPicId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            itemRepository.save(itemEntity);
        } catch (Exception e) {

            return false;
        }
        return true;

    }

    @Override
    @CacheEvict(value = "allItems", allEntries = true)
    public void deleteItem(Long id) {
        ItemEntity itemEntity = this.findById(id);
        itemEntity.setCategories(new HashSet<>()).setProducts(new HashSet<>()).setAllergens(new HashSet<>());
        PictureEntity picture = itemEntity.getPicture();
        itemEntity.setPicture(null);
        itemEntity.setActive(false);
        if (picture != null) {
            pictureService.deletePicture(picture.getPublicId(), picture.getId());
        }

        itemRepository.delete(itemEntity);


    }

    @Override
    public List<ItemViewModel> getAllByKeyWord(String keyword) {
        return mapToItemViewModels(itemRepository.search(keyword));

    }

    @Override
    public void initializeItems() {
        if (itemRepository.count() == 0) {
            ItemEntity e1 = new ItemEntity()
                    .setName("Shrimps soup")
                    .setPicture(pictureService.findPictureByIt(8L))
                    .setManufacturer("Chef Li")
                    .setType(TypeEnum.FOOD)
                    .setProducts(Set.of(productService.findProductByName("cheese"),
                            productService.findProductByName("shrimps"),
                            productService.findProductByName("lemon juice"),
                            productService.findProductByName("olive oil")))
                    .setCategories(Set.of(categoryService.findCategoryByName("soups"),
                            categoryService.findCategoryByName("starters")))
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(12.50))
                    .setVolume(250);

            itemRepository.save(e1);

            ItemEntity e2 = new ItemEntity()
                    .setPicture(pictureService.findPictureByIt(9L))
                    .setName("Pizza Pollo")
                    .setManufacturer("Chef Li")
                    .setType(TypeEnum.FOOD)
                    .setProducts(Set.of(productService.findProductByName("cheese"),
                            productService.findProductByName("chicken"),
                            productService.findProductByName("cream"),
                            productService.findProductByName("flour"),
                            productService.findProductByName("peppers")))
                    .setCategories(Set.of(
                            categoryService.findCategoryByName("main dishes"),
                            categoryService.findCategoryByName("pizza")))
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(15))
                    .setWeight(250);

            itemRepository.save(e2);

            ItemEntity e3 = new ItemEntity()
                    .setPicture(pictureService.findPictureByIt(4L))
                    .setName("Ceasar salad")
                    .setManufacturer("Chef Li")
                    .setType(TypeEnum.FOOD)
                    .setProducts(Set.of(productService.findProductByName("cheese"),
                            productService.findProductByName("chicken"),
                            productService.findProductByName("cream"),
                            productService.findProductByName("olive oil"),
                            productService.findProductByName("cucumbers")))
                    .setCategories(Set.of(
                            categoryService.findCategoryByName("starters")))
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(10))
                    .setWeight(250);

            itemRepository.save(e3);


            ItemEntity e4 = new ItemEntity()
                    .setPicture(pictureService.findPictureByIt(6L))
                    .setName("Main dish salad")
                    .setManufacturer("Chef Li")
                    .setType(TypeEnum.FOOD)
                    .setProducts(Set.of(productService.findProductByName("cheese"),
                            productService.findProductByName("salmon"),
                            productService.findProductByName("cream"),
                            productService.findProductByName("cucumbers"),
                            productService.findProductByName("tomato")))
                    .setCategories(Set.of(
                            categoryService.findCategoryByName("main dishes"),
                            categoryService.findCategoryByName("starters")))
                    .setActive(false)
                    .setPrice(BigDecimal.valueOf(15))
                    .setWeight(250);

            itemRepository.save(e4);
            ItemEntity e5 = new ItemEntity()
                    .setName("Mushroom soup")
                    .setPicture(pictureService.findPictureByIt(2L))
                    .setManufacturer("Chef Li")
                    .setType(TypeEnum.FOOD)
                    .setProducts(Set.of(
                            productService.findProductByName("potatoes"),
                            productService.findProductByName("tomato"),
                            productService.findProductByName("olive oil")))
                    .setCategories(Set.of(categoryService.findCategoryByName("soups"),
                            categoryService.findCategoryByName("starters")))
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(11))
                    .setVolume(250);

            itemRepository.save(e5);

            ItemEntity e6 = new ItemEntity()
                    .setName("Latte")
                    .setManufacturer("Lavazza")
                    .setType(TypeEnum.DRINK)
                    .setProducts(Set.of(
                            productService.findProductByName("coffee"),
                            productService.findProductByName("cream")))
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(5.55))
                    .setVolume(160)
                    .setPicture(pictureService.findPictureByIt(10L));

            itemRepository.save(e6);

            ItemEntity e7 = new ItemEntity()
                    .setName("Beer")
                    .setPicture(pictureService.findPictureByIt(11L))
                    .setDescription("Draft beer from Belgium.")
                    .setManufacturer("Heffe")
                    .setType(TypeEnum.DRINK)
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(6))
                    .setVolume(330);

            itemRepository.save(e7);

            ItemEntity e8 = new ItemEntity()
                    .setName("Cola")
                    .setDescription("Sparkling, sweet, american.")
                    .setManufacturer("Coca cola")
                    .setType(TypeEnum.DRINK)
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(4))
                    .setVolume(250);

            itemRepository.save(e8);

            ItemEntity e9 = new ItemEntity()
                    .setName("Water")
                    .setDescription("Pure, from Bulgarian mountains.")
                    .setManufacturer("Devin")
                    .setType(TypeEnum.DRINK)
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(2))
                    .setVolume(330);

            itemRepository.save(e9);

            ItemEntity e10 = new ItemEntity()
                    .setName("Plastic Cup")
                    .setDescription("Not good for the nature, but sometime it t needed.")
                    .setManufacturer("EcoPack")
                    .setType(TypeEnum.OTHER)
                    .setActive(true)
                    .setPrice(BigDecimal.valueOf(2))
                    .setVolume(330);

            itemRepository.save(e10);

        }
    }


}
