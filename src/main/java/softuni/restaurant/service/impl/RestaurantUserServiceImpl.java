package softuni.restaurant.service.impl;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import softuni.restaurant.repository.UserRepository;
import softuni.restaurant.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantUserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public RestaurantUserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



    UserEntity userEntity =
        userRepository.findByUsername(username).
            orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));

    return mapToUserDetails(userEntity);
  }

  private static UserDetails mapToUserDetails(UserEntity userEntity) {


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
