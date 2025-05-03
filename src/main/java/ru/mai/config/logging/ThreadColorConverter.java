package ru.mai.config.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ThreadColorConverter extends ClassicConverter {

    // ANSI-коды цветов
    private static final String RESET = "\u001B[0m";
    private static final String BLACK_WHITE = "\u001B[97;40m"; // white text, black background

    @Override
    public String convert(ILoggingEvent event) {
        String threadName = event.getThreadName();

        if (threadName.matches(".*[Bb]lackWhitePrinterExecutor.*")) {
            return BLACK_WHITE + threadName + RESET;
        } else if (threadName.matches(".*[Cc]olorPrinterExecutor.*")) {
            return generateRainbow(threadName) + RESET;
        } else {
            return threadName;
        }
    }

    // Генерация радужного текста
    private String generateRainbow(String text) {
        var sb = new StringBuilder();
        int[] colors = {31, 33, 32, 36, 34, 35}; // red, yellow, green, light blue, blue, purple
        for (var i = 0; i < text.length(); i++) {
            var c = text.charAt(i);
            var color = colors[i % colors.length];
            sb.append("\u001B[").append(color).append("m").append(c);
        }

        return sb.toString();
    }

}
