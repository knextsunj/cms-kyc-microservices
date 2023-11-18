package com.github.knextsunj.cms.cmskyccustomer.controller;

import com.github.knextsunj.cms.cmskyccustomer.service.CustomerService;
import com.github.knextsunj.cmskyccustomer.dto.CustomerDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/fetchAll")
    public List<CustomerDTO> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @PostMapping(value = "/save")
    public boolean saveCustomer(@RequestBody CustomerDTO customerDTO) {
        logger.info("Obtained customer data from CMS KYC microservice: {}",customerDTO);
        return customerService.saveCustomer(customerDTO);
    }

    @PutMapping(value = "/update")
    public boolean updateCustomer(@RequestBody CustomerDTO customerDTO) {
        Pair pair = customerService.updateCustomer(customerDTO);
        return (Boolean) pair.getRight();
    }

    @GetMapping(value = "/fetchAllDeleted")
    public List<CustomerDTO> findAllDeletedCustomers() {
        return customerService.findAllDeletedCustomers();
    }

}
