package softuni.restaurant.service;

import softuni.restaurant.model.entity.OrderItemEntity;

import java.util.Set;

public interface OrderItemService {
    void initializeOrderItems();

    OrderItemEntity getOrderItemById(Long id);

    void save(Set<OrderItemEntity> orderItemEntities);
}
