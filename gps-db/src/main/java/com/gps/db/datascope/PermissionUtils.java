package com.gps.db.datascope;

import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * 自定义权限相关工具类
 *
 * @author GaoYuan
 * @date 2018/4/17 上午11:45
 */
public class PermissionUtils {

    /**
     * 根据 StatementHandler 获取 注解对象
     *
     * @author GaoYuan
     * @date 2018/4/17 上午11:45
     */
    public static PermissionAop getPermissionByDelegate(MappedStatement mappedStatement) {
        PermissionAop permissionAop = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
            final Class cls = Class.forName(className);
            final Method[] method = cls.getMethods();
            for (Method me : method) {
                if (me.getName().equals(methodName) && me.isAnnotationPresent(PermissionAop.class)) {
                    permissionAop = me.getAnnotation(PermissionAop.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionAop;
    }
}