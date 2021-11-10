package softuni.restaurant.model.view;

public class CategoryEditView {
    private Long id;
    private String name;
    private String description;
    private PictureViewModel picture;

    public CategoryEditView() {
    }

    public Long getId() {
        return id;
    }

    public CategoryEditView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEditView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEditView setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureViewModel getPicture() {
        return picture;
    }

    public CategoryEditView setPicture(PictureViewModel picture) {
        this.picture = picture;
        return this;
    }
}
