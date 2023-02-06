package io.github.martinyes;

import com.google.gson.JsonObject;
import io.github.martinyes.page.Page;
import io.github.martinyes.util.JsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpServer {

    @Getter private static HttpServer instance;
    @Getter private static Javalin APP;

    public HttpServer() {
        instance = this;
    }

    public static void main(String[] args) {
        new HttpServer().start();
    }

    public void start() {
        APP = Javalin.create().start(5284);
        APP.before("*", ctx -> ctx.contentType("application/json"));

        addPage(new Page() {
            @Override
            public Handler handle() {
                return ctx -> ctx.result(getResponseScheme(ctx).toString());
            }

            @Override
            public String path() {
                return "/get";
            }
        });
        addPage(new Page() {
            @Override
            public Handler handle() {
                return ctx -> ctx.redirect("/redirectLoop2");
            }

            @Override
            public String path() {
                return "/redirectLoop";
            }
        });
        addPage(new Page() {
            @Override
            public Handler handle() {
                return ctx -> ctx.redirect("/redirectLoop");
            }

            @Override
            public String path() {
                return "/redirectLoop2";
            }
        });
        addPage(new Page() {
            @Override
            public Handler handle() {
                return ctx -> ctx.redirect("/get?redirect=true");
            }

            @Override
            public String path() {
                return "/redirect";
            }
        });
    }

    public JsonObject getResponseScheme(Context ctx) {
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
        root.add("protocol", ctx.protocol());
        root.add("method", ctx.method());
        root.add("redirect", (ctx.queryParam("redirect") != null));
        root.add("args", paramsTree.build());
        root.add("headers", headerTree.build());

        return root.build();
    }

    private JsonObject parseValueTree(List<String> values) {
        JsonBuilder builder = new JsonBuilder();
        AtomicInteger index = new AtomicInteger();

        values.forEach(val -> builder.add(String.valueOf(index.getAndIncrement()), val));

        return builder.build();
    }

    private void addPage(Page page) {
        switch (page.method()) {
            case GET: {
                APP.get(page.path(), page.handle());
                break;
            }

            case POST: {
                APP.post(page.path(), page.handle());
                break;
            }

            case PUT: {
                APP.put(page.path(), page.handle());
                break;
            }

            case DELETE: {
                APP.delete(page.path(), page.handle());
                break;
            }
        }
    }
}