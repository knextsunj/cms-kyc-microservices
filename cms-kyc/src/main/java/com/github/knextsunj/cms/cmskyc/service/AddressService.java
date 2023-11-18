package com.github.knextsunj.cms.cmskyc.service;


import com.github.knextsunj.cms.cmskyc.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    boolean saveAddress(AddressDTO addressDTO,String authHeader);

    boolean updateAddress(AddressDTO addressDTO,String authHeader);

    List<AddressDTO> fetchAllAddress(Long customerId,String authHeader);

}
