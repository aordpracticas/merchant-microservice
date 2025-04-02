package com.example.merchant.Merchant.aplication.port;

import com.example.merchant.Merchant.aplication.MerchantModel;

import java.util.List;

public interface MerchantGet {
    MerchantModel findMerchantById(String id, boolean simpleOutput);
    List<MerchantModel> findMerchantByName(String name);
    List<MerchantModel> findByClientId(String clientId);

}
