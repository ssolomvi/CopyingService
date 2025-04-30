package ru.mai.service.state;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.config.TimeToPrintConfiguration;
import ru.mai.model.print.PrintableInColor;
import ru.mai.model.print.PrintablePhoto;
import ru.mai.service.PhotoService;

@Component(ProxyPhotoPrintState.NAME)
public class ProxyPhotoPrintState extends PhotoPrintState {

    public final static String NAME = "mai_ProxyPhotoPrintState";

    private static final Logger log = LoggerFactory.getLogger(ProxyPhotoPrintState.class);

    @Autowired
    private PhotoService photoService;

    @Autowired
    protected ProxyPhotoPrintState(TimeToPrintConfiguration timeToPrintConfiguration) {
        super(timeToPrintConfiguration);
    }

    @Override
    public boolean canPrint(PrintableInColor request) {
        return request instanceof PrintablePhoto;
    }

    @Override
    public void print(PrintablePhoto request) {
        if (StringUtils.isBlank(request.getFilename())) {
            log.info("Asking for a photo");
            request.setFilename(photoService.takePhoto());
        }

        super.print(request);
    }

    @Override
    public String toString() {
        return "Proxy photo state";
    }

}
