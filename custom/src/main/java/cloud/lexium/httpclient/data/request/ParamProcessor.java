package cloud.lexium.httpclient.data.request;

import cloud.lexium.httpclient.utils.GeneralUtils;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ParamProcessor {

    public Map<String, String[]> parse(String[] params) {
        List<String> keys = extract(0, Arrays.asList(params));
        List<String> values = extract(1, Arrays.asList(params));

        Map<String, String> parsed = GeneralUtils.zipToMap(keys, values);

        parsed.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, e -> new String[]{e.getValue()}
        )).forEach((k, v) -> {
            System.out.println("k: " + k + ", v: " + Arrays.toString(v));
        });

        return parsed.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, e -> new String[]{e.getValue()}
        ));
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