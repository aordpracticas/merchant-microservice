package com.example.merchant.feign;

import com.example.merchant.Merchant.infrastructure.controller.DTO.ClientOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-service", url = "http://localhost:8082")
public interface ClientFeignClient {

    @GetMapping("/api/client/findById")
    ClientOutputDto getClientById(@RequestParam String id);
}