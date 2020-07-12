package utils

import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.function.Supplier

enum HaltingExecutorCompletionState {
    COMPLETED, HALTED
}

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
        if (!currentExecution || currentExecution.isDone()) {
            currentExecution = submitTasksUntilQueueIsEmpty()
            currentExecution.get()
        }
        return executionTask.future
    }

    // TODO: Where does this nullable value val come from?
    private CompletableFuture submitTasksUntilQueueIsEmpty(val) {
        if (haltExecution) {
            return CompletableFuture.completedFuture(HaltingExecutorCompletionState.HALTED)
        }
        def nextTask = executionQueue.poll()
        if (!nextTask) {
            return CompletableFuture.completedFuture(HaltingExecutorCompletionState.COMPLETED)
        }
        currentExecution = submitTask(nextTask.task)
                .thenCompose({
                    completionState ->
                        if (completionState == HaltingExecutorCompletionState.COMPLETED) {
                            nextTask.future.complete('Future complete.')
                        }
                        if (haltExecution) {
                            return CompletableFuture.completedFuture(HaltingExecutorCompletionState.HALTED)
                        } else {
                            return this.submitTasksUntilQueueIsEmpty(null)
                        }
                })
//                .thenCompose({
////                    if (completionState == HaltingExecutorCompletionState.COMPLETED) {
////                        nextTask.future.complete('Future complete.')
////                        return this::submitTasksUntilQueueIsEmpty
////                    }
////                    return CompletableFuture.completedFuture()
//                    return this::submitTasksUntilQueueIsEmpty
//                })
        return currentExecution
    }

    private submitTask(Runnable task) {
        return CompletableFuture.supplyAsync({->
            if(haltExecution) {
                return HaltingExecutorCompletionState.HALTED
            }
            task.run()
            return HaltingExecutorCompletionState.COMPLETED
        }, executorService)
//        return CompletableFuture<HaltingExecutorCompletionState>.supplyAsync({
//            ->
//            if (haltExecution) {
//                return CompletableFuture.completedFuture(HaltingExecutorCompletionState.HALTED)
//            }
////            return HaltingExecutorCompletionState.COMPLETED
//            return CompletableFuture.runAsync(task, executorService).thenApply({ ->
//                HaltingExecutorCompletionState.COMPLETED })
//        }, executorService)
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
