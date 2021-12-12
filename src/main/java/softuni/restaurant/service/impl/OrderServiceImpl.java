package softuni.restaurant.service.impl;

import org.springframework.stereotype.Service;
import softuni.restaurant.model.entity.OrderEntity;
import softuni.restaurant.model.entity.OrderItemEntity;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.OrderStatusEnum;
import softuni.restaurant.repository.OrderRepository;
import softuni.restaurant.service.OrderItemService;
import softuni.restaurant.service.OrderService;
import softuni.restaurant.service.UserService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    @Override
    public void initializeOrders() {
        if (orderRepository.count() == 0) {
            OrderEntity orderEntity = new OrderEntity();
            OrderItemEntity one = orderItemService.getOrderItemById(1L);
            OrderItemEntity two = orderItemService.getOrderItemById(2L);
            UserEntity customer = userService.getUserBYId(3L);
            orderEntity.setItems(Set.of(one, two));
            orderEntity.setCustomer(customer).setAddress("Near restaurant address 23 ").setPhone("0888888888").setStatus(OrderStatusEnum.NEW);
            orderRepository.save(orderEntity);

            OrderEntity orderEntity1 = new OrderEntity();
            OrderItemEntity three = orderItemService.getOrderItemById(3L);
            UserEntity customer1 = userService.getUserBYId(3L);
            orderEntity1.setItems(Set.of(three));
            orderEntity1.setCustomer(customer1).setAddress("Far from restaurant address 23 ").setPhone("099999999").setStatus(OrderStatusEnum.SEND).setEmail("newEmail@email");
            orderRepository.save(orderEntity1);

        }
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAllOrderByCreatedNewest();
    }

    @Override
    public void setNewStatus(Long id, UserEntity userEntity) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order not found"));
        order.setStatus(order.getStatus().ordinal() == 3 ? order.getStatus() : OrderStatusEnum.values()[order.getStatus().ordinal() + 1]);
        order.setEmployee(userEntity);
        orderRepository.save(order);
    }

    @Override
    public OrderEntity findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order witt id " + id + " has not been found."));
    }

    @Override
    public void deleteOrder(OrderEntity orderEntity) {
        try {
            Set<OrderItemEntity> orderItems = orderEntity.getItems();
            orderEntity.setItems(new HashSet<>());

            orderRepository.delete(orderEntity);

        } catch (Exception e) {

        }
    }

    @Override
    public void deleteAllOldOrders() {
        List<OrderEntity> aldOrders = orderRepository.findAllAldOrders();
        aldOrders.forEach(this::deleteOrder);

    }

}
