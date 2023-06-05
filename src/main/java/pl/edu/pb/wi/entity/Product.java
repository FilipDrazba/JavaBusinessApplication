package pl.edu.pb.wi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;

    public void setUpdatableValuesFrom(Product other) {
        if (other.getName() != null) {
            this.name = other.name;
        }
        if (other.price != null) {
            this.price = other.price;
        }
    }
}
