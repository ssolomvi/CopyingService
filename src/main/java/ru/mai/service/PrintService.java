package ru.mai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.mai.model.print.PrintableInColor;
import ru.mai.service.handler.PrintRequestHandler;

@Service(PrintService.NAME)
public class PrintService {

    public final static String NAME = "mai_PrintService";

    private final static Logger log = LoggerFactory.getLogger(PrintService.class);

    private PrintRequestHandler handler;

    @Autowired
    public PrintService(ApplicationContext context) {
        collectChainOfHandlers(context);
    }

    private void collectChainOfHandlers(ApplicationContext context) {
        var handlers = context.getBeansOfType(PrintRequestHandler.class)
                .values();

        PrintRequestHandler prev = null;
        for (var curr : handlers) {
            if (prev != null) {
                prev.setNext(curr);
            } else {
                handler = curr;
            }
            prev = curr;
        }
    }

    public <T extends PrintableInColor> void print(T request) {
        log.debug("Delegating handling event to handler {}", handler);

        handler.handle(request);
    }

}
