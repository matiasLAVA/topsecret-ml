package com.topsecret.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretPayloadDTO implements Serializable {

    private static final long serialVersionUID = 627497745695373435L;
    private List<SatelliteDTO> satellites;

}
