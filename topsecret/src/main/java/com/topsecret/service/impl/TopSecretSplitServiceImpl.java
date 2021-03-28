package com.topsecret.service.impl;




import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.topsecret.constant.EndpointConstant;
import com.topsecret.dto.SatelliteDTO;
import com.topsecret.dto.SatelliteSplitPayloadDTO;
import com.topsecret.dto.TopSecretDTO;
import com.topsecret.enums.SatelliteEnum;
import com.topsecret.service.TopSecretService;
import com.topsecret.service.TopSecretSplitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class TopSecretSplitServiceImpl implements TopSecretSplitService {

    @Autowired
    TopSecretService topSecretService;

    @Autowired
    private EurekaClient discoveryClient;

    WebClient webClient = WebClient.create();

    @Override
    public void createSatellite(String satelliteName, SatelliteSplitPayloadDTO satelliteSplitPayloadDTO) {
        log.info("creating the satellite to database");

        try {
            InstanceInfo ins = discoveryClient.getNextServerFromEureka("satellite",false);
            log.info("satellite url: " + ins.getHomePageUrl());
            Mono<String> resultMono = webClient.post()
                    .uri( ins.getHomePageUrl().concat(EndpointConstant.SATELLITE).concat("/").concat(satelliteName))
                    .body(Mono.just(satelliteSplitPayloadDTO), SatelliteSplitPayloadDTO.class)
                    .retrieve()
                    .bodyToMono(String.class);

            String result = resultMono.block();

            log.info("the satellite was created successfully");

        } catch(Exception e) {
            log.error("Error trying to create the satellite", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error trying to create the satellite");
        }

    }

    @Override
    public TopSecretDTO execute() {
        log.info("Starting process");
        List<SatelliteDTO> satellites = getSatellites();
        TopSecretDTO topSecretDTO = topSecretService.process(satellites);
        return topSecretDTO;
    }

    List<SatelliteDTO> getSatellites() {
        log.info("getting the satellites");

        try {
            InstanceInfo ins = discoveryClient.getNextServerFromEureka("satellite",false);
            log.info("satellite url: " + ins.getHomePageUrl());
            return webClient.get()
                    .uri(ins.getHomePageUrl().concat(EndpointConstant.SATELLITE))
                    .retrieve()
                    .bodyToFlux(SatelliteDTO.class)
                    .collectList()
                    .block();

        } catch(Exception e) {
            log.error("Error trying to create the satellite", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error trying to create the satellite");
        }
    }
}
