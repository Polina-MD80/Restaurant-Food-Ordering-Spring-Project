package softuni.restaurant.service;


import org.springframework.security.access.prepost.PreAuthorize;
import softuni.restaurant.model.service.UserRegistrationServiceModel;

public interface UserService {



  boolean registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

  boolean isUserNameFree(String username);

  void initUsers();


}
