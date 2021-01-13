package cloud.lexium.httpclient.config.impl;

import cloud.lexium.httpclient.config.IConfig;

import java.time.Duration;

public class DefaultConfiguration implements IConfig {

    @Override
    public Duration readTimeout() {
        return Duration.ofSeconds(10);
    }

    @Override
    public Duration connectTimeout() {
        return Duration.ofSeconds(10);
    }
}