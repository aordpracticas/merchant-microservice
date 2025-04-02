package com.example.merchant.Merchant.aplication;


import com.example.merchant.Merchant.aplication.port.MerchantCreate;
import com.example.merchant.Merchant.domain.MerchantEntity;
import com.example.merchant.Merchant.domain.mappers.MerchantEntityMapper;
import com.example.merchant.Merchant.infrastructure.repository.MerchantRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MerchantCreateImpl implements MerchantCreate {

    private final MerchantRepository merchantRepository;
    private  final MerchantEntityMapper merchantEntityMapper;
    @Override
    public  MerchantModel createMerchant(MerchantModel merchantCreate){
        MerchantEntity entity = merchantEntityMapper.toEntity(merchantCreate);
        MerchantEntity savedEntity = merchantRepository.save(entity,true);
        return  merchantEntityMapper.toModel(savedEntity);

    }
}
