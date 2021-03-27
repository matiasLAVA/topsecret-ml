package com.satellite.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SatelliteDTO implements Serializable {

    private static final long serialVersionUID = 4616380321702693200L;
    private String name;
    private Double distance;
    private String[] message;

}
