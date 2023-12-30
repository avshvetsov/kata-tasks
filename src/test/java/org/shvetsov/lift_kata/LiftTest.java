package org.shvetsov.lift_kata;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.shvetsov.lift_kata.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LiftTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    public void shouldCreateLiftOnSomeFlow() {
        final int FLOOR = 1000;
        Lift lift = new LiftImpl(FLOOR);
        assertThat(lift.getCurrentFloor()).isEqualTo(FLOOR);
    }

    @ParameterizedTest
    @ValueSource(ints = {-200, 500})
    public void shouldThrowIllegalArgumentException(int invalidFloor) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new LiftImpl(invalidFloor, -2, 24));
    }


    //TODO: understand how to test callbacks
    @SneakyThrows
    @Test
    public void shouldRespondToCall() {
        Lift lift = new LiftImpl(1);
        int startFloor = lift.getCurrentFloor();
        LiftState state = lift.call(5, changedState -> {
            System.out.println(changedState);
            assertThat(lift.getCurrentFloor()).isNotEqualTo(startFloor);
        });
        Thread.sleep(1000);

        assertThat(state.getFloor()).isEqualTo(5);
        assertThat(state.getDirection()).isEqualTo(Directions.STOPPED);
    }

    @Test
    public void shouldReturnMonitorWhenCalled() {
        assertThat(new LiftImpl(1).call(3, liftState -> {
        })).isInstanceOf(LiftMonitor.class);
    }
}