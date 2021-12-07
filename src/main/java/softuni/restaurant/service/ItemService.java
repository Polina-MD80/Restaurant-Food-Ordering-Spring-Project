package softuni.restaurant.service;

import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.view.ItemViewModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemService {
    List<ItemViewModel> getAllItems();

    boolean addItem(ItemServiceModel itemServiceModel);

    boolean isItemNameFree(String name);

    List<ItemViewModel> getAllFoods();

    List<ItemViewModel> getAllDrinks();

    List<ItemViewModel> getAllOther();

    List<ItemViewModel> getAllByCategoryName(String categoryName);

    ItemEntity findById(Long itemId);

    void initializeItems();


}
