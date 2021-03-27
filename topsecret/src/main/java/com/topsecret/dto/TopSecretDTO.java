package com.topsecret.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class TopSecretDTO implements Serializable {

    private static final long serialVersionUID = -7634684813285796187L;
    private PositionDTO position;
    private String message;

}
