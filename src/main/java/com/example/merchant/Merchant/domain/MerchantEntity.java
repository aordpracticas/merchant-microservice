package com.example.merchant.Merchant.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.merchant.Merchant.domain.enums.MerchantType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Entidad que representa un comercio (merchant)")
public class MerchantEntity extends MainTable {

    @ApiModelProperty(value = "Nombre del comercio")
    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @ApiModelProperty(value = "Dirección del comercio")
    @DynamoDBAttribute(attributeName = "address")
    private String address;

    @ApiModelProperty(value = "Tipo de comercio", example = "MERCHANT_TYPE_PERSONAL_SERVICES")
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "merchantType")
    private MerchantType merchantType;

    @ApiModelProperty(value = "ID del cliente asociado a este comercio")
    @DynamoDBAttribute(attributeName = "clientId")
    private String clientId;

    @ApiModelProperty(value = "Nombre del comercio normalizado para búsqueda")
    @DynamoDBAttribute(attributeName = "normalizedName")
    private String normalizedName;

    public void iniciarCampos() {
        this.inicializarBase("Merchant");
    }

    @DynamoDBHashKey(attributeName = "PK")
    @Override
    public String getPK() {
        return super.getPK();
    }

    @DynamoDBRangeKey(attributeName = "SK")
    @Override
    public String getSK() {
        return super.getSK();
    }
}
