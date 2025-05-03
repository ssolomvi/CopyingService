package ru.mai.model.print;


import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

public abstract class PrintableWithFile extends PrintableInColor {

    @Schema(example = "my-beautiful-file",
            description = "Filename for the file to print")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        if (StringUtils.isWhitespace(filename)) throw new IllegalArgumentException("File is specified incorrectly");
        this.filename = filename;
    }

}
