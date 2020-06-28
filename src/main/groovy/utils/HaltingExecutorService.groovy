package utils

import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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

    private CompletableFuture submitTasksUntilQueueIsEmpty() {
        if (haltExecution) {
            return CompletableFuture.completedFuture()
        }
        def nextTask = executionQueue.poll()
        if (!nextTask) {
            return CompletableFuture.completedFuture()
        }
        currentExecution = submitTask(nextTask.task)
                .thenRun({
                    -> nextTask.future.complete('test')
                })
        // TODO: Can't make this recursive call work.
                .thenCompose({ ->
                    return submitTasksUntilQueueIsEmpty()
                })
        return currentExecution
    }

    private submitTask(Runnable task) {
        return CompletableFuture.supplyAsync({
            ->
            System.println('test2')
            return CompletableFuture.runAsync(task, executorService)
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
