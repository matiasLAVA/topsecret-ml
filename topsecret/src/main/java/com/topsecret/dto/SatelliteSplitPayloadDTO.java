package com.topsecret.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SatelliteSplitPayloadDTO implements Serializable {

    private static final long serialVersionUID = -8859638578229654733L;
    private double distance;
    private String[] message;

}
