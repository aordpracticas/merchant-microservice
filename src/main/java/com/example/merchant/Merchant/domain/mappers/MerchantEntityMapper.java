package com.example.merchant.Merchant.domain.mappers;

import com.example.merchant.Merchant.aplication.MerchantModel;
import com.example.merchant.Merchant.domain.MerchantEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MerchantEntityMapper {

@Mapping(target = "normalizedName", source = "name", qualifiedByName = "normalize")
MerchantEntity toEntity(MerchantModel model);
MerchantModel toModel(MerchantEntity entity);






    @Named("normalize")
    static String normalizeName(String name) {
        if (name == null) return null;
        return name
                .toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[ñ]", "n")
                .replaceAll("[^a-z0-9]", "");
    }

    @AfterMapping
    default void afterMapping(@MappingTarget MerchantEntity entity) {
        entity.iniciarCampos();
    }
}
