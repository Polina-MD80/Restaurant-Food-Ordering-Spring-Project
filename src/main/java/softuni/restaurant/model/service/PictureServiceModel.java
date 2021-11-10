package softuni.restaurant.model.service;

public class PictureServiceModel {
    private Long id;
    private String publicId;
    private String url;

    public PictureServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public PictureServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureServiceModel setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureServiceModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
