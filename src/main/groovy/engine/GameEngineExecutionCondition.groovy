package engine

import java.util.concurrent.atomic.AtomicLong

interface GameEngineExecutionCondition {
    void onGameEngineCycle(long timestamp, long delta)
    boolean shouldStop()
}

class FixedCycleGameEngineExecutionCondition implements GameEngineExecutionCondition {
    private long totalCycles
    private AtomicLong cyclesPassed = new AtomicLong()

    static nrOfCycles(long cycles) {
        return new FixedCycleGameEngineExecutionCondition(totalCycles: cycles)
    }

    @Override
    void onGameEngineCycle(long timestamp, long delta) {
        cyclesPassed.incrementAndGet()
    }

    @Override
    boolean shouldStop() {
        cyclesPassed.get() >= totalCycles
    }
}