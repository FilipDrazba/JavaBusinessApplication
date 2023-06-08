package pl.edu.pb.wi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Customer extends User {

    @ManyToMany
    List<Product> shoppingCart;
}
