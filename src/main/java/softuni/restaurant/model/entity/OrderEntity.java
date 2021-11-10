package softuni.restaurant.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private DeliveryAddress address;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean isComplete;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false)
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<OrderDetailEntity> details = new LinkedList<>();

    public OrderEntity() {
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderEntity setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<OrderDetailEntity> getDetails() {
        return details;
    }

    public OrderEntity setDetails(List<OrderDetailEntity> details) {
        this.details = details;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public DeliveryAddress getAddress() {
        return address;
    }

    public OrderEntity setAddress(DeliveryAddress address) {
        this.address = address;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public OrderEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public OrderEntity setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public OrderEntity setComplete(boolean complete) {
        isComplete = complete;
        return this;
    }
    @PrePersist
    public void beforeCreate() {
        this.created = LocalDateTime.now();
    }

    @PostPersist
    public void onUpdate() {
        this.modified = LocalDateTime.now();

    }

}
