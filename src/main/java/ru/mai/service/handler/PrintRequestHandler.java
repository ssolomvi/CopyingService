package ru.mai.service.handler;

import ru.mai.model.print.PrintableInColor;

public interface PrintRequestHandler {

    boolean canHandle(PrintableInColor request);

    void handle(PrintableInColor request);

    void setNext(PrintRequestHandler successor);

}
