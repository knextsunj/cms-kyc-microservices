package com.github.knextsunj.cms.cmskyccustomer.service;


import com.github.knextsunj.cmskyccustomer.dto.CustomerDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CustomerService {

    boolean saveCustomer(CustomerDTO customerDTO);

    Pair updateCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> findAllCustomers();

    List<CustomerDTO> findAllDeletedCustomers();

}
