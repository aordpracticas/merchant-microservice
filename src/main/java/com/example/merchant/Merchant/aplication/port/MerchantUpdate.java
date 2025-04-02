package com.example.merchant.Merchant.aplication.port;

import com.example.merchant.Merchant.aplication.MerchantModel;

public interface MerchantUpdate {

    public MerchantModel updateClient(String id, MerchantModel model);
}
