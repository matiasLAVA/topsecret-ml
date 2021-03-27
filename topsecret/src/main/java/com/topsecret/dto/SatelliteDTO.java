package com.topsecret.dto;

import lombok.*;

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
