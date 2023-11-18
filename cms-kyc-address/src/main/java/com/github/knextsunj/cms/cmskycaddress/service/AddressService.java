package com.github.knextsunj.cms.cmskycaddress.service;



import com.github.knextsunj.cms.cmskycaddress.dto.AddressDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AddressService {

    boolean saveAddress(AddressDTO addressDTO);

    boolean updateAddress(AddressDTO addressDTO);

    CompletableFuture<List<AddressDTO>> fetchAllAddress(Long customerId) throws InterruptedException;

}
