package io.github.martinyes.page;

import io.github.martinyes.HttpMethod;
import io.javalin.http.Handler;

public interface Page {

    Handler handle();

    String path();

    default HttpMethod method() {
        return HttpMethod.GET;
    }
}