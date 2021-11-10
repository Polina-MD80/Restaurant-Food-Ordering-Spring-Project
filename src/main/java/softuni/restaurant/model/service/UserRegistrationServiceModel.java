package softuni.restaurant.model.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationServiceModel {

    private String username;
    private String email;
    private String password;


    public String getUsername() {
        return username != null ?
                username.trim() :
                null;
    }

    public UserRegistrationServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
