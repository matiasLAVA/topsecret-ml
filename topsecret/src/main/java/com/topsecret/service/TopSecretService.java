package com.topsecret.service;


import com.topsecret.dto.SatelliteDTO;
import com.topsecret.dto.TopSecretDTO;

import java.util.List;

public interface TopSecretService {

    /***
     * This method returns the location and the received message. If it fails, it returns a ResponseStatusException with http 404 error
     * @param satellites
     * @return TopSecretResponseDTO
     */
    TopSecretDTO process(List<SatelliteDTO> satellites);

}
