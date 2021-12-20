package softuni.restaurant.model.binding;

import softuni.restaurant.model.validator.UniqueUserName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserRegistrationBindingModel {

  @NotBlank
  @Size(min=4, max=20, message = "Username must be between 4 and 20 characters.")
  @UniqueUserName
  private String username;
  @NotNull
  @Size(min = 4, max = 128)
  private String email;

  @NotNull
  @Size(min=4, max=20 ,message = "Password must be between 4 and 20 characters.")
  private String password;
  @NotNull
  @Size(min=4, max=20, message = "Password must be between 4 and 20 characters.")
  private String confirmPassword;

  public String getUsername() {
    return username;
  }

  public UserRegistrationBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }



  public String getPassword() {
    return password;
  }

  public UserRegistrationBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserRegistrationBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }
}
