package com.example.merchant.Merchant.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.merchant.Merchant.domain.MerchantEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class MerchantRepository {

    private  final DynamoDBMapper dynamoDBMapper;

    public MerchantEntity save(MerchantEntity merchant, boolean isNew){

        if(isNew){
            merchant.iniciarCampos();
        }
        dynamoDBMapper.save(merchant);
        return  merchant;

    }
    public MerchantEntity findById(String id){
        String pk = "#Merchant"+id;
        return  dynamoDBMapper.load(MerchantEntity.class,pk,id);
    }

    public List<MerchantEntity> findByName(String name){

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":gIndex2Pk", new AttributeValue().withS("Merchant")); // Valor hash del GSI
        expressionValues.put(":name", new AttributeValue().withS(name.toLowerCase())); // Valor del filtro

        Map<String, String> expressionNames = new HashMap<>();
        expressionNames.put("#gIndex2Pk", "gIndex2Pk");
        expressionNames.put("#normalizedName", "normalizedName");

        DynamoDBQueryExpression<MerchantEntity> query = new DynamoDBQueryExpression<MerchantEntity>()
                .withIndexName("GSIgIndex2Pk")
                .withKeyConditionExpression("#gIndex2Pk = :gIndex2Pk")
                .withFilterExpression("contains(#normalizedName, :name)")
                .withExpressionAttributeNames(expressionNames)
                .withExpressionAttributeValues(expressionValues)
                .withConsistentRead(false);

        return dynamoDBMapper.query(MerchantEntity.class, query);

    }

    public boolean deleteById(String id) {
        String pk = "#Merchant" + id;
        MerchantEntity merchant = dynamoDBMapper.load(MerchantEntity.class, pk, id);
        if (merchant != null) {
            dynamoDBMapper.delete(merchant);
            return true;
        }
        return false;
    }
    public List<MerchantEntity> findByClientId(String clientId) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":gIndex2Pk", new AttributeValue().withS("Merchant"));
        expressionValues.put(":clientId", new AttributeValue().withS(clientId));

        Map<String, String> expressionNames = new HashMap<>();
        expressionNames.put("#gIndex2Pk", "gIndex2Pk");
        expressionNames.put("#clientId", "clientId");

        DynamoDBQueryExpression<MerchantEntity> query = new DynamoDBQueryExpression<MerchantEntity>()
                .withIndexName("GSIgIndex2Pk")
                .withKeyConditionExpression("#gIndex2Pk = :gIndex2Pk")
                .withFilterExpression("#clientId = :clientId")
                .withExpressionAttributeNames(expressionNames)
                .withExpressionAttributeValues(expressionValues)
                .withConsistentRead(false);

        return dynamoDBMapper.query(MerchantEntity.class, query);
    }


}
