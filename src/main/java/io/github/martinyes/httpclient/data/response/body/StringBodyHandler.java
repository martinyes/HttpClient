package io.github.martinyes.httpclient.data.response.body;

import io.github.martinyes.httpclient.data.response.BodyHandler;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;

/**
 * String Body Handler.
 *
 * @author martin
 */
public class StringBodyHandler implements BodyHandler<String> {

    @Override
    public String apply(WrappedHttpResponse res) {
        return res.getBody().toString();
    }
}