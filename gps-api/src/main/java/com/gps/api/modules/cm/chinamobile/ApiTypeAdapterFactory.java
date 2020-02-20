package com.gps.api.modules.cm.chinamobile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.gps.api.modules.cm.ApiException;

import java.io.IOException;

/**
 * Created by zwb.
 */
public class ApiTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementTypeAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementTypeAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    boolean hasStatus = jsonObject.has("code");
                    boolean hasMsg = jsonObject.has("error");
                    boolean hasData = jsonObject.has("data");
                    if (hasStatus && 2 <= jsonObject.size() && jsonObject.size() <= 3 && (hasMsg || hasData)) {
                        try {
                            int status = jsonObject.get("code").getAsInt();
                            if (status == 0) {
                                if (hasData && !jsonObject.get("data").isJsonNull()) {
                                    //jsonElement = jsonObject.get("data");
                                    String data = jsonObject.get("data").getAsString();
                                    jsonElement = elementTypeAdapter.fromJson(data);
                                } else {
                                    hasData = false;
                                }
                            } else {
                                hasData = false;
                            }
                            if (!hasData) {
                                String message = "gps180 Unknown Error";
                                if (hasMsg) {
                                    message = jsonObject.get("error").getAsString();
                                }
                                throw new ApiException(status, message);
                            }
                        } catch (NumberFormatException | UnsupportedOperationException e) {
                            throw new ApiException(500, e.getMessage());
                        }
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }

        }.nullSafe();
    }
}
