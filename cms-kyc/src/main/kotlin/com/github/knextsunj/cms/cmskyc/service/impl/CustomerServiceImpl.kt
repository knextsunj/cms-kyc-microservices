package com.github.knextsunj.cms.cmskyc.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.knextsunj.cms.cmskyc.dto.AddressDTO
import com.github.knextsunj.cms.cmskyc.dto.CustomerDTO
import com.github.knextsunj.cms.cmskyc.service.BaseServiceImpl
import com.github.knextsunj.cms.cmskyc.service.CustomerService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.RequestNotPermitted
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
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
open class CustomerServiceImpl : CustomerService, BaseServiceImpl() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CustomerServiceImpl::class.java)
    }

    @Autowired
    open lateinit var environment: Environment

    @Autowired
    open lateinit var objectMapper: ObjectMapper

    @Autowired
    open lateinit var restTemplate: RestTemplate

    /**
     * Computed property using parent abstract java class field as backing field
     */
    private val customerBaseUrl:String
        get():String{
           baseCustomerUrl  = environment.getProperty("cms-kyc.customer.base.url")
            return baseCustomerUrl
        }


    @CircuitBreaker(name = "saveOrUpdateCustomerCallCircuitBreaker", fallbackMethod = "logSaveOrUpdateCustomerCallFailure")
    override fun saveCustomer(customerDTO: CustomerDTO?,authHeader:String): Boolean {
        val fullUrl = customerBaseUrl+"/save"
//        val url = environment.get("cms-kyc-customer.save.url")
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val entity = HttpEntity(customerDTO, headers)
        logger.info("Calling customer kyc microservice for posting data: {}",customerDTO)
        logger.info("Current time is::: {}",LocalDateTime.now());
        return restTemplate.postForObject(fullUrl, entity, Boolean::class.java)!!
    }

    @CircuitBreaker(name = "saveOrUpdateCustomerCallCircuitBreaker", fallbackMethod = "logSaveOrUpdateCustomerCallFailure")
    override fun updateCustomer(customerDTO: CustomerDTO?,authHeader:String): Boolean {
        val fullUrl = customerBaseUrl+"/update"
//        val url = environment.get("cms-kyc-customer.update.url")
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val request: HttpEntity<CustomerDTO> = HttpEntity(customerDTO,headers)
        val response: ResponseEntity<Boolean> = restTemplate.exchange(fullUrl, HttpMethod.PUT, request, Boolean::class.java)
        return response.body
    }

    override fun findAllCustomers(authHeader:String): MutableList<CustomerDTO> {
//        val url: String? = environment.get("cms-kyc-customer.findall.url")
        val fullUrl = customerBaseUrl+"/fetchAll"
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val request = HttpEntity(null,headers)
        val responseEntity = restTemplate.getForEntity(fullUrl, MutableList::class.java,request)
        return responseEntity.body as MutableList<CustomerDTO>
    }

    @RateLimiter(name = "findAllDeletedCustomersRateLimiter", fallbackMethod = "findAllDeletedCustomersFallback")
    override fun findAllDeletedCustomers(authHeader:String): List<CustomerDTO> {
        val fullUrl = customerBaseUrl+"/fetchAllDeleted"
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION,authHeader)
        val request = HttpEntity(null,headers)
        val responseEntity = restTemplate.getForEntity(fullUrl, MutableList::class.java,request)
        return responseEntity.body as MutableList<CustomerDTO>
    }

    private fun findAllDeletedCustomersFallback(rnp: RequestNotPermitted):List<CustomerDTO> {
        logger.error("Timeout exception when fetching all deleted customers",rnp);
        return emptyList()
    }

    private fun logSaveOrUpdateCustomerCallFailure(customerDTO: CustomerDTO?, authHeader:String, exception: RuntimeException):Boolean {
//       logger.error("Failed to save or update address ",exception)
        logger.error("logSaveOrUpdateCustomerCallFailure called")
        return false
    }
}