package softuni.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.PictureEntity;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
}
