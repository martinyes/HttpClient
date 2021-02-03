package io.github.martinyes.httpclient.data.request;

import io.github.martinyes.httpclient.utils.GeneralUtils;
import com.google.common.collect.Multimap;
import com.google.common.net.UrlEscapers;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A builder for building query URL.
 * <p>
 * The ParamProcessor is used to create a query String with the given parameters.
 *
 * @author martin
 */
@UtilityClass
public class ParamProcessor {

    /**
     * This method is used to generate a MultiMap from the given params.
     *
     * @param params the params
     * @return a MultiMap built from params
     */
    public Multimap<String, String> parseParams(String[] params) {
        List<String> keys = extract(0, Arrays.asList(params));
        List<String> values = extract(1, Arrays.asList(params));

        return GeneralUtils.zipToMap(keys, values);
    }

    /**
     * This method is used to build the actual query String
     * using the given HTTP Request params parsed with {@link ParamProcessor#parseParams(String[])}.
     *
     * @param request the request
     * @return the query String
     */
    public String buildQueryURL(HttpRequest request) {
        if (request.getParams() == null || !shouldBuild(request))
            return "";

        if (request.getParams().size() % 2 != 0)
            throw new IllegalArgumentException("Need both key and value for parameters.");

        final StringBuilder builder = new StringBuilder();

        builder.insert(0, '?');
        for (Map.Entry<String, String> entry : request.getParams().entries()) {
            builder.append(encode(entry.getKey())).append('=')
                    .append(encode(entry.getValue())).append('&');
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    /**
     * This method encodes the given String.
     * <p>
     * URL encoding translates special characters from the URL to a representation
     * that adheres to the spec and can be correctly understood and interpreted.
     *
     * @param s the String that needs to be encoded
     * @return the encoded String
     */
    @SneakyThrows
    public String encode(String s) {
        return UrlEscapers.urlPathSegmentEscaper().escape(s);
    }

    private boolean shouldBuild(HttpRequest request) {
        return !request.getParams().isEmpty();
    }

    private List<String> extract(int start, List<String> data) {
        int skip = 2;
        int size = data.size();

        // Limit to carefully avoid IndexOutOfBoundsException
        int limit = size / skip + size % skip;

        return Stream.iterate(start, i -> i + skip)
                .limit(limit)
                .map(data::get)
                .collect(Collectors.toList());
    }
}