package com.weather_app.service;
import com.weather_app.exception.EntityValidationException;
import com.weather_app.exception.ExceptionPayload;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.weather_app.model.Customer;
import com.weather_app.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService extends BasicServiceOperations<CustomerRepository,Customer>{
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }
    @Override
  public Customer save(Customer entity) {
    if (entity.getId() == null) {
       entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    } else {
        Customer user = repository.findById(entity.getId())
          .orElseThrow(() -> new EntityNotFoundException("No entity found with id: " + entity.getId()));
      entity.setPassword(user.getPassword());
    }

    return super.save(entity);
  }

  @Override
  protected void validateEntity(Customer entity) throws EntityValidationException {

    Customer existingCustomer = repository.findByEmail(entity.getEmail()).orElse(null);

    if (existingCustomer != null && !existingCustomer.getId().equals(entity.getId())) {
      throw new EntityValidationException(ExceptionPayload.builder()
          .code("DuplicateEmail")
          .fieldName("email")
          .rejectedValue(entity.getEmail())
          .message("This email already exists")
          .build()
      );
    }
  }

    public Customer login(String email, String password) {
        Customer customer = this.repository.findByEmail(email)
                .orElseThrow(() -> new EntityValidationException(ExceptionPayload.builder()
                        .code("WrongEmail")
                        .fieldName("email")
                        .rejectedValue(email)
                        .message("Wrong email")
                        .build())
                );
        if (!customer.getPassword().equals(password)) {
            throw new EntityValidationException(ExceptionPayload.builder()
                    .code("WrongPassword")
                    .fieldName("password")
                    .rejectedValue(password)
                    .message("Wrong password")
                    .build());
        }

        return customer;
    }
}