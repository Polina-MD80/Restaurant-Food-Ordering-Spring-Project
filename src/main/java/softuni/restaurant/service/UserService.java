package softuni.restaurant.service;


import softuni.restaurant.model.service.UserRegistrationServiceModel;

public interface UserService {



  void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

  boolean isUserNameFree(String username);

  void initUsers();

}
