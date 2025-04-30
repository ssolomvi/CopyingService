package ru.mai.service.handler;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.mai.config.ExecutorConfiguration;
import ru.mai.model.print.PrintableInColor;
import ru.mai.service.state.PrintState;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("rawtypes")
public abstract class StatefulPrinter implements PrintRequestHandler {

    private final static Logger log = LoggerFactory.getLogger(StatefulPrinter.class);

    protected final BlockingQueue<PrintableInColor> queue;
    protected final ReentrantLock lock = new ReentrantLock();
    protected final ScheduledExecutorService executor;

    protected final Collection<PrintState> printStates;

    protected PrintRequestHandler successor = null;
    protected PrintState currentPrintState;

    public StatefulPrinter(ApplicationContext context, ExecutorConfiguration executorConfiguration, ScheduledExecutorService executor) {
        this.printStates = context.getBeansOfType(PrintState.class).values();
        this.queue = new LinkedBlockingQueue<>(executorConfiguration.getQueueCapacity());
        this.executor = executor;

        scheduleQueuePolling(executorConfiguration);
    }

    @Override
    public void handle(PrintableInColor request) {
        if (canHandle(request)) {
            log.info("{}: Handling request {}", this, request);

            handleByColor(request);
        } else {
            log.debug("{}: Passing request {} to another handler", this, request);

            successor.handle(request);
        }
    }

    private void handleByColor(PrintableInColor request) {
        try {
            queue.add(request);
            log.debug("There is room for {} elements in queue", queue.remainingCapacity());
        } catch (IllegalStateException e) {
            log.error("Cannot add the request to the queue due to capacity restrictions");
        }
    }

    @Override
    public void setNext(PrintRequestHandler successor) {
        this.successor = successor;
    }

    protected PrintState configureState(PrintableInColor request) {
        try {
            lock.lock();
            if (needToConfigureStateForRequestProcessing(request)) {
                log.debug("Configuring state for printing request: {}", request);

                for (var printState : printStates) {
                    if (printState.canPrint(request)) {
                        currentPrintState = printState;
                    }
                }
            }

            return currentPrintState;
        } finally {
            lock.unlock();
        }
    }

    protected boolean needToConfigureStateForRequestProcessing(PrintableInColor request) {
        return currentPrintState == null
                || !currentPrintState.canPrint(request);
    }

    protected void scheduleQueuePolling(ExecutorConfiguration executorConfiguration) {
        executor.scheduleWithFixedDelay(
                this::pollAndProcessIfAny,
                executorConfiguration.getInitialDelay(),
                executorConfiguration.getDelay(),
                TimeUnit.SECONDS
        );
    }

    protected void pollAndProcessIfAny() {
        PrintableInColor request;
        if ((request = queue.poll()) != null) {
            processRequest(request);
        } else {
            processEmptyQueue();
        }
    }

    protected void processRequest(PrintableInColor request) {
        var state = configureState(request);

        //noinspection unchecked
        state.print(request);
    }

    protected abstract void processEmptyQueue();

    @PreDestroy
    protected void shutdownExecutorServiceAndAwaitTermination() {
        log.debug("Shutting down the scheduled executor service");

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
