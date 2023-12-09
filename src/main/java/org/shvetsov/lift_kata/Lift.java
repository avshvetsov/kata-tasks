package org.shvetsov.lift_kata;

import java.util.function.Consumer;

public interface Lift {


    int getCurrentFloor();

    LiftState call(int destinationFloor, Consumer<LiftState> callBack);
}
