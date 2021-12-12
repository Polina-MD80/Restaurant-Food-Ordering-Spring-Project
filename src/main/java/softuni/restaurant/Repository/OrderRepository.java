package softuni.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
