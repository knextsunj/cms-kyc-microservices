package com.github.knextsunj.cms.cmskyc.controller;

import com.github.knextsunj.cms.cmskyc.dto.AddressDTO;
import com.github.knextsunj.cms.cmskyc.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean saveAddress(@RequestBody AddressDTO addressDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return addressService.saveAddress(addressDTO,authHeader);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public boolean updateAddress(@RequestBody AddressDTO addressDTO,@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return addressService.updateAddress(addressDTO,authHeader);
    }

    @RequestMapping(value = "/fetchAll/{customerId}", method = RequestMethod.GET)
    public List<AddressDTO> findAllAddress(@PathVariable("customerId") Long customerId,@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return addressService.fetchAllAddress(customerId,authHeader);
    }

}
