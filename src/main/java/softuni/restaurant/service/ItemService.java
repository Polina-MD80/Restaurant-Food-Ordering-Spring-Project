package softuni.restaurant.service;

import softuni.restaurant.model.binding.ItemAddBindingModel;
import softuni.restaurant.model.binding.ItemUpdateBindingModel;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.view.ItemViewModel;

import java.util.List;

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

    ItemUpdateBindingModel getItemUpdateBindingModel(Long id);

    boolean itemUpdate(ItemServiceModel itemServiceModel);

    void deleteItem(Long id) throws Exception;

    List<ItemViewModel> getAllByKeyWord(String keyword);
}
