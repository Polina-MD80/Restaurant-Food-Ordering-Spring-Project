package softuni.restaurant.service;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.service.UserRegistrationServiceModel;
import softuni.restaurant.service.impl.RestaurantUser;

public interface UserService {



  boolean registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

  boolean isUserNameFree(String username);

  void initUsers();

  UserEntity getUserByLoggedInUser(RestaurantUser user);


}
