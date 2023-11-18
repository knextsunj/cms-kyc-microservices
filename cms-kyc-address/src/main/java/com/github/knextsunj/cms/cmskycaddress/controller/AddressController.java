package com.github.knextsunj.cms.cmskycaddress.controller;


import com.github.knextsunj.cms.cmskycaddress.dto.AddressDTO;
import com.github.knextsunj.cms.cmskycaddress.exception.BusinessException;
import com.github.knextsunj.cms.cmskycaddress.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean saveAddress(@RequestBody AddressDTO addressDTO) {
        logger.info(addressDTO.toString());
        return addressService.saveAddress(addressDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public boolean updateAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.updateAddress(addressDTO);
    }

    @RequestMapping(value = "/fetchAll/{customerId}", method = RequestMethod.GET)
    public List<AddressDTO> findAllAddress(@PathVariable("customerId") Long customerId) throws ExecutionException, InterruptedException {

        try {
            return addressService.fetchAllAddress(customerId).get();
        }
        catch(ExecutionException executionException) {
            logger.error("Failed to get addresses for customerId:{}, due to",executionException);
            throw new BusinessException("Failed to get addresses for customerId"+customerId);
        }
        catch(InterruptedException interruptedException) {
            logger.error("Failed to get addresses for customerId:{}, due to",interruptedException);
            throw new BusinessException("Failed to get addresses for customerId"+customerId);
        }
    }

}
