package com.github.knextsunj.cms.cmskyccustomer.repository;

import com.github.knextsunj.cmskyccustomer.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer,Long> {

    Customer findByName(String name);

    List<Customer> findAllByCustomerStatusAndDeleted(String customerStatus, String deleted);

    List<Customer> findAllByDeleted(String deleted);
}
