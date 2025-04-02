package com.example.merchant.Merchant.aplication;

import com.example.merchant.Merchant.aplication.port.MerchantUpdate;
import com.example.merchant.Merchant.domain.MerchantEntity;
import com.example.merchant.Merchant.domain.mappers.MerchantEntityMapper;
import com.example.merchant.Merchant.infrastructure.repository.MerchantRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Getter
@Setter
public class MerchantUpdateImpl  implements MerchantUpdate {


    private final MerchantRepository merchantRepository;
    private  final MerchantEntityMapper merchantEntityMapper;
    @Override
    public MerchantModel updateClient(String id, MerchantModel dto) {
        MerchantEntity originalEntity = merchantRepository.findById(id);
        if (originalEntity == null) return  null;

        MerchantModel model = merchantEntityMapper.toModel(originalEntity);

        if (dto.getName() != null) model.setName(dto.getName());
        if (dto.getAddress() !=null) model.setAddress(dto.getAddress());
        if (dto.getMerchantType() !=null) model.setMerchantType(dto.getMerchantType());
        if (dto.getClientId() !=null) model.setClientId(dto.getClientId());

        MerchantEntity updatedEntity = merchantEntityMapper.toEntity(model);
        updatedEntity.setPK(originalEntity.getPK());
        updatedEntity.setSK(originalEntity.getSK());
        updatedEntity.setId(originalEntity.getId());
        updatedEntity.setStatus(originalEntity.getStatus());
        updatedEntity.setCreatedDate(originalEntity.getCreatedDate());
        updatedEntity.setGIndex2Pk(originalEntity.getGIndex2Pk());

        MerchantEntity savedEntity = merchantRepository.save(updatedEntity,false);
        return  merchantEntityMapper.toModel(savedEntity);
    }
}
