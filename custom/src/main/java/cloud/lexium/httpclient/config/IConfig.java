package cloud.lexium.httpclient.config;

import java.time.Duration;

public interface IConfig {

    Duration readTimeout();

    Duration connectTimeout();
}