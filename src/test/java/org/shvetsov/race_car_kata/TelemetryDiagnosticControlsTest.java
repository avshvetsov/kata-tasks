package org.shvetsov.race_car_kata;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelemetryDiagnosticControlsTest {

    private TelemetryDiagnosticControls tdc = new TelemetryDiagnosticControls();

    @SneakyThrows
    @Test
    public void checkTransmissionTest() {
//        tdc.checkTransmission();
        System.out.println(tdc.checkTransmission());
    }

}