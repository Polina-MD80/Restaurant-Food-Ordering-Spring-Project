package softuni.restaurant.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "address")
@Entity
public class DeliveryAddress extends BaseEntity {
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    private Integer number;
    private Integer zipCode;

    public DeliveryAddress() {
    }

    public String getCity() {
        return city;
    }

    public DeliveryAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public DeliveryAddress setStreet(String street) {
        this.street = street;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public DeliveryAddress setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public DeliveryAddress setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
        return this;
    }
}