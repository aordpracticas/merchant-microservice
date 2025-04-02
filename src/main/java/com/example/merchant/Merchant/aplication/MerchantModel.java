package com.example.merchant.Merchant.aplication;


import com.example.merchant.Merchant.domain.enums.MerchantType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MerchantModel {

    private String id;
    private String name;
    private String address;
    private MerchantType merchantType;
    private String clientId;
    private String normalizedName;


    private String status;
    private Date createdDate;
}
