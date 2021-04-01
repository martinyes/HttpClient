package io.github.martinyes.httpclient;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.net.UrlEscapers;
import io.github.martinyes.httpclient.data.Pair;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.utils.GeneralUtils;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A builder for building query params.
 * <p>
 * The HttpParams is used to create a query String with the given parameters.
 *
 * @author martin
 * @version 2
 * @since 1
 */
public class HttpParams implements Pair<Multimap<String, String>> {

    @Getter private final Multimap<String, String> paramsMap = ArrayListMultimap.create();

    /**
     * This method is used to generate a MultiMap from the given params.
     *
     * @param params the params
     * @return a MultiMap built from params
     */
    @Override
    public Multimap<String, String> parse(String[] params) {
        if (params.length % 2 != 0)
            throw new IllegalArgumentException("Need both key and value for parameters.");

        List<String> keys = extract(0, Arrays.asList(params));
        List<String> values = extract(1, Arrays.asList(params));

        Multimap<String, String> result = GeneralUtils.zipToMulti(keys, values);
        this.paramsMap.putAll(result);

        return result;
    }

    /**
     * This method is used to build the actual query String
     * using the given HTTP Request params parsed through {@link Pair#parse(String[])}.
     *
     * @param request the request
     * @return the query String
     */
    public String build(HttpRequest request) {
        if (request.getParams().getParamsMap() == null || !shouldBuild(request))
            return "";

        final StringBuilder builder = new StringBuilder();

        builder.insert(0, '?');
        for (Map.Entry<String, String> entry : request.getParams().getParamsMap().entries()) {
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
    public static String encode(String s) {
        return UrlEscapers.urlPathSegmentEscaper().escape(s);
    }

    private static boolean shouldBuild(HttpRequest request) {
        return !request.getParams().getParamsMap().isEmpty();
    }
}