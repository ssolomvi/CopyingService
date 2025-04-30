package ru.mai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service(PhotoService.NAME)
public class PhotoService {

    public final static String NAME = "mai_PhotoService";

    private static final Logger log = LoggerFactory.getLogger(PhotoService.class);

    @Value("${app.time-to.take-a-photo}")
    private Integer takeAPhoto;

    public String takePhoto() {
        try {
            log.info("Taking a photo...");
            Thread.sleep(takeAPhoto * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return String.format(
                "%s-photo.jpg",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
        );
    }

}
