package io.github.martinyes.httpclient.utils;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GeneralUtils {

    public static <K, V> Multimap<K, V> zipToMulti(List<K> keys, List<V> values) {
        ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
        for (int i = 0; i < Math.min(keys.size(), values.size()); i++) {
            builder.put(keys.get(i), values.get(i));
        }

        return builder.build();
    }
}