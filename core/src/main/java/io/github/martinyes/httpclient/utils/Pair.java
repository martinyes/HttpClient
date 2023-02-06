package io.github.martinyes.httpclient.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface Pair<T> {

    T parse(String[] p0);

    default List<String> extract(int p0, List<String> p1) {
        int skip = 2;
        int size = p1.size();

        // Limit to carefully avoid IndexOutOfBoundsException
        int limit = size / skip + size % skip;

        return Stream.iterate(p0, i -> i + skip)
                .limit(limit)
                .map(p1::get)
                .collect(Collectors.toList());
    }
}
