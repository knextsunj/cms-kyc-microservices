package com.github.knextsunj.cms.cmskyc.messaging.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.knextsunj.cms.cmskyc.dto.AddressDTO
import com.github.knextsunj.cms.cmskyc.dto.CustomerDTO
import com.github.knextsunj.cms.cmskyc.messaging.KafkaConsumer
import com.github.knextsunj.cms.cmskyc.service.AddressService
import org.apache.commons.lang3.tuple.Pair
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
@Qualifier("addressKafkaConsumer")
open class AddressKafkaConsumer: KafkaConsumer {


    @Autowired
    open lateinit var addressService: AddressService

    @Autowired
    open lateinit var objectMapper: ObjectMapper

    @KafkaListener(topics = ["cms-kyc-events-address"],groupId = "test-consumer-group")
    override fun receive(message: String?) {
        val addrPair: Pair<AddressDTO, String> = objectMapper.readValue(message,
            Pair::class.java) as Pair<AddressDTO, String>

        val addressDTO: AddressDTO = addrPair.left
        val headers:String = addrPair.right

        when(addressDTO.deleted) {
            "Y" -> addressService.updateAddress(addressDTO,headers)
            "N" -> addressService.saveAddress (addressDTO,headers)
        }
    }
}