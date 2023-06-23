package pl.edu.pb.wi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pb.wi.entity.Product;
import java.math.BigDecimal;

class ProductTest {

    @Test
    void setUpdatableValuesFrom_WhenOtherNameNotNull_ShouldSetProductName() {
        Product product = new Product();
        Product other = new Product();
        other.setName("New Product");

        product.setUpdatableValuesFrom(other);

        Assertions.assertEquals("New Product", product.getName());
    }

    @Test
    void setUpdatableValuesFrom_WhenOtherNameNull_ShouldNotSetProductName() {
        Product product = new Product();
        product.setName("Existing Product");
        Product other = new Product();

        product.setUpdatableValuesFrom(other);

        Assertions.assertEquals("Existing Product", product.getName());
    }

    @Test
    void setUpdatableValuesFrom_WhenOtherPriceNotNull_ShouldSetProductPrice() {
        Product product = new Product();
        Product other = new Product();
        other.setPrice(BigDecimal.valueOf(9.99));

        product.setUpdatableValuesFrom(other);

        Assertions.assertEquals(BigDecimal.valueOf(9.99), product.getPrice());
    }

    @Test
    void setUpdatableValuesFrom_WhenOtherPriceNull_ShouldNotSetProductPrice() {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5.99));
        Product other = new Product();

        product.setUpdatableValuesFrom(other);

        Assertions.assertEquals(BigDecimal.valueOf(5.99), product.getPrice());
    }
}
