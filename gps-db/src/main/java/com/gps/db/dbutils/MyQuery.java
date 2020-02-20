
package com.gps.db.dbutils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gps.db.dbutils.xss.SQLFilter;
import org.apache.commons.lang3.StringUtils;


import java.util.Map;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class MyQuery {

    //Constant.PAGE Constant.LIMIT Constant.ORDER_FIELD Constant.ORDER
    public static <T> IPage<T> getPage(Map<String, Object> params) {
        return getPage(params, null, false);
    }

    public static <T> IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }
        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));
        String order = (String) params.get(Constant.ORDER);
        return getPage(curPage, limit, orderField, order, defaultOrderField, isAsc);
    }

    public static <T> IPage<T> getPage(Long curPage, Long limit, String orderField, String order) {
        return getPage(curPage, limit, orderField, order, null, false);
    }

    public static <T> IPage<T> getPage(Long curPage, Long limit, String orderField, String order, String defaultOrderField, boolean isAsc) {
        if (curPage == null || curPage == 0) curPage = 1L;
        if (limit == null || limit == 0) limit = 10L;

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

//        //分页参数
//        params.put(Constant.PAGE, page);

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        if (defaultOrderField != null) {
            //默认排序
            if (isAsc) {
                page.addOrder(OrderItem.asc(defaultOrderField));
            } else {
                page.addOrder(OrderItem.desc(defaultOrderField));
            }
        }
        return page;
    }
}
