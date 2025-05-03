package ru.mai.service.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mai.config.property.TimeToPrintConfiguration;
import ru.mai.model.print.PrintableInColor;

import java.text.NumberFormat;

public abstract class PrintState<Printable extends PrintableInColor> {

    private final static Logger log = LoggerFactory.getLogger(PrintState.class);
    private static final NumberFormat nFormat = NumberFormat.getInstance();

    protected TimeToPrintConfiguration timeToPrintConfiguration;

    protected PrintState(TimeToPrintConfiguration timeToPrintConfiguration) {
        this.timeToPrintConfiguration = timeToPrintConfiguration;
    }

    public void print(Printable request) {
        var timeToProcess = calculatePrintProcessingTime(request);
        log.info("State: {} evaluated time is: {} ms for processing request: {}", this, nFormat.format(timeToProcess), request);

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
