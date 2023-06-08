package pl.edu.pb.wi.controlleer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.wi.controlleer.annotation.ApiRequestMapping;
import pl.edu.pb.wi.dto.response.CustomerDtoResponse;
import pl.edu.pb.wi.entity.Customer;
import pl.edu.pb.wi.mapper.CustomerMapper;
import pl.edu.pb.wi.service.CustomerService;

@RestController
@ApiRequestMapping
@RequiredArgsConstructor
public class CustomerController {
    public final CustomerService customerService;

    @GetMapping("customer/{id}")
    public ResponseEntity<CustomerDtoResponse> findById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        CustomerDtoResponse customerDtoResponse = CustomerMapper.INSTANCE.formCustomerToCustomerDtoResponse(customer);
        return ResponseEntity.ok(customerDtoResponse);
    }
}
