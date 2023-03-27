package com.nopecho.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static com.google.gson.JsonParser.parseString;

public class JsonUtils {

    private static volatile Gson gson;

    public static Gson get() {
        Gson result = gson;
        if (result == null) {
            synchronized (JsonUtils.class) {
                result = gson;
                if (result == null) {
                    gson = result = new Gson();
                }
            }
        }
        return result;
    }

    public static String getOrThrowJsonValue(String jsonString, String fieldName) {
        try {
            // JSON 문자열을 JsonObject 로 변환
            JsonElement jsonElement = parseString(jsonString);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // fieldName 이 "."으로 분리되어 있는 경우, depth 를 맞춰가며 JsonObject 를 탐색
            String[] fieldNames = fieldName.split("\\.");
            for (int i = 0; i < fieldNames.length - 1; i++) {
                String name = fieldNames[i];
                JsonElement element = jsonObject.get(name);
                if (element.isJsonArray()) {
                    // 배열일 경우, 첫 번째 요소의 JsonObject 가져옴
                    JsonArray jsonArray = element.getAsJsonArray();
                    if (jsonArray.size() > 0) {
                        jsonObject = jsonArray.get(0).getAsJsonObject();
                    }
                } else {
                    // 객체일 경우, JsonObject 가져옴
                    jsonObject = element.getAsJsonObject();
                }
            }

            String lastFieldName = fieldNames[fieldNames.length - 1];
            JsonElement lastJsonElement = jsonObject.get(lastFieldName);

            // 마지막 필드가 배열일 경우, 배열의 첫번째 값 반환
            if (lastJsonElement.isJsonArray()) {
                JsonArray jsonArray = lastJsonElement.getAsJsonArray();
                return jsonArray.get(0).getAsString();
            }

            // 마지막 필드가 일반 필드일 경우 값을 반환
            return lastJsonElement.getAsString();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(" %s 의 JSON 형식이 올바르지 않거나 %s 을 찾을 수 없습니다.", jsonString, fieldName));
        }
    }

    public static void validJsonFormat(String json) {
        try {
            parseString(json);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(" %s 의 JSON 형식이 올바르지 않습니다.", json));
        }
    }

    public static boolean isExistPropertyKeyFrom(Object o, String propKey) {
        try {
            String toJson = get().toJson(o);
            JsonObject jsonObject = get().fromJson(toJson, JsonObject.class);
            return jsonObject.has(propKey);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("[%s isExistPropertyKeyFrom %s] JSON 형식이 올바르지 않습니다.", o.toString(), propKey));
        }
    }

    public static String replaceJsonVariable(String jsonString, Object o, String supportVariable) {
        try {
            String toJson = get().toJson(o);
            JsonObject jsonObject = get().fromJson(toJson, JsonObject.class);
            String toJsonValue = jsonObject.get(supportVariable).getAsString();

            return jsonString.replace("$" + supportVariable , toJsonValue);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("[%s replace %s] JSON 값을 변경할 수 없습니다.", jsonString, supportVariable));
        }
    }
}
