package com.github.knextsunj.cms.cmskycaddress.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigInteger
import java.time.LocalDateTime

@Document(collection = "Address")
open class Address {

    @Id
    open var id: String? = null;

    open var name: String? = null;

    open var deleted: String? = null

    open var createdDate: LocalDateTime? = null;

    open var updatedDate: LocalDateTime? = null;

    open var street: String? = null;

    open var locality: String? = null;

    open var area: String? = null;

    open var pincode: Long? = 0;

    open var city: String? = null

    open var state: String? = null

    open var country: String? = null

    open var addressType: String? = null

    open var customer: String? = null

    open var customerId:Long? = null;

}