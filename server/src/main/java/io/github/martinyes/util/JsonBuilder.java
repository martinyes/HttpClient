package io.github.martinyes.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonBuilder {

    private final JsonObject jsonObject = new JsonObject();

    public JsonBuilder add(String property, String value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, Number value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, Boolean value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, Character value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    public JsonBuilder add(String property, JsonElement value) {
        this.jsonObject.add(property, value);
        return this;
    }

    public JsonObject build() {
        return this.jsonObject;
    }
}