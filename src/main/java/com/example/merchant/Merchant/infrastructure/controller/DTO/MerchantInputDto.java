package com.example.merchant.Merchant.infrastructure.controller.DTO;

import com.example.merchant.Merchant.domain.enums.MerchantType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantInputDto {

        private String name;
        private String address;
        private MerchantType merchantType;
        private String clientId;


}
