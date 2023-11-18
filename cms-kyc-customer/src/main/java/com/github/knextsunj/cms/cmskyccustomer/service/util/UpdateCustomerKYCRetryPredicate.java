package com.github.knextsunj.cms.cmskyccustomer.service.util;

import com.github.knextsunj.cmskyccustomer.dto.CustomerDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

public class UpdateCustomerKYCRetryPredicate implements Predicate<Pair> {

    @Override
    public boolean test(Pair pair) {
        CustomerDTO customerDTO = (CustomerDTO) pair.getLeft();
        if(customerDTO.getDeleted().equals("Y")) {
            return false;
        }
        return true;
    }
}
