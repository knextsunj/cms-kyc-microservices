package com.github.knextsunj.cms.cmskyc.controller;

import com.github.knextsunj.cms.cmskyc.dto.CustomerDTO;
import com.github.knextsunj.cms.cmskyc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/findAll")
    public List<CustomerDTO> findAllCountries(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return customerService.findAllCustomers(authHeader);
    }

    @PostMapping(value = "/save")
    public boolean saveCountry(@RequestBody CustomerDTO customerDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return customerService.saveCustomer(customerDTO,authHeader);
    }

    @PutMapping(value = "/update")
    public boolean updateCountry(@RequestBody CustomerDTO customerDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return customerService.updateCustomer(customerDTO,authHeader);
    }

    @GetMapping(value="/findAllDeletedCustomers")
    public List<CustomerDTO> findAllDeletedCustomers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return customerService.findAllDeletedCustomers(authHeader);
    }

}
