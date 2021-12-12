package softuni.restaurant.service;

import softuni.restaurant.model.entity.OrderItemEntity;

public interface OrderItemService {
    void initializeOrderItems();

    OrderItemEntity getOrderItemById(Long id);
}
