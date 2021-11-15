package softuni.restaurant.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByName(String name);

    @Override
    Optional<ItemEntity> findById(Long aLong);

    @Override
    List<ItemEntity> findAll(Sort sort);

    boolean existsByName(String name);
}
