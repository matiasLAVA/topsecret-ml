package com.topsecret;


import com.google.gson.Gson;
import com.topsecret.constant.EndpointConstant;
import com.topsecret.dto.SatelliteSplitPayloadDTO;
import com.topsecret.enums.SatelliteEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TopSecretApp.class)
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("dev")
public class TopSecretSplitTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void createSatellite() {
        Map<String, SatelliteSplitPayloadDTO> satellites = new HashMap<>();

        satellites.put(SatelliteEnum.KENOBI.getName(),
                SatelliteSplitPayloadDTO.builder()
                        .message(new String[]{"", "es", "un", "mensaje"})
                        .distance(200)
                        .build());
        satellites.put(SatelliteEnum.SKYWALKER.getName(),
                SatelliteSplitPayloadDTO.builder()
                        .message(new String[]{"este", "", "un", "mensaje"})
                        .distance(300)
                        .build());
        satellites.put(SatelliteEnum.SATO.getName(),
                SatelliteSplitPayloadDTO.builder()
                        .message(new String[]{"", "es", "", "mensaje"})
                        .distance(400)
                        .build());

        satellites.forEach(this::sendRequest);

    }

    private void sendRequest(String name,SatelliteSplitPayloadDTO dto){
        try{
            mvc.perform(MockMvcRequestBuilders
                    .post(EndpointConstant.TOPSECRET_SPLIT + "/" + name)
                    .content(new Gson().toJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }catch (Exception ex){
            log.error("the request could not be sent");
        }
    }

    @Test
    public void executeTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(EndpointConstant.TOPSECRET_SPLIT)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position.x").value(-133.24631300256522))
                .andExpect(jsonPath("$.position.y").value(-82.1948460076762))
                .andExpect(jsonPath("$.message").value("este es un mensaje"));
    }
}
