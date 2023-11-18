package com.github.knextsunj.cmskyccustomer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "Customer")
open class Customer {

    @Id
    open var id: String? = null;

    open var name: String? = null;

    open var deleted: String? = null

    open var dateOfBirth: LocalDate? = null;

    open var gender: String? = null;

    open var mobileNo: Long? = null;

    open var emailAddress: String? = null;

    open var createdDate: LocalDateTime? = null;

    open var updatedDate: LocalDateTime? = null;

    open var customerStatus: String? = null

    open var customerId:Long? = null

}