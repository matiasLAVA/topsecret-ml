package com.topsecret.service;


import com.topsecret.dto.PositionDTO;

public interface CommunicationCenterService {

    /***
     * Through an array of distance long, the trilateration is calculated and the method returns the position if its possible, if not, it returns null
     * @param distances
     * @return PositionDTO
     */
    PositionDTO getPosition(double... distances);

    /***
     * Through an array of String words, the message is decoded if possible, if it is not, null returns
     * @param messages
     * @returnString
     */
    String getMessage(String[]... messages);

}
