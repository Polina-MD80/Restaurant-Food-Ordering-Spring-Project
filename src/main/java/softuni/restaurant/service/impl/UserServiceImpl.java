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
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
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
    public boolean registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) {


        UserEntity newUser = new UserEntity();

        newUser.
                setUsername(userRegistrationServiceModel.getUsername()).
                setEmail(userRegistrationServiceModel.getEmail()).
                setActive(true).
                setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword())).
                setRole(RoleEnum.CUSTOMER);
        try {
            newUser = userRepository.save(newUser);
        } catch (Exception e) {
            return false;
        }


        UserDetails principal = restaurantUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);

        return true;
    }

    public boolean isUserNameFree(Long id, String username) {

        if (userRepository.findByUsernameIgnoreCase(username).isEmpty()) {
            return true;
        }

        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        boolean isCreatingNewUser = (id == null);
        if (userEntity != null && !isCreatingNewUser) {
            if (id.equals(userEntity.getId())) {
                return true;
            } else return false;
        }
         if (isCreatingNewUser && userEntity!=null){
             return false;
         }

        return true;
    }


    @Override
    public void initUsers() {

        if (userRepository.count() == 0) {

            UserEntity admin = new UserEntity("admin", passwordEncoder.encode("admin"), "admin@admin", RoleEnum.ADMIN, true);
            UserEntity employee = new UserEntity("employee", passwordEncoder.encode("employee"), "employee@employee", RoleEnum.EMPLOYEE, true);
            UserEntity customer = new UserEntity("customer", passwordEncoder.encode("customer"), "customer@customer", RoleEnum.CUSTOMER, true);

            userRepository.saveAll(List.of(admin, employee, customer));
        }
    }

    @Override
    public UserEntity getUserByLoggedInUser(RestaurantUser user) {

        if (user == null) {
            return null;
        }

        String userIdentifier = user.getUserIdentifier();
        return userRepository.findByUsername(userIdentifier).orElse(null);

    }

    @Override
    public List<UserEntity> allUsers() {

        return userRepository.findAll();
    }

    @Override
    public void saveUser(UserEntity user) {
        boolean updating = (user.getId() != null);
        if (updating) {
            UserEntity updatedUser = userRepository.getById(user.getId());
            if (!user.getPassword().equals("")) {
                encodePassword(user);
            }else {
                user.setPassword(updatedUser.getPassword());
            }
        }else {
            encodePassword(user);
        }
        userRepository.save(user);

    }

    @Override
    public UserEntity getUserBYId(Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ObjectNotFoundException("There is no user with id " + id);
        }

    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity userEntity = this.getUserBYId(id);
        userRepository.delete(userEntity);
    }

    @Override
    public boolean usernameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    private void encodePassword(UserEntity user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
    }

}