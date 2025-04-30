package ru.mai.service.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.config.TimeToPrintConfiguration;
import ru.mai.model.enums.Color;
import ru.mai.model.print.PrintableInColor;
import ru.mai.model.print.PrintablePaper;

@Component(PaperPrintState.NAME)
public class PaperPrintState extends PrintState<PrintablePaper> {

    public final static String NAME = "mai_PaperPrintState";

    @Autowired
    protected PaperPrintState(TimeToPrintConfiguration timeToPrintConfiguration) {
        super(timeToPrintConfiguration);
    }

    @Override
    public boolean canPrint(PrintableInColor request) {
        return request instanceof PrintablePaper;
    }

    @Override
    protected long calculatePrintProcessingTime(PrintablePaper toPrint) {
        return timeToPrintConfiguration.getTimeToPrint(toPrint.getPaperSize())
                * timeToPrintConfiguration.getTimeToPrintMultiplier(Color.inColor);
    }

    @Override
    public String toString() {
        return "Paper print state";
    }

}
