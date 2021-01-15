package cloud.lexium.httpclient.data.request;

import cloud.lexium.httpclient.utils.GeneralUtils;
import com.google.common.collect.Multimap;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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

        return "?" + request.getParams().entries().stream()
                .map(e -> e.getKey() + "=" + encode(e.getValue()))
                .collect(Collectors.joining("&"));
    }

    private boolean canBuild(HttpRequest request) {
        return !request.getParams().isEmpty();
    }

    @SneakyThrows
    private String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
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