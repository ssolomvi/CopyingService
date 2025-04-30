package ru.mai.service.state;

import ru.mai.config.TimeToPrintConfiguration;
import ru.mai.model.enums.Color;
import ru.mai.model.print.PrintablePhoto;

public abstract class PhotoPrintState extends PrintState<PrintablePhoto> {

    protected PhotoPrintState(TimeToPrintConfiguration timeToPrintConfiguration) {
        super(timeToPrintConfiguration);
    }

    @Override
    protected long calculatePrintProcessingTime(PrintablePhoto toPrint) {
        return timeToPrintConfiguration.getTimeToPrint(toPrint.getPhotoSize())
                * timeToPrintConfiguration.getTimeToPrintMultiplier(Color.blackAndWhite);
    }

}
