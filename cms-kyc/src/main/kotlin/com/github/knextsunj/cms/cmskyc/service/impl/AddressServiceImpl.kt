package com.github.knextsunj.cms.cmskyc.service.impl

import com.github.knextsunj.cms.cmskyc.dto.AddressDTO
import com.github.knextsunj.cms.cmskyc.service.AddressService
import com.github.knextsunj.cms.cmskyc.service.BaseServiceImpl
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Service
open class AddressServiceImpl : AddressService, BaseServiceImpl() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AddressServiceImpl::class.java)
    }

    @Autowired
    open lateinit var environment: Environment

    @Autowired
    open lateinit var restTemplate: RestTemplate

    /**
     * Computed property using abstract parent java class field as backing field
     */
    private val addressBaseUrl:String
        get():String{
            baseAddressUrl  = environment.getProperty("cms-kyc.address.base.url")
            return baseAddressUrl
        }


    @CircuitBreaker(name = "saveOrUpdateAddressCallCircuitBreaker", fallbackMethod = "logSaveOrUpdateAddressCallFailure")
    override fun saveAddress(addressDTO: AddressDTO?,authHeader:String): Boolean {
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val fullUrl = addressBaseUrl+"/save"
        val entity = HttpEntity(addressDTO, headers)
        logger.info("Current time is::: {}", LocalDateTime.now());
        return restTemplate.postForObject(fullUrl, entity, Boolean::class.java)!!
    }

    @CircuitBreaker(name = "saveOrUpdateAddressCallCircuitBreaker", fallbackMethod = "logSaveOrUpdateAddressCallFailure")
    override fun updateAddress(addressDTO: AddressDTO?,authHeader:String): Boolean {
//        val url = environment.get("cms-kyc-address.update.url")
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val fullUrl = addressBaseUrl+"/update"
        val request: HttpEntity<AddressDTO> = HttpEntity(addressDTO,headers)
        val response: ResponseEntity<Boolean> = restTemplate.exchange(fullUrl, HttpMethod.PUT, request, Boolean::class.java)
        return response.body
    }


    override fun fetchAllAddress(customerId: Long?,authHeader:String): MutableList<AddressDTO> {
//        val url: String? = environment.get("cms-kyc-address.findall.url")
        val fullUrl = addressBaseUrl+"/fetchAll/"+customerId
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val request = HttpEntity(null,headers)
        val responseEntity = restTemplate.getForEntity(fullUrl, MutableList::class.java,request)
        return responseEntity.body as MutableList<AddressDTO>
    }

    fun saveOrUpdateAddress(addressDTO: AddressDTO?,exception: RuntimeException):Boolean {
        return false;
    }

    private fun logSaveOrUpdateAddressCallFailure(addressDTO: AddressDTO?,authHeader:String,exception: RuntimeException):Boolean {
//        logger.error("Failed to save or update address ",exception)
        logger.error("logSaveOrUpdateAddressCallFailure called")
        return false
    }
}