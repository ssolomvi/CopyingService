package ru.mai.model.print;


import io.swagger.v3.oas.annotations.media.Schema;

public abstract class PrintableWithFile extends PrintableInColor {

    @Schema(example = "my-beautiful-file",
            description = "Filename for the file to print")
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
