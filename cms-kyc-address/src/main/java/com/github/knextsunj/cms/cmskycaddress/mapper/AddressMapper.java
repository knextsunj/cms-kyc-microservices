package com.github.knextsunj.cms.cmskycaddress.mapper;


import com.github.knextsunj.cms.cmskycaddress.domain.Address;
import com.github.knextsunj.cms.cmskycaddress.dto.AddressDTO;
import com.github.knextsunj.cms.cmskycaddress.util.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "locality", source = "locality")
    @Mapping(target = "area", source = "area")
    @Mapping(target = "pincode", source = "pincode")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "addressType", source = "addressType")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target="customerId",source="customerId")
    AddressDTO toAddressDTO(Address address);

    default Address fromAddressDTO(AddressDTO addressDTO) {
        return MapperUtil.fromAddressDTO(addressDTO);
    }

    default Address setDates(Address address) {
        return MapperUtil.setAddressDates(address);
    }
}
