package ru.mai.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.mai.model.enums.Color;
import ru.mai.model.enums.PaperSize;
import ru.mai.model.enums.PhotoSize;

import java.util.Map;

@ConfigurationProperties(prefix = "app.time-to.print")
public class TimeToPrintConfiguration {

    private Map<String, Long> paperSizeTimeToPrintMapping;

    private Map<String, Long> photoSizeTimeToPrintMapping;

    private Map<String, Long> colorTimeToPrintMultiplierMapping;

    public Map<String, Long> getPaperSizeTimeToPrintMapping() {
        return paperSizeTimeToPrintMapping;
    }

    public void setPaperSizeTimeToPrintMapping(Map<String, Long> paperSizeTimeToPrintMapping) {
        this.paperSizeTimeToPrintMapping = paperSizeTimeToPrintMapping;
    }

    public Map<String, Long> getPhotoSizeTimeToPrintMapping() {
        return photoSizeTimeToPrintMapping;
    }

    public void setPhotoSizeTimeToPrintMapping(Map<String, Long> photoSizeTimeToPrintMapping) {
        this.photoSizeTimeToPrintMapping = photoSizeTimeToPrintMapping;
    }

    public Map<String, Long> getColorTimeToPrintMultiplierMapping() {
        return colorTimeToPrintMultiplierMapping;
    }

    public void setColorTimeToPrintMultiplierMapping(Map<String, Long> colorTimeToPrintMultiplierMapping) {
        this.colorTimeToPrintMultiplierMapping = colorTimeToPrintMultiplierMapping;
    }

    public Long getTimeToPrint(PaperSize paperSizeEnum) {
        return paperSizeTimeToPrintMapping.get(paperSizeEnum.name());
    }

    public Long getTimeToPrint(PhotoSize photoSizeEnum) {
        return photoSizeTimeToPrintMapping.get(photoSizeEnum.name());
    }

    public Long getTimeToPrintMultiplier(Color colorEnum) {
        return colorTimeToPrintMultiplierMapping.get(colorEnum.name());
    }

}
