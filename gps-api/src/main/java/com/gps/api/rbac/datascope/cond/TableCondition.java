package com.gps.api.rbac.datascope.cond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableCondition {
    private String operator;
    private String fieldName;
    private Object fieldValue;
}
