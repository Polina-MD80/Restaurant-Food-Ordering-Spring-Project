package softuni.restaurant.service.impl;

import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.repository.OrderItemRepository;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final ItemService itemService;
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(ItemService itemService, OrderItemRepository orderItemRepository) {
        this.itemService = itemService;

        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public void initializeOrderItems() {
        if (orderItemRepository.count() == 0) {
            OrderItemEntity oa = new OrderItemEntity();
            ItemEntity itemEntity = itemService.findById(1L);
            oa.setItem(itemEntity).setQuantity(3);
            orderItemRepository.save(oa);

            OrderItemEntity oa1 = new OrderItemEntity();
            ItemEntity itemEntity1 = itemService.findById(2L);
            oa1.setItem(itemEntity1).setQuantity(4);
            orderItemRepository.save(oa1);
        }
    }
}