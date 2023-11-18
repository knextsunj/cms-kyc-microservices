package com.github.knextsunj.cms.cmskyc.service;


import com.github.knextsunj.cms.cmskyc.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    boolean saveCustomer(CustomerDTO customerDTO,String authHeader);

    boolean updateCustomer(CustomerDTO customerDTO,String authHeader);

    List<CustomerDTO> findAllCustomers(String authHeader);

    List<CustomerDTO> findAllDeletedCustomers(String authHeader);

}
