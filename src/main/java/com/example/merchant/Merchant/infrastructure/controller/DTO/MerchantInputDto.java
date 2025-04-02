package com.example.merchant.Merchant.infrastructure.controller.DTO;

import com.example.merchant.Merchant.domain.enums.MerchantType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "DTO de entrada para crear o actualizar un comercio (merchant)")
public class MerchantInputDto {

        @ApiModelProperty(value = "Nombre del comercio", example = "Zapatería El Paso")
        private String name;

        @ApiModelProperty(value = "Dirección del comercio", example = "Calle Mayor 123")
        private String address;

        @ApiModelProperty(value = "Tipo de comercio", example = "MERCHANT_TYPE_PERSONAL_SERVICES")
        private MerchantType merchantType;

        @ApiModelProperty(value = "ID del cliente asociado a este comercio", example = "a1b2c3d4-e5f6-7890-gh12-ijk345lmn678")
        private String clientId;
}

