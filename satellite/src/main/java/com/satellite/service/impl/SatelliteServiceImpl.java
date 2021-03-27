package com.satellite.service.impl;

import com.satellite.dto.SatelliteDTO;
import com.satellite.enums.SatelliteEnum;
import com.satellite.service.SatelliteService;
import com.google.gson.Gson;
import com.satellite.dto.SatellitePayloadDTO;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SatelliteServiceImpl implements SatelliteService {

    @Autowired
    SatelliteService satelliteService;

    @Autowired
    MongoDatabase mongoDatabase;

    @Override
    public void createSatellite(String satelliteName, SatellitePayloadDTO satellitePayloadDTO) {
        log.info("creating the satellite");
        Gson gson = new Gson();
        try {
            SatelliteDTO satelliteDTO = SatelliteDTO.builder()
                    .name(SatelliteEnum.findByLabel(satelliteName).getName())
                    .distance(satellitePayloadDTO.getDistance())
                    .message(satellitePayloadDTO.getMessage())
                    .build();

            Document document = Document.parse(gson.toJson(satelliteDTO));

            if(validateDocumentExists(document)){
                mongoDatabase.getCollection("satellite").insertOne(document);
                log.info("Finished creating the satellite successfully");
            }else{
                log.info("the satellite was already created");
            }
        } catch(Exception e) {
            log.error("Error trying to create the satellite", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error trying to create the satellite");
        }

    }

    public Boolean validateDocumentExists(Document document){
        log.info("validated if the document exists");
        long count =  mongoDatabase.getCollection("satellite").count(document);
        if (count > 0){
            return false;
        }

        return true;
    }

    @Override
    public List<SatelliteDTO> getSatellites() {
        List<SatelliteDTO> satellites = new ArrayList<>();
        Gson gson = new Gson();
        try (MongoCursor<Document> cursor = mongoDatabase.getCollection("satellite").find().iterator()) {
            while (cursor.hasNext()) {
                satellites.add(gson.fromJson(cursor.next().toJson(),SatelliteDTO.class));
            }
        }

        return satellites;
    }
}
