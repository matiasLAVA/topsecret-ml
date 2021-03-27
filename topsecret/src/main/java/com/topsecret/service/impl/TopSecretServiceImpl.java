package com.topsecret.service.impl;


import com.topsecret.dto.PositionDTO;
import com.topsecret.dto.SatelliteDTO;
import com.topsecret.dto.TopSecretDTO;
import com.topsecret.service.CommunicationCenterService;
import com.topsecret.service.TopSecretService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TopSecretServiceImpl implements TopSecretService {

    @Autowired
    CommunicationCenterService communicationCenterService;

    /**
     * method that gets the messages and calculates the position
     * @param satellites
     * @return
     */
    @Override
    public TopSecretDTO process(List<SatelliteDTO> satellites) {
        log.info("Start process");
        PositionDTO positionDTO;
        String message;
        try{
            log.info("Getting messages from satellites");
            if((message = getMessage(satellites)) != null){
                log.info("Getting position from satellites");
                if((positionDTO = getPosition(satellites)) != null){
                    log.info("Position and messages decoded correctly");
                    return TopSecretDTO.builder()
                            .message(message).position(positionDTO).build();
                }else{
                    log.info("Error getting position from satellites");
                }
            }else{
                log.info("Error getting the messages from satellites");
            }
        }catch(Exception ex){
            log.error("Error getting the messages and calculating the position");
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error not enough information");
    }

    /**
     * Valid messages and position
     * @param message
     * @param positionDTO
     * @return
     */
    Boolean validMessagesAndPosition(String message,PositionDTO positionDTO){

        if(StringUtils.isBlank(message)){
            log.info("error getting the messages from satellites");
        }

        if(positionDTO == null){
            log.info("Error getting position from satellites");
        }

        return StringUtils.isBlank(message) || positionDTO == null;
    }

    /**
     * function to decode the messages of the ship
     * @param satellitesDTOS
     * @return
     */
    private String getMessage(List<SatelliteDTO> satellitesDTOS) {
        log.info("Start process getMessage");
        String messageDTO = null;
        if(satellitesDTOS != null && satellitesDTOS.size() > 0){
            String[][] messages = satellitesDTOS.stream()
                    .map(SatelliteDTO::getMessage)
                    .filter(Objects::nonNull)
                    .toArray(String[][]::new);

            if (messages != null && messages.length > 0) {
                if (((messageDTO = communicationCenterService.getMessage(messages)) == null)) {
                    log.error("Error getting message");
                }
            } else {
                log.error("There are no messages");
            }
        }
        return messageDTO;
    }

    /**
     * function to get the position of the ship
     * @param satellitesDTOS
     * @return
     */
    private PositionDTO getPosition(List<SatelliteDTO> satellitesDTOS) {
        log.info("Start process processAndGetLocation");
        PositionDTO positionDTO = null;

        if(satellitesDTOS != null && satellitesDTOS.size() > 0){
            double[] distances = satellitesDTOS.stream().
                    mapToDouble(SatelliteDTO::getDistance)
                    .toArray();

            if (distances.length > 0) {
                if (((positionDTO = communicationCenterService.getPosition(distances)) == null)) {
                    log.error("Error calculating location");
                }
            } else {
                log.error("there are no distances");
            }
        }

        return positionDTO;
    }
}
