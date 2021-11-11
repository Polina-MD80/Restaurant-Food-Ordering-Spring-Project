package softuni.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);
}
