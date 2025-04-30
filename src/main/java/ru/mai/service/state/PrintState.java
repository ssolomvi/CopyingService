package ru.mai.service.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mai.config.TimeToPrintConfiguration;
import ru.mai.model.print.PrintableInColor;

public abstract class PrintState<Printable extends PrintableInColor> {

    private final static Logger log = LoggerFactory.getLogger(PrintState.class);

    protected TimeToPrintConfiguration timeToPrintConfiguration;

    protected PrintState(TimeToPrintConfiguration timeToPrintConfiguration) {
        this.timeToPrintConfiguration = timeToPrintConfiguration;
    }

    public void print(Printable request) {
        log.debug("State: {} processing request: {}", this, request);
        var timeToProcess = calculatePrintProcessingTime(request);

        innerPrint(timeToProcess, request);
    }

    protected void innerPrint(long millis, Printable request) {
        try {
            Thread.sleep(millis);
            log.info("Printed request: {}", request);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean canPrint(PrintableInColor request);

    protected abstract long calculatePrintProcessingTime(Printable toPrint);

}
