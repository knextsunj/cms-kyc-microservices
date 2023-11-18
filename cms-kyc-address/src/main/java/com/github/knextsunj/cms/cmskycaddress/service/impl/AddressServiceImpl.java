package com.github.knextsunj.cms.cmskycaddress.service.impl;


import com.github.knextsunj.cms.cmskycaddress.domain.Address;
import com.github.knextsunj.cms.cmskycaddress.dto.AddressDTO;
import com.github.knextsunj.cms.cmskycaddress.exception.BusinessException;
import com.github.knextsunj.cms.cmskycaddress.mapper.AddressMapper;
import com.github.knextsunj.cms.cmskycaddress.repository.AddressRepository;
import com.github.knextsunj.cms.cmskycaddress.service.AddressService;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.timelimiter.TimeLimiter.EventPublisher;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RetryRegistry retryRegistry;

    @Autowired
    private TimeLimiterRegistry timeLimiterRegistry;


    @Override
    @Retry(name = "saveAddressKyc",fallbackMethod = "saveAddressFallback")
    public boolean saveAddress(AddressDTO addressDTO) {
//        throw new RuntimeException("test exception");
        Address address = addressRepository.insert(addressMapper.fromAddressDTO(addressDTO));
        return address != null ? true : false;
//        return false;
    }

    @Override
    @Retry(name = "updateAddressKyc",fallbackMethod = "updateAddressFallback")
    public boolean updateAddress(AddressDTO addressDTO) {

        if (addressDTO.getDeleted().equals("Y")) {
            Optional<Address> customerOptional = addressRepository.findById(addressDTO.getId());
            if (customerOptional.isPresent()) {
                try {
                    Address address = customerOptional.get();
                    address.setDeleted("Y");
                    address = addressRepository.save(address);
                    return address != null ? true : false;
                } catch (Exception ex) {
                    logger.error("Failed to delete customer", ex);
                    throw new BusinessException("Failed to delete customer", ex);
                }
            }
        }
        Address customer = addressRepository.save(addressMapper.fromAddressDTO(addressDTO));
        return customer != null ? true : false;
    }

    @Override
    @TimeLimiter(name = "fetchAllAddressTimeLimiter",fallbackMethod = "fetchAllAddressFallback")
    public CompletableFuture<List<AddressDTO>> fetchAllAddress(Long customerId) throws InterruptedException {
        Thread.sleep(150000);
        CompletableFuture completableFuture = new CompletableFuture();
      return  CompletableFuture.supplyAsync(()->{
            for(int i=0;i<25000;i++) {
                logger.info("i= {}",i);
            }
            return Collections.emptyList();
        });
//        return CompletableFuture.supplyAsync(() ->addressRepository.findAddressByCustomerIdAndDeleted(customerId, "N").stream().map(address -> addressMapper.toAddressDTO(address)).collect(Collectors.toList()));
    }

    private boolean saveAddressFallback(AddressDTO addressDTO, RuntimeException runtimeException) {
        return false;
    }

    private boolean updateAddressFallback(AddressDTO addressDTO, RuntimeException runtimeException) {
        return false;
    }

    @PostConstruct
    public void init() {
        retryRegistry.retry("saveAddressKyc")
                .getEventPublisher()
                .onRetry(System.out::println);

        retryRegistry.retry("updateAddressKyc")
                .getEventPublisher()
                .onRetry(System.out::println);

        EventPublisher eventPublisher = timeLimiterRegistry.timeLimiter("fetchAllAddressTimeLimiter").getEventPublisher();
        eventPublisher.onSuccess(System.out::println);
        eventPublisher.onError(System.out::println);
        eventPublisher.onTimeout(System.out::println);
    }

    private CompletableFuture<List<AddressDTO>> fetchAllAddressFallback(Long customerId, TimeoutException timeoutException) {
        CompletableFuture<List<AddressDTO>> result = new CompletableFuture<>();
        result.complete(Collections.emptyList());
        return result;
    }
}
