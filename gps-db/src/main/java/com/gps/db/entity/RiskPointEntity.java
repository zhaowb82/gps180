package com.gps.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Gps高风险点
 * @author qulong
 * @date 2020/2/3 14:08
 * @description
 */
@Data
@TableName("alarm")
public class RiskPointEntity {
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    private Double longitude;
    private Double latitude;
    private String addressName;
    private String storeName;
    private String cityName;
    private String cityId;
}
