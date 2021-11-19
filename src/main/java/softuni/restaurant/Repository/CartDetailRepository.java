package softuni.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.restaurant.model.entity.CartDetailEntity;
import softuni.restaurant.model.entity.ItemEntity;
import softuni.restaurant.model.entity.UserEntity;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Long> {
List<CartDetailEntity> findByUser(UserEntity userEntity);
CartDetailEntity findByUserAndItem(UserEntity user, ItemEntity item);


}
