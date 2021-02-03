package io.github.martinyes.httpclient.config;

import java.time.Duration;

/**
 * An abstract config class used to configure some basic options.
 *
 * @author martin
 */
public interface Config {

    /**
     * Sets read timeout to the socket server.
     *
     * @return the duration
     */
    Duration readTimeout();

    /**
     * Sets the connect timeout to the socket server.
     *
     * @return the duration
     */
    Duration connectTimeout();
}