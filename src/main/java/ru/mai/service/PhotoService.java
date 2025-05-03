package ru.mai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service(PhotoService.NAME)
public class PhotoService {

    public final static String NAME = "mai_PhotoService";

    private static final Logger log = LoggerFactory.getLogger(PhotoService.class);
    private static final NumberFormat nFormat = NumberFormat.getInstance();

    private final Lock lock = new ReentrantLock();

    @Value("${app.time-to.take-a-photo}")
    private Integer takeAPhoto;

    public String takePhoto() {
        try {
            lock.lock();
            log.info("Taking a photo... this will take {} ms", nFormat.format(takeAPhoto));
            Thread.sleep(takeAPhoto);

            var photoName = String.format(
                    "%s-photo.jpg",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS"))
            );

            log.info("Took a photo: {}", photoName);

            return photoName;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
