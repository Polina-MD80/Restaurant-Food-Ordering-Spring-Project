package softuni.restaurant.model.view;


import softuni.restaurant.model.entity.UserEntity;

import javax.persistence.Transient;
import java.math.BigDecimal;


public class CartDetailViewModel {
    private ItemViewModel item;
    private UserEntity user;
    private Integer quantity;

    public ItemViewModel getItem() {
        return item;
    }

    public CartDetailViewModel setItem(ItemViewModel item) {
        this.item = item;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CartDetailViewModel setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartDetailViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
    @Transient
    public BigDecimal getSubTotal() {
        return item.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}