package ru.mai.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.mai.config.ExecutorConfiguration;
import ru.mai.model.enums.Color;
import ru.mai.model.print.PrintableInColor;

import java.util.concurrent.ScheduledExecutorService;

import static ru.mai.service.handler.ColorPrinter.NAME;

@Service(NAME)
public class ColorPrinter extends StatefulPrinter {

    public final static String NAME = "mai_ColorPrinter";

    private final static Logger log = LoggerFactory.getLogger(ColorPrinter.class);

    @Autowired
    public ColorPrinter(ApplicationContext context,
                        ExecutorConfiguration executorConfiguration,
                        @Qualifier("colorPrinterExecutor") ScheduledExecutorService executor) {
        super(context, executorConfiguration, executor);
    }

    @Override
    public boolean canHandle(PrintableInColor request) {
        return Color.inColor.equals(request.getColor());
    }

    @Override
    protected void processRequest(PrintableInColor request) {
        log.trace("Processing request: {}", request);
        super.processRequest(request);
        log.debug("There are {} elements left in queue", queue.size());
    }

    @Override
    protected void processEmptyQueue() {
        log.trace("Color printer's queue is empty");
    }

    @Override
    public String toString() {
        return "Color printer";
    }

    /*
    @Scheduled(fixedRate = 1_000)
    public void scheduleQueuePolling() {
        PrintableInColor request;
        if ((request = queue.poll()) != null) {
            executor.execute(() -> {
                var state = configureState(request);

                //noinspection unchecked
                state.print(request);
            });
        }
    }
     */

}
