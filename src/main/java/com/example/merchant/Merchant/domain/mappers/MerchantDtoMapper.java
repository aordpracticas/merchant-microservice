package com.example.merchant.Merchant.domain.mappers;


import com.example.merchant.Merchant.aplication.MerchantModel;
import com.example.merchant.Merchant.infrastructure.controller.DTO.MerchantOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface MerchantDtoMapper {

    MerchantModel toModel(MerchantOutputDto dto);

    @Mapping(source = "createdDate", target = "C" )


    @Named("dateToString")
    static String mapDateToString(Date date) {
        if (date == null) return null;

        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(date);

    }
}
