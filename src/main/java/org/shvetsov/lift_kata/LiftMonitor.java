package org.shvetsov.lift_kata;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LiftMonitor {

    private List<Consumer<LiftState>> consumers = new ArrayList<>();

    void addConsumer(Consumer<LiftState> consumer) {
        consumers.add(consumer);
    }

    public void changeState(LiftState liftState) {
        consumers.forEach(consumer -> consumer.accept(liftState));
    }
}
