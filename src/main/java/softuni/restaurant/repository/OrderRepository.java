package softuni.restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.restaurant.model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("select  o from  OrderEntity o order by o.id desc")
    List<OrderEntity> findAllOrderByCreatedNewest();

    @Query("select o from OrderEntity  o where o.status=3")
    List<OrderEntity> findAllAldOrders();
}
