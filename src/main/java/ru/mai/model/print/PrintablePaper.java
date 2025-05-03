package ru.mai.model.print;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.mai.model.enums.PaperSize;

public class PrintablePaper extends PrintableWithFile {

    @Schema(example = "A4",
            description = "Format of the paper to print")
    @NotNull
    private PaperSize paperSize;

    public PaperSize getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(PaperSize paperSize) {
        this.paperSize = paperSize;
    }

    @Override
    public String toString() {
        return String.format("\"Paper\": { \"id\": %s, \"color\": %s,\"filename\": %s,\"paperSize\": %s }",
                getId(), getColor(), getFilename(), getPaperSize());
    }

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Override
    public String getFilename() {
        return super.getFilename();
    }

    @Override
    public void setFilename(@NotBlank String filename) {
        super.setFilename(filename);
    }

}
