package cloud.lexium.httpclient.config;

import java.time.Duration;

public interface Config {

    Duration readTimeout();

    Duration connectTimeout();
}