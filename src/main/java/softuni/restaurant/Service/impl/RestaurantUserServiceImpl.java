package softuni.restaurant.Service.impl;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.restaurant.Repository.UserRepository;
import softuni.restaurant.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantUserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public RestaurantUserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // The purpose of this method is to map our user representation (UserEntity)
    // to the user representation in the spring sercurity world (UserDetails).
    // The only thing that spring will provide to us is the user name.
    // The user name will come from the HTML login form.

    UserEntity userEntity =
        userRepository.findByUsername(username).
            orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));

    return mapToUserDetails(userEntity);
  }

  private static UserDetails mapToUserDetails(UserEntity userEntity) {

    // GrantedAuthority is the representation of a user role in the
    // spring world. SimpleGrantedAuthority is an implementation of GrantedAuthority
    // which spring provides for our convenience.
    // Our representation of role is UserRoleEntity
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()));

    // User is the spring implementation of UserDetails interface.
    return new RestaurantUser(
        userEntity.getUsername(),
        userEntity.getPassword(),
        authorities
    );
  }
}
