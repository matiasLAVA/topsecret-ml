package com.topsecret.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class PositionDTO implements Serializable {

    private static final long serialVersionUID = 4412001263895943967L;
    private double x;
    private double y;

}
