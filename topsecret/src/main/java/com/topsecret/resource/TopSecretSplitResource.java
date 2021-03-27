package com.topsecret.resource;


import com.topsecret.constant.EndpointConstant;
import com.topsecret.dto.SatelliteSplitPayloadDTO;
import com.topsecret.dto.TopSecretDTO;
import com.topsecret.service.TopSecretSplitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(EndpointConstant.TOPSECRET_SPLIT)
public class TopSecretSplitResource {

    @Autowired
    TopSecretSplitService topSecretSplitService;

    @PostMapping(value = "/{satellite_name}",
                 consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createSatellite(@PathVariable(value="satellite_name") String satelliteName, @RequestBody SatelliteSplitPayloadDTO satelliteSplitPayloadDTO){
        topSecretSplitService.createSatellite(satelliteName,satelliteSplitPayloadDTO);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public TopSecretDTO execute(){
        return topSecretSplitService.execute();
    }

}
