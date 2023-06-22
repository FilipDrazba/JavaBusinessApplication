package pl.edu.pb.wi.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Product> products;

    @ManyToOne
    private User owner;

    @Transient
    private BigDecimal amount;

    public Order(Long id, List<Product> products, User owner) {
        this.id = id;
        this.products = products;
        this.owner = owner;
    }

    public BigDecimal getAmount() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
