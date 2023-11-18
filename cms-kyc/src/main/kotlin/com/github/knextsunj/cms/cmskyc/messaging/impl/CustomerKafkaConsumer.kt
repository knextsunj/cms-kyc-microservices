package com.github.knextsunj.cms.cmskyc.messaging.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.knextsunj.cms.cmskyc.dto.CustomerDTO
import com.github.knextsunj.cms.cmskyc.messaging.KafkaConsumer
import com.github.knextsunj.cms.cmskyc.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.apache.commons.lang3.tuple.Pair
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
@Qualifier("customerKafkaConsumer")
open class CustomerKafkaConsumer:KafkaConsumer {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CustomerKafkaConsumer::class.java)
    }

    @Autowired
    open lateinit var customerService: CustomerService

    @Autowired
    open lateinit var objectMapper: ObjectMapper

    @KafkaListener(topics = ["cms-kyc-events-customer"], groupId = "test-consumer-group")
    override fun receive(message: String?) {
        log.error("Received customer data: {}",message)
        val custPair:Pair<CustomerDTO,String> = objectMapper.readValue(message,Pair::class.java) as Pair<CustomerDTO,String>

       val customerDTO:CustomerDTO = custPair.left
        val headers:String = custPair.right
        when(customerDTO.deleted) {
            "Y" -> customerService.updateCustomer(customerDTO,headers)
            "N" -> customerService.saveCustomer (customerDTO,headers)
        }
    }

 }