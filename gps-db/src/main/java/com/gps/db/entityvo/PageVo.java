package com.gps.db.entityvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zwb
 */
@Data
public class PageVo implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页号")
    private Long pageNum;

    @ApiModelProperty(value = "页面大小")
    private Long pageSize;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;
}
