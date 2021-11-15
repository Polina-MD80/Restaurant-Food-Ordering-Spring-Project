package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.view.ItemViewModel;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;

    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper, PictureService pictureService) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
    }

    @Override
    public List<ItemViewModel> getAllItems() {
//        List<ItemEntity> itemEntities = itemRepository.findAll();
//        List<ItemViewModel> itemViewModels = itemEntities.stream().map(itemEntity -> modelMapper.map(itemEntities, ItemViewModel.class)).collect(Collectors.toList());
//        itemViewModels.stream().forEach(itemViewModel -> {
//            itemViewModel.setCategories()
//        });
        return null;
    }

    @Override
    public boolean addItem(ItemServiceModel itemServiceModel) {
        return false;
    }

    @Override
    public boolean isItemNameFree(String name) {
        return !itemRepository.existsByName(name);
    }
}
