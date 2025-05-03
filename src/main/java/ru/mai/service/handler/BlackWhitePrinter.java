package ru.mai.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.mai.config.property.ExecutorConfiguration;
import ru.mai.model.enums.Color;
import ru.mai.model.print.PrintableInColor;

import java.util.concurrent.ScheduledExecutorService;

@Service(BlackWhitePrinter.NAME)
public class BlackWhitePrinter extends StatefulPrinter {

    public final static String NAME = "mai_BlackWhitePrinter";

    private final static Logger log = LoggerFactory.getLogger(BlackWhitePrinter.class);

    @Autowired
    public BlackWhitePrinter(ApplicationContext context,
                             ExecutorConfiguration executorConfiguration,
                             @Qualifier("blackWhitePrinterExecutor") ScheduledExecutorService executor) {
        super(context, executorConfiguration, executor);
    }

    @Override
    public boolean canHandle(PrintableInColor request) {
        return Color.blackAndWhite.equals(request.getColor());
    }

    @Override
    protected void processRequest(PrintableInColor request) {
        log.trace("Processing request: {}", request);
        super.processRequest(request);
        log.trace("There are {} elements left in queue", queue.size());
    }

    @Override
    public String toString() {
        return "Black and white printer";
    }

}
