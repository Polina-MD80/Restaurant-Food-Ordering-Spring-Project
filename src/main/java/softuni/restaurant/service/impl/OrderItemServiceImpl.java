package softuni.restaurant.service.impl;

import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.repository.OrderItemRepository;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.OrderItemService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

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

            OrderItemEntity oa2 = new OrderItemEntity();
            ItemEntity itemEntity2 = itemService.findById(3L);
            oa2.setItem(itemEntity2).setQuantity(6);
            orderItemRepository.save(oa2);
        }
    }

    @Override
    public OrderItemEntity getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("OrderItem with id " + id +" not found."));
    }
}