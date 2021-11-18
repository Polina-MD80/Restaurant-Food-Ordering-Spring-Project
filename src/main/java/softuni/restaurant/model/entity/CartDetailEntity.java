package softuni.restaurant.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_detail")
public class CartDetailEntity extends BaseEntity{
    @ManyToOne
    private ItemEntity item;
    @ManyToOne
    private UserEntity user;
    @Column(nullable = false)
    private Integer quantity;

    public CartDetailEntity() {
    }

    public ItemEntity getItem() {
        return item;
    }

    public CartDetailEntity setItem(ItemEntity item) {
        this.item = item;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CartDetailEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartDetailEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
