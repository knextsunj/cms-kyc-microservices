package com.github.knextsunj.cmskyccustomer.util


import com.github.knextsunj.cmskyccustomer.domain.Customer
import com.github.knextsunj.cmskyccustomer.dto.CustomerDTO

open class MapperUtil {

    companion object {


        @JvmStatic
        fun updateCustomerDetails(customerDTO: CustomerDTO, customer: Customer): Customer {
            if (!CmsUtil.isNull(customerDTO.name) && !customerDTO.name.equals(customer.name)) {
                customer.name = customerDTO.name;
            }

            if (!CmsUtil.isNull(customerDTO.deleted) && !customerDTO.deleted.equals(customer.deleted)) {
                customer.deleted = customerDTO.deleted;
            }

            if (!CmsUtil.isNull(customerDTO.dob) && !customerDTO.dob.equals(customer.dateOfBirth)) {
                customer.dateOfBirth = customerDTO.dob;
            }

            if (!CmsUtil.isNull(customerDTO.emailAddress) && !customerDTO.emailAddress.equals(customer.emailAddress)) {
                customer.emailAddress = customerDTO.emailAddress;
            }

            if (!CmsUtil.isNull(customerDTO.gender) && !customerDTO.gender.equals(customer.gender)) {
                customer.gender = customerDTO.gender;
            }

            if (!CmsUtil.isNull(customerDTO.mobileNo) && !customerDTO.mobileNo.equals(customer.mobileNo)) {
                customer.mobileNo = customerDTO.mobileNo;
            }

            return customer;
        }

    }


}
