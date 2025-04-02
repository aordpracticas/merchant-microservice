package com.example.merchant.Merchant.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.example.merchant.Merchant.domain.enums.MerchantType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantEntity extends  MainTable {

    @DynamoDBAttribute(attributeName = "name")
    private  String name;

    @DynamoDBAttribute(attributeName = "address")
    private  String address;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "merchantType")
    private MerchantType merchantType;

    @DynamoDBAttribute(attributeName = "clientId")
    private  String clientId;

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

