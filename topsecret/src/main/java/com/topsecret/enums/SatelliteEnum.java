package com.topsecret.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.geom.Point2D;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SatelliteEnum {

    KENOBI ("Kenobi", new Point2D.Double( -500, -200)),
    SKYWALKER ("Skywalker", new Point2D.Double(100, -100)),
    SATO ("Sato", new Point2D.Double(500, 100));

    private final String name;
    private final Point2D.Double point;

    public double[] getLocation() {
        return new double[]{point.x, point.y};
    }

    public static SatelliteEnum findByLabel(String satelliteName) {
        return Arrays.stream(SatelliteEnum.values()).filter(satellite -> satellite.getName()
                .equals(satelliteName)).findFirst().orElseThrow(IllegalArgumentException::new);
    }

}
