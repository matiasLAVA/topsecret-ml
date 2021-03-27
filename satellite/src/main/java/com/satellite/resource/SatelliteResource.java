package com.satellite.resource;

import com.satellite.dto.SatelliteDTO;
import com.satellite.service.SatelliteService;
import com.satellite.dto.SatellitePayloadDTO;
import com.satellite.constant.EndpointConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(EndpointConstant.SATELLITE)
public class SatelliteResource {

    @Autowired
    SatelliteService satelliteService;

    @PostMapping(value = "/{satellite_name}",
                 consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createSatellite(@PathVariable(value="satellite_name") String satelliteName, @RequestBody SatellitePayloadDTO satellitePayloadDTO){
        satelliteService.createSatellite(satelliteName, satellitePayloadDTO);
        return "Satellite creado correctamente";
    }

    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SatelliteDTO> getAllSatelites(){
        return satelliteService.getSatellites();
    }

}
