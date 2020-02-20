package com.gps.db.datascope;

import java.lang.annotation.*;


/**
 * 数据权限过滤
 * @author GaoYuan
 * @date 2018/4/17 下午2:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAop {
    String value() default "";

    /**  表的别名 */
    String tableAlias() default  "";
    /**  true：没有本部门数据权限，也能查询本人数据 */
    boolean user() default true;
}