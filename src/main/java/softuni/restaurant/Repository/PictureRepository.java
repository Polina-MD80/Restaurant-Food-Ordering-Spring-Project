package softuni.restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.PictureEntity;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
}
