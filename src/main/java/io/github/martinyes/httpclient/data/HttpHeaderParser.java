package io.github.martinyes.httpclient.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.utils.GeneralUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author martin
 * @since 2
 */
public class HttpHeaderParser implements Pair<Multimap<String, String>> {

    @Getter private final Multimap<String, String> headersMap = ArrayListMultimap.create();

    @Override
    public Multimap<String, String> parse(String[] array) {
        List<String> keys = extract(0, Arrays.asList(array));
        List<String> values = extract(1, Arrays.asList(array));

        Multimap<String, String> result = GeneralUtils.zipToMulti(keys, values);
        this.headersMap.putAll(result);

        return result;
    }

    public StringBuilder build(HttpRequest request) {
        StringBuilder builder = new StringBuilder();

        if (request.getHeaders().getHeadersMap() == null || request.getHeaders().getHeadersMap().isEmpty())
            return null;

        for (Map.Entry<String, Collection<String>> entry : request.getHeaders().getHeadersMap().asMap().entrySet()) {
            builder.append(entry.getKey()).append(": ");
            builder.append(String.join(", ", entry.getValue()));
            builder.append("\r\n");
        }

        return builder;
    }
}