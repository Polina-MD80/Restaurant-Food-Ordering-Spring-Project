package softuni.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.restaurant.model.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);

    @Query("select c from CategoryEntity c order by c.name")
    List<CategoryEntity> findAllOrderedByName();


    Optional<CategoryEntity> findByName(String name);
}
