package com.gps.db.entityvo;

import com.gps.db.entity.GeofenceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qulong
 * @date 2020/2/4 10:59
 * @description
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GeofenceListVo extends GeofenceEntity {
    private Integer deviceCount;
}
