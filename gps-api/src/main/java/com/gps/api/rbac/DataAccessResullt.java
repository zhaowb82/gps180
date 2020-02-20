package com.gps.api.rbac;

import lombok.Data;

import java.util.List;

/**
 * 通过DataAccess 得出跟查询相关的用户或者组织机构列表
 *
 * @author lijiazhi
 */
@Data
public class DataAccessResullt {
    private List<Long> userIds;
    private List<Long> orgIds;
    //1 结果仅仅包含用户， 2 ，结果仅仅包含组织机构 3 结果匹配所有组织结构 4 结果不匹配任何组织机构
    private AccessType status;
}
