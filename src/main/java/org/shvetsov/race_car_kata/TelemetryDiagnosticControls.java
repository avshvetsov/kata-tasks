package org.shvetsov.race_car_kata;

public class TelemetryDiagnosticControls {

    public static final String DIAGNOSTIC_CHANNEL_CONNECTION_STRING = "*111#";

    public TelemetryDiagnosticControls() {
    }

    public String checkTransmission() throws Exception {
        try (TelemetryClient telemetryClient = new TelemetryClient(DIAGNOSTIC_CHANNEL_CONNECTION_STRING, 3)) {
            telemetryClient.send(TelemetryClient.DIAGNOSTIC_MESSAGE);
            return telemetryClient.receive();
        } catch (Exception e) {
            throw new Exception("Unable to connect.");
        }
    }
}
