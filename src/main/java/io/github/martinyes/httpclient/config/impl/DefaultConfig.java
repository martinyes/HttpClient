package io.github.martinyes.httpclient.config.impl;

import io.github.martinyes.httpclient.config.Config;

import java.time.Duration;

/**
 * A default implementation of {@link Config} used for requests not providing any custom config.
 *
 * @author martin
 */
public class DefaultConfig implements Config {

    /**
     * Sets read timeout to the socket server.
     *
     * @return the duration
     */
    @Override
    public Duration readTimeout() {
        return Duration.ofSeconds(10);
    }

    /**
     * Sets the connect timeout to the socket server.
     *
     * @return the duration
     */
    @Override
    public Duration connectTimeout() {
        return Duration.ofSeconds(10);
    }
}