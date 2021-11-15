package softuni.restaurant.service;

import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.view.ItemViewModel;

import java.util.List;

public interface ItemService {
    List<ItemViewModel> getAllItems();

    boolean addItem(ItemServiceModel itemServiceModel);

    boolean isItemNameFree(String name);
}
