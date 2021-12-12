package softuni.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
