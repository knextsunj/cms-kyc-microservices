package com.github.knextsunj.cms.cmskyccustomer.service.impl;


import com.github.knextsunj.cms.cmskyccustomer.exception.BusinessException;
import com.github.knextsunj.cms.cmskyccustomer.mapper.CustomerMapper;
import com.github.knextsunj.cms.cmskyccustomer.repository.CustomerRepository;
import com.github.knextsunj.cms.cmskyccustomer.service.CustomerService;
import com.github.knextsunj.cmskyccustomer.domain.Customer;
import com.github.knextsunj.cmskyccustomer.dto.CustomerDTO;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RetryRegistry retryRegistry;

    @Override
    @Retry(name="saveCustomerForKyc",fallbackMethod = "saveCustomerFallback")
    public boolean saveCustomer(CustomerDTO customerDTO) {
        logger.debug("Saving data for customer kyc: {}",customerDTO);
        Customer customer = customerRepository.insert(customerMapper.fromCustomerDTO(customerDTO));
        return customer != null ? true : false;
    }

    @Override
    @Retry(name="updateCustomerKyc",fallbackMethod = "updateCustomerFallback")
    public Pair updateCustomer(CustomerDTO customerDTO) {

        boolean result = false;

        if (customerDTO.getDeleted().equals("Y")) {
            Optional<Customer> customerOptional = customerRepository.findById(customerDTO.getId());
            if (customerOptional.isPresent()) {
                try {
                    Customer customer = customerOptional.get();
                    customer.setDeleted("Y");
                    customer = customerRepository.save(customer);
                    result = customer != null ? true : false;
                } catch (Exception ex) {
                    logger.error("Failed to delete customer", ex);
                    throw new BusinessException("Failed to delete customer",ex);
                }
            }
        }
        Customer customer = customerRepository.save(customerMapper.fromCustomerDTO(customerDTO));
        result = customer != null ? true : false;
        return Pair.of(customerDTO,result);
    }

    @Override
    @Bulkhead(name="findAllCustomerBulkhead",fallbackMethod = "findAllCustomersFallback")
    public List<CustomerDTO> findAllCustomers() {
        return customerRepository.findAllByCustomerStatusAndDeleted("ACTIVE", "N").stream().map(customer -> customerMapper.toCustomerDTO(customer)).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> findAllDeletedCustomers() {
        return customerRepository.findAllByDeleted("Y").stream().map(customer -> customerMapper.toCustomerDTO(customer)).collect(Collectors.toList());
    }

    private boolean saveCustomerFallback(CustomerDTO customerDTO, RuntimeException runtimeException) {
        return false;
    }

    private Pair updateCustomerFallback(CustomerDTO customerDTO, RuntimeException runtimeException) {
        return Pair.of(customerDTO,false);
    }

    private List<CustomerDTO> findAllCustomersFallback() {
        return Collections.emptyList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        retryRegistry.retry("saveCustomerForKyc")
                .getEventPublisher()
                .onRetry(System.out::println);

        retryRegistry.retry("updateCustomerKyc")
                .getEventPublisher()
                .onRetry(System.out::println);
    }


}
