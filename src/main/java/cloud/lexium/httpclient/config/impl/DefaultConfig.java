package cloud.lexium.httpclient.config.impl;

import cloud.lexium.httpclient.config.Config;

import java.time.Duration;

public class DefaultConfig implements Config {

    @Override
    public Duration readTimeout() {
        return Duration.ofSeconds(10);
    }

    @Override
    public Duration connectTimeout() {
        return Duration.ofSeconds(10);
    }
}