package ru.mai.model.print;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.mai.model.enums.Color;

public abstract class PrintableInColor {

    @Schema(example = "blackAndWhite",
            description = "Color mode of printing")
    @NotNull
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
