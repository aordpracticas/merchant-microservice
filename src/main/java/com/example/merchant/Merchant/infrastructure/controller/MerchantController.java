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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@Api(value = "MerchantManagement", description = "Operaciones relacionadas con los merchants")
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

    @ApiOperation(value = "Crear un Cliente", notes = "En caso de estar el email mal saltara el error")
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
    @ApiOperation(value = "Busca un Merchant por ID", notes = "Devuelve un Merchant si existe")
    @GetMapping("/findById")
    public  ResponseEntity<?> findMerchantById(@RequestParam String id, @RequestParam(required = false) Boolean simpleOutput){

        MerchantModel model = merchantGet.findMerchantById(id, simpleOutput != null && simpleOutput);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hemos encontrado el merchant con ese ID");
        }

       MerchantOutputDto  dto = merchantDtoMapper.toOutputDto(model);
        return ResponseEntity.ok(dto);

    }

    @ApiOperation(value = "Busca todos los merchants cuya cadena coincide con la pasada")
    @GetMapping("/findByName")
    public ResponseEntity<List<MerchantOutputDto>> findClientByName(@RequestParam String name) {
        List<MerchantModel> models = merchantGet.findMerchantByName(name);
        List<MerchantOutputDto> dtos = models.stream().map(merchantDtoMapper::toOutputDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @ApiOperation(value = "Actualiza el Merchant, Recibe el Id del Merchant como parametros")
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
    @ApiOperation(value = "Elimina el Merchant cuyo Id coincide con el pasado")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClientById(@RequestParam String id) {
        boolean deleted = merchantDelete.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Merchant eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant con ese ID no encontrado.");
        }
    }
    @ApiOperation(value = "Devuelve datos del Client al cual pertenece el Merchant")
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

    @ApiOperation(value = "Devuelve todos los Merchants cuyo clientId coincida con el pasado")
    @GetMapping("/findByClientId")
    public ResponseEntity<List<MerchantOutputDto>> findByClientId(@RequestParam String clientId) {
        List<MerchantModel> models = merchantGet.findByClientId(clientId);
        List<MerchantOutputDto> dtos = models.stream()
                .map(merchantDtoMapper::toOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }





}
