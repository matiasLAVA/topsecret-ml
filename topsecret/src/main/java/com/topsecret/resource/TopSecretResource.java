package com.topsecret.resource;


import com.topsecret.constant.EndpointConstant;
import com.topsecret.dto.TopSecretDTO;
import com.topsecret.dto.TopSecretPayloadDTO;
import com.topsecret.service.impl.TopSecretServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointConstant.TOPSECRET)
public class TopSecretResource {

    @Autowired
    TopSecretServiceImpl mercadolibreAPIService;

    @PostMapping(value = "",
                 consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    public TopSecretDTO process(@RequestBody TopSecretPayloadDTO satelliteDTO) {
        return mercadolibreAPIService.process(satelliteDTO.getSatellites());
    }

}
