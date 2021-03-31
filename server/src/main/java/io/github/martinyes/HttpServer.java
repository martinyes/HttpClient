package io.github.martinyes;

import com.google.gson.JsonObject;
import io.github.martinyes.util.JsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpServer {

    @Getter private static Javalin APP;

    public static void main(String[] args) {
        APP = Javalin.create().start(7000);

        APP.get("/test", ctx -> {
            ctx.result(getResponseScheme(ctx).toString()).contentType("application/json");
        });
    }

    public static JsonObject getResponseScheme(Context ctx) {
        JsonBuilder root = new JsonBuilder();

        JsonBuilder paramsTree = new JsonBuilder();
        Map<String, List<String>> params = ctx.queryParamMap();

        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            if (entry.getValue().size() < 2)
                paramsTree.add(entry.getKey(), entry.getValue().get(0));
            else
                paramsTree.add(entry.getKey(), parseValueTree(entry.getValue()));
        }

        JsonBuilder headerTree = new JsonBuilder();
        Map<String, String> headers = ctx.headerMap();

        for (Map.Entry<String, String> entry : headers.entrySet())
            headerTree.add(entry.getKey(), entry.getValue());

        root.add("url", ctx.fullUrl());
        root.add("http_ver", ctx.protocol());
        root.add("args", paramsTree.build());
        root.add("headers", headerTree.build());

        return root.build();
    }

    private static JsonObject parseValueTree(List<String> values) {
        JsonBuilder builder = new JsonBuilder();
        AtomicInteger index = new AtomicInteger();

        values.forEach(val -> builder.add(String.valueOf(index.getAndIncrement()), val));

        return builder.build();
    }
}