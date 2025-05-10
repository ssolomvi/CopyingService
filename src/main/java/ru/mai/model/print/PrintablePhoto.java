package ru.mai.model.print;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.mai.model.enums.PhotoSize;

public class PrintablePhoto extends PrintableWithFile {

    @Schema(example = "SIZE_10X15",
            description = "Size of photo to print")
    @NotNull
    private PhotoSize photoSize;

    public PhotoSize getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(PhotoSize photoSize) {
        this.photoSize = photoSize;
    }

    @Override
    public String toString() {
        return String.format(
                "\"Photo\": { \"id\": %s, \"color\": %s,\"filename\": %s,\"photoSize\": %s }",
                getId(), getColor(), getFilename(), getPhotoSize()
        );
    }

}
