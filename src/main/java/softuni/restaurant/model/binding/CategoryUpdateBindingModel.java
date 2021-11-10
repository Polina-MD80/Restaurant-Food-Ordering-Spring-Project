package softuni.restaurant.model.binding;

import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.model.validator.UniqueCategoryName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryUpdateBindingModel {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "The category name must contain at least 2 symbols")
    private String name;
    private String description;
    private MultipartFile picture;


    public CategoryUpdateBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public CategoryUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public CategoryUpdateBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
