package com.topsecret.service;


import com.topsecret.dto.SatelliteSplitPayloadDTO;
import com.topsecret.dto.TopSecretDTO;


public interface TopSecretSplitService {

    /***
     * This function keeps the satellite alive
     * @param satelliteSplitPayloadDTO
     * @param satelliteName
     */
    void createSatellite(String satelliteName, SatelliteSplitPayloadDTO satelliteSplitPayloadDTO);

    /***
     * With all data stored by the method keepSplitSatellite, this function return a TopSecretResponseDTO
     * @return TopSecretResponseDTO
     */
    TopSecretDTO execute();
}
