package softuni.restaurant.service;

import softuni.restaurant.model.entity.OrderEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.service.impl.RestaurantUser;

import java.util.List;

public interface OrderService {
    void initializeOrders();
    List<OrderEntity> getAllOrders();

    void setNewStatus(Long id, UserEntity userEntity);

    OrderEntity findOrderById(Long id);
    void deleteOrder(OrderEntity orderEntity);

    void deleteAllOldOrders();

    boolean saveOrder(OrderEntity order, RestaurantUser user);

    void deleteOrderById(Long id);

}
