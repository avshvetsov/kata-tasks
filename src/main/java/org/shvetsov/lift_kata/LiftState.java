package org.shvetsov.lift_kata;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
public class LiftState {
    private int floor;
    private Directions direction = Directions.STOPPED;

    private LiftMonitor monitor;

    public LiftState(int floor, LiftMonitor monitor) {
        this.floor = floor;
        this.monitor = monitor;
    }

    @SneakyThrows
    protected void up(long speed) {
        direction = Directions.UP;
        Thread.sleep(speed);
        floor++;
        System.out.println("Current floor: " + floor);
        monitor.changeState(this);
    }
    @SneakyThrows
    protected void down(long speed) {
        direction = Directions.DOWN;
        Thread.sleep(speed);
        floor--;
        System.out.println("Current floor: " + floor);
        monitor.changeState(this);
    }

    @Override
    public String toString() {
        return "LiftState{" +
                "floor=" + floor +
                ", direction=" + direction +
                '}';
    }
}
