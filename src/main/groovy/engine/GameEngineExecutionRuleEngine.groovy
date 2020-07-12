package engine


import java.util.concurrent.atomic.AtomicLong

class GameEngineExecutionRuleEngine {
    private List<GameEngineExecutionRule> rules = []

    void leftShift(GameEngineExecutionRule rule) {
        rules << rule
    }

    void onGameEngineCycle(long timestamp, long delta) {
        rules.forEach({it.onGameEngineCycle(timestamp, delta)})
    }

    boolean shouldShutdown() {
        return rules.find{it.should(GameEngineExecutionRuleType.SHUTDOWN)}
    }

    boolean shouldHalt() {
        return rules.find{it.should(GameEngineExecutionRuleType.HALT)}
    }
}

enum GameEngineExecutionRuleType {
    HALT, SHUTDOWN
}

abstract class GameEngineExecutionRule {
    // TODO: Perfect use case for AST Transformations!
    boolean should(GameEngineExecutionRuleType ruleType) {
        return ruleTypes.find {it == ruleType} && checkCondition()
    }
    abstract Set<GameEngineExecutionRuleType> getRuleTypes()
    abstract void onGameEngineCycle(long timestamp, long delta)
    protected abstract boolean checkCondition()
}

class NoopGameEngineExecutionCondition extends GameEngineExecutionRule {
    @Override
    void onGameEngineCycle(long timestamp, long delta) {
    }

    @Override
    Set<GameEngineExecutionRuleType> getRuleTypes() {
        return []
    }

    @Override
    protected boolean checkCondition() {
        return false
    }
}

class ShutdownAfterFixedNumberOfCyclesExecutionRule extends GameEngineExecutionRule {
    private long totalCycles
    private AtomicLong cyclesPassed = new AtomicLong()

    static nrOfCycles(long cycles) {
        return new ShutdownAfterFixedNumberOfCyclesExecutionRule(totalCycles: cycles)
    }

    @Override
    Set<GameEngineExecutionRuleType> getRuleTypes() {
        return [GameEngineExecutionRuleType.SHUTDOWN]
    }

    @Override
    void onGameEngineCycle(long timestamp, long delta) {
        cyclesPassed.incrementAndGet()
    }

    @Override
    protected boolean checkCondition() {
        cyclesPassed.get() >= totalCycles
    }
}

class HaltEveryCycleExecutionRule extends GameEngineExecutionRule {
    @Override
    Set<GameEngineExecutionRuleType> getRuleTypes() {
        return [GameEngineExecutionRuleType.HALT]
    }

    @Override
    void onGameEngineCycle(long timestamp, long delta) {
    }

    @Override
    protected boolean checkCondition() {
        return true
    }
}