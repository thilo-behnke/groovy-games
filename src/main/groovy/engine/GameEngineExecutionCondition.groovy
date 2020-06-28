package engine

import global.Observer

import java.util.concurrent.atomic.AtomicLong

interface GameEngineExecutionCondition {
    void onGameEngineCycle(long timestamp, long delta)
    boolean shouldStop()
}

class FixedCycleGameEngineExecutionCondition implements GameEngineExecutionCondition {
    private long totalCycles
    private AtomicLong cyclesPassed = new AtomicLong()

    private List<Observer<Long>> cycleObservers = new ArrayList()

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