package ru.mai.config.asyc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.config.property.ExecutorConfiguration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class AsyncConfig {

    @Autowired
    private ExecutorConfiguration executorConfiguration;

    @Bean(name = "blackWhitePrinterExecutor")
    public ScheduledExecutorService blackWhitePrinterExecutor() {
        var config = executorConfiguration.getBlackAndWhite();

        var scheduledExecutor = new ScheduledThreadPoolExecutor(config.getCorePoolSize());
        scheduledExecutor.setMaximumPoolSize(config.getMaxPoolSize());
        scheduledExecutor.setThreadFactory(new SchedulerThreadFactory("blackWhitePrinterExecutor-"));

        return scheduledExecutor;
    }

    @Bean(name = "colorPrinterExecutor")
    public ScheduledExecutorService colorPrinterExecutor() {
        var config = executorConfiguration.getColor();

        var scheduledExecutor = new ScheduledThreadPoolExecutor(config.getCorePoolSize());
        scheduledExecutor.setMaximumPoolSize(config.getMaxPoolSize());
        scheduledExecutor.setThreadFactory(new SchedulerThreadFactory("colorPrinterExecutor-"));

        return scheduledExecutor;
    }

}
