package cloud.lexium.httpclient.data.request;

import cloud.lexium.httpclient.utils.GeneralUtils;
import com.google.common.collect.Multimap;
import com.google.common.net.UrlEscapers;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ParamProcessor {

    public Multimap<String, String> parse(String[] params) {
        List<String> keys = extract(0, Arrays.asList(params));
        List<String> values = extract(1, Arrays.asList(params));

        return GeneralUtils.zipToMap(keys, values);
    }

    public String buildQueryURL(HttpRequest request) {
        if (request.getParams() == null || !canBuild(request))
            return "";

        final StringBuilder builder = new StringBuilder();

        builder.insert(0, '?');
        for (Map.Entry<String, String> entry : request.getParams().entries()) {
            builder.append(encode(entry.getKey())).append('=')
                    .append(encode(entry.getValue())).append('&');
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    private boolean canBuild(HttpRequest request) {
        return !request.getParams().isEmpty();
    }

    @SneakyThrows
    public String encode(String s) {
        return UrlEscapers.urlPathSegmentEscaper().escape(s);
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