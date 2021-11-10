package softuni.restaurant.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "details")
public class OrderDetailEntity extends BaseEntity{
    @ManyToOne
    private ItemEntity item;
    @ManyToOne
    private OrderEntity order;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal sum;
    @Column(nullable = false)
    private BigDecimal price;

    public OrderDetailEntity() {
    }

    public ItemEntity getItem() {
        return item;
    }

    public OrderDetailEntity setItem(ItemEntity productEntity) {
        this.item = productEntity;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public OrderDetailEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderDetailEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public OrderDetailEntity setSum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderDetailEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
    @PrePersist
    private void setDefault(){
        setPrice(BigDecimal.ZERO);
        setSum(BigDecimal.ZERO);
        setQuantity(0);
    }
}
