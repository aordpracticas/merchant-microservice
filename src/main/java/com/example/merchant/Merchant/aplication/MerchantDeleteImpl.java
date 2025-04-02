package com.example.merchant.Merchant.aplication;


import com.example.merchant.Merchant.aplication.port.MerchantDelete;
import com.example.merchant.Merchant.infrastructure.repository.MerchantRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MerchantDeleteImpl implements MerchantDelete {
    private  final MerchantRepository merchantRepository;


    @Override
    public boolean deleteById(String id) {
        return merchantRepository.deleteById(id);
    }
}
