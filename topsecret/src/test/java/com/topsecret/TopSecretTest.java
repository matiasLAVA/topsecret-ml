package com.topsecret;


import com.google.gson.Gson;
import com.topsecret.constant.EndpointConstant;
import com.topsecret.dto.SatelliteDTO;
import com.topsecret.dto.SatellitePayloadDTO;
import com.topsecret.enums.SatelliteEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TopSecretApp.class)
@AutoConfigureMockMvc
public class TopSecretTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getMessage() throws Exception {
        List<SatelliteDTO> list = new ArrayList<>();

        list.add(SatelliteDTO.builder().name(SatelliteEnum.KENOBI.name()).message(new String[]{"", "es", "un", "mensaje"}).build());
        list.add(SatelliteDTO.builder().name(SatelliteEnum.SKYWALKER.name()).message(new String[]{"este","","un","mensaje"}).build());
        list.add(SatelliteDTO.builder().name(SatelliteEnum.SATO.name()).message(new String[]{"","es","","mensaje"}).build());

        mvc.perform(MockMvcRequestBuilders
                .post(EndpointConstant.TOPSECRET)
                .content(new Gson().toJson(SatellitePayloadDTO.builder().satellites(list).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("este es un mensaje"));
    }

    @Test
    public void getLocation() throws Exception {
        List<SatelliteDTO> list = new ArrayList<>();

        list.add(SatelliteDTO.builder().name(SatelliteEnum.KENOBI.name()).message(new String[]{"", "es", "un", "mensaje"}).distance(300.0).build());
        list.add(SatelliteDTO.builder().name(SatelliteEnum.SKYWALKER.name()).message(new String[]{"este","","un","mensaje"}).distance(200.0).build());
        list.add(SatelliteDTO.builder().name(SatelliteEnum.SATO.name()).message(new String[]{"","es","","mensaje"}).distance(400.0).build());

        mvc.perform(MockMvcRequestBuilders
                .post(EndpointConstant.TOPSECRET)
                .content(new Gson().toJson(SatellitePayloadDTO.builder().satellites(list)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
