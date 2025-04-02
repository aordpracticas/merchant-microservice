package com.example.merchant.Merchant.infrastructure.controller;

import com.example.merchant.Merchant.aplication.MerchantModel;
import com.example.merchant.Merchant.aplication.port.MerchantCreate;
import com.example.merchant.Merchant.aplication.port.MerchantDelete;
import com.example.merchant.Merchant.aplication.port.MerchantGet;
import com.example.merchant.Merchant.aplication.port.MerchantUpdate;
import com.example.merchant.Merchant.domain.mappers.MerchantDtoMapper;
import com.example.merchant.Merchant.infrastructure.controller.DTO.ClientOutputDto;
import com.example.merchant.Merchant.infrastructure.controller.DTO.MerchantInputDto;
import com.example.merchant.Merchant.infrastructure.controller.DTO.MerchantOutputDto;
import com.example.merchant.feign.ClientFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private  final MerchantCreate merchantCreate;
    private  final MerchantUpdate merchantUpdate;
    private  final MerchantGet merchantGet;
    private  final MerchantDelete merchantDelete;
    private  final MerchantDtoMapper merchantDtoMapper;
    private  final ClientFeignClient clientFeignClient;


    @PostMapping("/create")
    public ResponseEntity<?>createClient(@RequestBody @Valid MerchantInputDto merchantDto, BindingResult result){

        if(result.hasErrors()){
            String errorMessage = result.getFieldError("email") != null ?
                    result.getFieldError("email").getDefaultMessage() :
                    "Error en la validación de los campos.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

        }
        MerchantModel model = merchantDtoMapper.toModel(merchantDto);
        MerchantOutputDto dto = merchantDtoMapper.toOutputDto(merchantCreate.createMerchant(model));
        return  ResponseEntity.status(HttpStatus.CREATED).body(dto);

    }
    @GetMapping("/findById")
    public  ResponseEntity<?> findMerchantById(@RequestParam String id, @RequestParam(required = false) Boolean simpleOutput){

        MerchantModel model = merchantGet.findMerchantById(id, simpleOutput != null && simpleOutput);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hemos encontrado el merchant con ese ID");
        }

       MerchantOutputDto  dto = merchantDtoMapper.toOutputDto(model);
        return ResponseEntity.ok(dto);

    }
    @GetMapping("/findByName")
    public ResponseEntity<List<MerchantOutputDto>> findClientByName(@RequestParam String name) {
        List<MerchantModel> models = merchantGet.findMerchantByName(name);
        List<MerchantOutputDto> dtos = models.stream().map(merchantDtoMapper::toOutputDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClient(@RequestParam String id, @RequestBody @Valid MerchantInputDto merchantDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada");
        }


        MerchantModel model = merchantDtoMapper.toModel(merchantDto);


        MerchantModel updatedModel = merchantUpdate.updateClient(id, model);
        if (updatedModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant no encontrado");
        }


        MerchantOutputDto dto = merchantDtoMapper.toOutputDto(updatedModel);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClientById(@RequestParam String id) {
        boolean deleted = merchantDelete.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Merchant eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant con ese ID no encontrado.");
        }
    }
    @GetMapping("/getClientFromMerchant")
    public ResponseEntity<?> getClientFromMerchant(@RequestParam String merchantId) {

        MerchantModel merchant = merchantGet.findMerchantById(merchantId, false);
        if (merchant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant no encontrado");
        }


        String clientId = merchant.getClientId();


        try {
            ClientOutputDto client = clientFeignClient.getClientById(clientId);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente asociado no encontrado");
        }
    }

    @GetMapping("/findByClientId")
    public ResponseEntity<List<MerchantOutputDto>> findByClientId(@RequestParam String clientId) {
        List<MerchantModel> models = merchantGet.findByClientId(clientId);
        List<MerchantOutputDto> dtos = models.stream()
                .map(merchantDtoMapper::toOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }





}
