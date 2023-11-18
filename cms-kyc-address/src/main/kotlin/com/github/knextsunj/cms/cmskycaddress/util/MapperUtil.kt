package com.github.knextsunj.cms.cmskycaddress.util

import com.github.knextsunj.cms.cmskycaddress.domain.Address
import com.github.knextsunj.cms.cmskycaddress.dto.AddressDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime



open class MapperUtil {

    companion object {

        val logger: Logger = LoggerFactory.getLogger(MapperUtil::class.java)

        @JvmStatic
        fun fromAddressDTO(addressDTO: AddressDTO?): Address {
            // !! is non-null asserted call
            logger.info("MapperUtil:{}",addressDTO.toString())
           return buildOrUpdateAddress(toUpdate=false,address=null,addressDTO= addressDTO!!)
        }

        @JvmStatic
        fun setAddressDates(address:Address):Address {
            if(!CmsUtil.isNull(address)) {
                if(CmsUtil.isNull(address?.createdDate)) {
                    address.createdDate = LocalDateTime.now()
                }
                address.updatedDate = LocalDateTime.now()

            }
            return address
        }

          private  fun buildOrUpdateAddress(addressDTO:AddressDTO,toUpdate:Boolean,address:Address?):Address {
              logger.info("MapperUtil-buildOrUpdateAddress:{}",addressDTO.toString())
              logger.info("toUpdate:{}",toUpdate)

            if(!toUpdate) {
                logger.info("Inside not toUpdate")
                var newAddress = Address()
                setAddressValues(addressDTO=addressDTO,address=newAddress)
                return newAddress
            }
              logger.info("Inside else")

              // !! is non-null asserted call
              setAddressValues(addressDTO = addressDTO, address = address!!)
              return address
        }


        private fun setAddressValues(address:Address,addressDTO:AddressDTO):Address {
            /**
             * The safe call(?) operator checks if addressDTO is null,if yes then null is assigned to address.name
             */
            if(address==null) {
                logger.info("Address is null")

            }
            address.name = addressDTO?.name
            address.deleted = addressDTO?.deleted
            address.street = addressDTO?.street
            address.locality = addressDTO?.locality
            address.area = addressDTO?.area
            address.pincode = addressDTO?.pincode
            address.city = addressDTO?.city
            address.state = addressDTO?.state
            address.country = addressDTO?.country
            address.customerId = addressDTO?.customerId

            return address
        }

    }


    }
