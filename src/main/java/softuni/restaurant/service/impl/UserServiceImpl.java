package softuni.restaurant.service.impl;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.restaurant.repository.UserRepository;
import softuni.restaurant.service.UserService;
import softuni.restaurant.model.entity.UserEntity;
import softuni.restaurant.model.entity.enums.RoleEnum;
import softuni.restaurant.model.service.UserRegistrationServiceModel;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RestaurantUserServiceImpl restaurantUserService;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RestaurantUserServiceImpl restaurantUserService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

        this.restaurantUserService = restaurantUserService;
    }


    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) {


        UserEntity newUser = new UserEntity();

        newUser.
                setUsername(userRegistrationServiceModel.getUsername()).
                setEmail(userRegistrationServiceModel.getEmail()).
                setActive(true).
                setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword())).
                setRole(RoleEnum.CUSTOMER);

        newUser = userRepository.save(newUser);

        // this is the Spring representation of a user
        UserDetails principal = restaurantUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);
    }

    public boolean isUserNameFree(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    @Override
    public void initUsers() {

        if (userRepository.count() == 0) {

            UserEntity admin = new UserEntity("admin", passwordEncoder.encode( "admin"), "admin@admin", RoleEnum.ADMIN, true);
            UserEntity employee = new UserEntity("employee", passwordEncoder.encode( "employee"), "employee@employee", RoleEnum.EMPLOYEE, true);
            UserEntity customer = new UserEntity("customer", passwordEncoder.encode( "customer"), "customer@customer", RoleEnum.CUSTOMER, true);

            userRepository.saveAll(List.of(admin, employee, customer));
        }
    }
}