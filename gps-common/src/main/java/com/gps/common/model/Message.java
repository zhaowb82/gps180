package com.gps.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Message extends ExtendedModel {

    private String type;

}
