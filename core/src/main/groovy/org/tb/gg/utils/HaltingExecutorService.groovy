package org.tb.gg.utils

import groovy.util.logging.Log4j

import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

enum HaltingExecutorCompletionState {
    COMPLETED, HALTED
}

@Log4j
class HaltingExecutorService implements ExecutorService {
    private Future currentExecution
    private Queue<ExecutorServiceQueueElement> executionQueue = new LinkedList<>()
    private haltExecution = false
    private executorService = Executors.newFixedThreadPool(1)

    class ExecutorServiceQueueElement {
        Runnable task
        CompletableFuture<String> future
    }

    @Override
    void shutdown() {
        executorService.shutdown()
    }

    @Override
    List<Runnable> shutdownNow() {
        return executorService.shutdownNow()
    }

    @Override
    boolean isShutdown() {
        return executorService.isShutdown()
    }

    @Override
    boolean isTerminated() {
        return executorService.isTerminated()
    }

    @Override
    boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit)
    }

    @Override
    def <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task)
    }

    @Override
    def <T> Future<T> submit(Runnable task, T result) {
        return executorService.submit(task, result)
    }

    @Override
    Future<?> submit(Runnable task) {
        def executionTask = enqueueTask(task)
        triggerExecutionIfNotAlreadyRunning()
        return executionTask.future
    }

    private triggerExecutionIfNotAlreadyRunning() {
        if (!currentExecution || currentExecution.isDone()) {
            currentExecution = submitTasksUntilQueueIsEmpty()
            currentExecution.get()
        }
    }

    // TODO: Where does this nullable value val come from?
    private CompletableFuture submitTasksUntilQueueIsEmpty(val) {
        if (haltExecution) {
            log.debug("Halting execution when ready to run the next task from queue.")
            return CompletableFuture.completedFuture(HaltingExecutorCompletionState.HALTED)
        }
        def nextTask = executionQueue.poll()
        if (!nextTask) {
            log.debug("No next task in queue, ending execution.")
            return CompletableFuture.completedFuture(HaltingExecutorCompletionState.COMPLETED)
        }
        currentExecution = submitTask(nextTask.task)
                .thenCompose({
                    completionState ->
                        if (completionState == HaltingExecutorCompletionState.COMPLETED) {
                            nextTask.future.complete('Future complete.')
                        }
                        return this.submitTasksUntilQueueIsEmpty(null)
                })
        return currentExecution
    }

    private submitTask(Runnable task) {
        return CompletableFuture.supplyAsync({->
            if(haltExecution) {
                log.debug("Halting execution in task executor - returning halted execution state.")
                return HaltingExecutorCompletionState.HALTED
            }
            task.run()
            return HaltingExecutorCompletionState.COMPLETED
        }, executorService)
    }

    private enqueueTask(Runnable task) {
        def executionQueueElement = createExecutionQueueElement(task)
        executionQueue.add(executionQueueElement)
        return executionQueueElement
    }

    private createExecutionQueueElement(Runnable task) {
        def submitFuture = new CompletableFuture()
        return new ExecutorServiceQueueElement(task: task, future: submitFuture)
    }

    void halt() {
        haltExecution = true
    }

    void continueExecution() {
        haltExecution = false
        triggerExecutionIfNotAlreadyRunning()
    }

    @Override
    def <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks)
    }

    @Override
    def <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.invokeAll(tasks, timeout, unit)
    }

    @Override
    def <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return executorService.invokeAny(tasks)
    }

    @Override
    def <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return executorService.invokeAny(tasks, timeout, unit)
    }

    @Override
    void execute(Runnable command) {
        executorService.execute(command)
    }
}
