package softuni.restaurant.model.view;

public class AllergenViewModel {
    private Long id;
    private String name;
    private String imageUrl;

    public AllergenViewModel() {
    }

    public Long getId() {
        return id;
    }

    public AllergenViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AllergenViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AllergenViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
