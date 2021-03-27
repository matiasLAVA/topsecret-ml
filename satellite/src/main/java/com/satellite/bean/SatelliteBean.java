package com.satellite.bean;

import com.satellite.enums.SatelliteEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SatelliteBean {

    private SatelliteEnum satelliteEnum;
    private double distance;
    private String[] message;

}
