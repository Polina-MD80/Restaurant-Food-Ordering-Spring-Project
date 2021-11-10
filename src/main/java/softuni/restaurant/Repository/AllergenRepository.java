package softuni.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.AllergenEntity;

public interface AllergenRepository extends JpaRepository<AllergenEntity, Long> {

}
