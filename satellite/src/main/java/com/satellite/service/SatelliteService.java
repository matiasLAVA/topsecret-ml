package com.satellite.service;

import com.satellite.dto.SatelliteDTO;
import com.satellite.dto.SatellitePayloadDTO;

import java.util.List;


public interface SatelliteService {

    /**
     * this function stores the satellite in the database
     * @param satelliteName
     * @param satellitePayloadDTO
     */
    void createSatellite(String satelliteName, SatellitePayloadDTO satellitePayloadDTO);

    /**
     * this function gets all the satellites from the database
     * @return
     */
    List<SatelliteDTO> getSatellites();

}
