package org.shvetsov.lift_kata;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.function.Consumer;


/**
 * The Lift Kata
 * <p><a href="https://kata-log.rocks/lift-kata">lift-kata</a>
 */
public class LiftImpl implements Lift {
    /**
     * Speed in millis by floor
     */
    private static final long LIFT_SPEED = 100;
    private final LiftState state;
    private final LiftMonitor monitor;
    int minFloor = Integer.MIN_VALUE;
    int maxFloor = Integer.MAX_VALUE;

    public LiftImpl(int floor) {
        this.monitor = new LiftMonitor();
        this.state = new LiftState(floor, monitor);
    }

    public LiftImpl(int floor, int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        if (floor < minFloor || floor > maxFloor) {
            throw new IllegalArgumentException(String.format("Floor must be in range from %s to %s", minFloor, maxFloor));
        }
        this.monitor = new LiftMonitor();
        this.state = new LiftState(floor, monitor);
    }

    @Override
    public int getCurrentFloor() {
        return state.getFloor();
    }

    @Override
    public LiftState call(int destinationFloor, Consumer<LiftState> callBack) {
        System.out.println("Lift called from " + destinationFloor + " floor. Current floor: " + state.getFloor());
        monitor.addConsumer(callBack);
        Thread.ofVirtual().start(() -> {
            while (state.getFloor() != destinationFloor) {
                move(destinationFloor);
            }
            state.setDirection(Directions.STOPPED);
        });
        return state;
    }


    private void move(int destinationFloor) {
        if (destinationFloor > state.getFloor()) {
            state.up(LIFT_SPEED);
        } else if (destinationFloor < state.getFloor()) {
            state.down(LIFT_SPEED);
        }
    }
}
