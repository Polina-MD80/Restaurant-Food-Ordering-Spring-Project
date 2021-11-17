package softuni.restaurant.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);

    @Override
    List<ProductEntity> findAll(Sort sort);

    Optional<ProductEntity> findByName(String name);
}
