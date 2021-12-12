package softuni.restaurant.model.entity;

import softuni.restaurant.model.entity.enums.OrderStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{
    @OneToMany
    private Set<OrderItemEntity> items = new HashSet<>();
    @ManyToOne
    private UserEntity customer;
    @ManyToOne
    private UserEntity employee;
    @Column(nullable = false)
    private String address;
    private String email;
    @Column(nullable = false)
    private String phone;
    private BigDecimal total;
    private OrderStatusEnum status;
    private LocalDateTime created;

    public OrderEntity() {
    }

    public Set<OrderItemEntity> getItems() {
        return items;
    }

    public OrderEntity setItems(Set<OrderItemEntity> items) {
        this.items = items;
        return this;
    }

    public UserEntity getCustomer() {
        return customer;
    }

    public OrderEntity setCustomer(UserEntity customer) {
        this.customer = customer;
        return this;
    }

    public UserEntity getEmployee() {
        return employee;
    }

    public OrderEntity setEmployee(UserEntity employee) {
        this.employee = employee;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OrderEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderEntity setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public OrderEntity setStatus(OrderStatusEnum status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public OrderEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
    @PrePersist
    public void beforeCreate() {
        this.created = LocalDateTime.now();
        this.total = items.stream().map(OrderItemEntity::getSubtotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
