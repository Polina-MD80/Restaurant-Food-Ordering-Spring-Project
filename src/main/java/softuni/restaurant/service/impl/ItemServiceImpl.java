package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.view.ItemViewModel;
import softuni.restaurant.repository.ItemRepository;
import softuni.restaurant.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
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
}
