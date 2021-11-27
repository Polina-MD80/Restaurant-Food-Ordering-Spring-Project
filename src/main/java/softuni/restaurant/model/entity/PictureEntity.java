package softuni.restaurant.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static softuni.restaurant.constants.RestaurantConstantImages.DEFAULT_CATEGORY_IMAGE;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String url;
    @Column(nullable = false, unique = true)
    private String publicId = DEFAULT_CATEGORY_IMAGE ;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureEntity setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
