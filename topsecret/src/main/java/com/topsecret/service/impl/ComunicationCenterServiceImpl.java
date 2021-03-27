package com.topsecret.service.impl;


import com.topsecret.dto.PositionDTO;
import com.topsecret.enums.SatelliteEnum;
import com.topsecret.service.CommunicationCenterService;
import com.topsecret.trilateration.NonLinearLeastSquaresSolver;
import com.topsecret.trilateration.TrilaterationFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ComunicationCenterServiceImpl implements CommunicationCenterService {

    private LeastSquaresOptimizer.Optimum nonLinearOptimum;
    private int xPosition = 0;
    private int yPosition = 1;

    @Override
    public PositionDTO getPosition(double... distances) {
        log.info("Starting to calculate position");
        PositionDTO positionDTO = null;
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(getAllPositions(), distances);
        NonLinearLeastSquaresSolver nlSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new LevenbergMarquardtOptimizer());

        try {
            nonLinearOptimum = nlSolver.solve();
            double[] calculatedPosition = nonLinearOptimum.getPoint().toArray();
            positionDTO = PositionDTO.builder().x(calculatedPosition[xPosition]).y(calculatedPosition[yPosition]).build();
            log.info("Position calculated successfully");
        } catch (Exception e) {
            log.error("Error calculating position", e);
        }

        return positionDTO;
    }

    @Override
    public String getMessage(String[]... messages) {
        List<String> result = new ArrayList<>();
        Integer word=0;
        Integer sentence=0;
        if(validMessages(messages)){
            while(sentence < messages.length && word < messages[0].length &&
                    result.size() != messages[0].length){
                if(StringUtils.isNotBlank(messages[sentence][word])){
                    result.add(messages[sentence][word]);
                    word++;
                    sentence = 0;
                }else{
                    sentence++;
                }
            }

            if(result.size() == messages[0].length){
                log.error("The message could be decoded correctly");
                return String.join(StringUtils.SPACE,result).trim();
            }
        }

        log.error("The message could not be decoded correctly");
        return null;

    }

    Boolean validMessages(String[]... messages){
        boolean isSameLength = false;

        if(messages != null && messages.length > 0){
            isSameLength =
                    Arrays.stream(messages)
                            .allMatch(l -> l.length == messages[0].length);
        }


        return isSameLength;
    }

    String[] getColumn(String[][] matrix, int column) {
        return IntStream.range(0, matrix.length)
                .mapToObj(i -> matrix[i][column]).toArray(String[]::new);
    }

    void computeMessage(StringBuilder encodedMessage,String[] row){
       String result = Arrays.stream(row).filter(StringUtils::isNotBlank).findFirst().orElse(StringUtils.EMPTY);
       if(!result.equals(StringUtils.EMPTY)){
           encodedMessage.append(result).append(" ");
       }
    }

    /***
     * Method used to get all the satellite location
     * @return
     */
    private double[][] getAllPositions() {
        return new double[][]{
                SatelliteEnum.KENOBI.getLocation(),
                SatelliteEnum.SKYWALKER.getLocation(),
                SatelliteEnum.SATO.getLocation()
        };
    }

}
