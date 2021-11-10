package softuni.restaurant.model.service;

import softuni.restaurant.model.entity.PictureEntity;
import softuni.restaurant.model.view.PictureViewModel;

public class CategoryServiceModel {
    private Long id;
    private String name;
    private String description;
    private PictureServiceModel picture;

    public CategoryServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public CategoryServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureServiceModel getPicture() {
        return picture;
    }

    public CategoryServiceModel setPicture(PictureServiceModel picture) {
        this.picture = picture;
        return this;
    }


}
