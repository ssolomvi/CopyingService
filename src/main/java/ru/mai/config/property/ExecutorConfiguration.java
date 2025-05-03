package ru.mai.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.executor")
public class ExecutorConfiguration {

    private ExecutorProperties blackAndWhite = new ExecutorProperties();

    private ExecutorProperties color = new ExecutorProperties();

    private Integer initialDelay;

    private Integer delay;

    private Integer queueCapacity;

    public ExecutorProperties getBlackAndWhite() {
        return blackAndWhite;
    }

    public void setBlackAndWhite(ExecutorProperties blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public ExecutorProperties getColor() {
        return color;
    }

    public void setColor(ExecutorProperties color) {
        this.color = color;
    }

    public Integer getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(Integer initialDelay) {
        this.initialDelay = initialDelay;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public static class ExecutorProperties {

        private Integer corePoolSize;
        private Integer maxPoolSize;

        public Integer getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(Integer corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public Integer getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(Integer maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

    }

}
