package com.github.knextsunj.cms.cmskycaddress.repository;

import com.github.knextsunj.cms.cmskycaddress.domain.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, Long> {

    List<Address> findAddressByCustomerIdAndDeleted(Long customerId,String deleted);
}
