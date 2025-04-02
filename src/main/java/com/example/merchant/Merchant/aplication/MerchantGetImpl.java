package com.example.merchant.Merchant.aplication;

import com.example.merchant.Merchant.aplication.port.MerchantGet;
import com.example.merchant.Merchant.domain.MerchantEntity;
import com.example.merchant.Merchant.domain.mappers.MerchantEntityMapper;
import com.example.merchant.Merchant.infrastructure.repository.MerchantRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MerchantGetImpl implements MerchantGet {

    private final MerchantRepository merchantRepository;
    private  final MerchantEntityMapper merchantEntityMapper;

    @Override
    public MerchantModel findMerchantById(String id, boolean simpleOutput) {
        MerchantEntity merchantEntity = merchantRepository.findById(id);
        if (merchantEntity == null) return  null;
        return  merchantEntityMapper.toModel(merchantEntity);
    }

    @Override
    public List<MerchantModel> findMerchantByName(String name) {
        String normalized = normalize(name);
       return  merchantRepository.findByName(normalized)
               .stream()
               .map(merchantEntityMapper::toModel)
               .collect(Collectors.toList());
    }

    @Override
    public List<MerchantModel> findByClientId(String clientId) {
        return merchantRepository.findByClientId(clientId)
                .stream()
                .map(merchantEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    private String normalize(String name) {
        return name.toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[ñ]", "n")
                .replaceAll("[^a-z0-9]", "");
    }
}
