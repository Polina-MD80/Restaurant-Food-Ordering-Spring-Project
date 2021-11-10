package softuni.restaurant.model.view;

public class CategoryViewModel {
    private Long id;
    private String name;
    private String description;
    private PictureViewModel picture;

    public CategoryViewModel() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    public CategoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureViewModel getPicture() {
        return picture;
    }

    public CategoryViewModel setPicture(PictureViewModel picture) {
        this.picture = picture;
        return this;
    }
}
