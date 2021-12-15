package softuni.restaurant.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.restaurant.model.entity.CategoryEntity;
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

    @Query("select i from ItemEntity i where i.type='FOOD'")
    List<ItemEntity> allFoods();

    @Query("select i from ItemEntity i where i.type='DRINK'")
    List<ItemEntity> allDrinks();

    @Query("select i from ItemEntity i where i.type='OTHER'")
    List<ItemEntity> allOther();

    @Query("SELECT i FROM ItemEntity i WHERE i.name LIKE %?1%")
    List<ItemEntity> search(String keyword);


}
