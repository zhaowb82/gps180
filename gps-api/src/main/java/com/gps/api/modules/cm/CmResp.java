package com.gps.api.modules.cm;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class CmResp<T> {
    private int code; //Y
    private String error;
    @JsonDeserialize(using = DataSerializer.class)
    private T data;
}
