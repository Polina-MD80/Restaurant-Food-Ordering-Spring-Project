package softuni.restaurant.service;

import softuni.restaurant.model.view.ItemViewModel;

import java.util.List;

public interface ItemService {
    List<ItemViewModel> getAllItems();
}
