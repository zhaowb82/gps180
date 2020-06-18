package com.gps.api.rbac.datascope;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.gps.db.datascope.PermissionAop;
import com.gps.db.datascope.PermissionUtils;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.io.StringReader;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author lengleng
 * @date 2019/2/1
 * <p>
 * mybatis 数据权限拦截器
 */
@Slf4j
@Setter
@Accessors(chain = true)
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {

    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
//        if (log.isInfoEnabled()) {
//            log.info("进入 PrepareInterceptor 拦截器...");
//        }
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
        // 可以分离出最原始的的目标类)
        while (metaObject.hasGetter("h")) {
            Object object = metaObject.getValue("h");
            metaObject = SystemMetaObject.forObject(object);
        }
        // 分离最后一个代理对象的目标类
        while (metaObject.hasGetter("target")) {
            Object object = metaObject.getValue("target");
            metaObject = SystemMetaObject.forObject(object);
        }
        this.sqlParser(metaObject);

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType(); //获得方法类型
        if (SqlCommandType.SELECT != sqlCommandType
                || StatementType.CALLABLE == mappedStatement.getStatementType()) { // 先判断是不是SELECT操作
            return invocation.proceed();
        }
//        BoundSql boundSql1 = statementHandler.getBoundSql();
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object paramObj = boundSql.getParameterObject();
        String sql = boundSql.getSql();
        if (sql.contains("NEXTVAL")){ // 去掉子序列函数
            return invocation.proceed();
        }

        //获取方法dao id
        String id = mappedStatement.getId();
        if (id.contains("InventoryInoutMapper") || id.contains("selectSaleReportByGmtCreate")
                || id.contains("selectBiShipReportByGmtCreate") || id.contains("selectTransNoinventoryReport")
                || id.contains("queryPurStoDetails") || id.contains("queryTaskDailyCloseIds")) { // 剔除不需要走拦截器的方法
            return invocation.proceed();
        }
        try {
            PermissionAop permissionAop = PermissionUtils.getPermissionByDelegate(mappedStatement);
            if (permissionAop != null) {
                if (log.isInfoEnabled()) {
                    log.debug("数据权限处理【拼接SQL】...");
                }
                boolean onlyU = permissionAop.user();
                String taAlis = permissionAop.tableAlias();
//            if ("需要增强的方法的id".equals(id)) {
                //增强sql代码块
                //如果是select就将sql转成SELECT对象
                Select select = (Select) parserManager.parse(new StringReader(sql));
                //访问各个visitor
                select.getSelectBody().accept(new SelectVisitorImpl(permissionAop));
                //将增强后的sql放回
                metaObject.setValue("delegate.boundSql.sql", select.toString());
//            }
                return invocation.proceed();
            }
//            if (log.isInfoEnabled()) {
//                log.info("数据权限放行...");
//            }
            return invocation.proceed();
        } catch (Throwable e) {
            log.error("拦截器异常Start");
//            log.error(e.getMessage());
            e.printStackTrace();
            log.error("拦截器异常End");
        }
        //查找参数中包含DataScope类型的参数
//        DataScope dataScope = findDataScopeObject(paramObj);
//        if (dataScope != null) {
//            String scopeName = dataScope.getScopeName();
//            List<Integer> deptIds = dataScope.getDeptIds();
////            if (StrUtil.isNotBlank(scopeName) && CollectionUtil.isNotEmpty(deptIds)) {
////                String join = CollectionUtil.join(deptIds, ",");
////                originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
////                metaObject.setValue("delegate.boundSql.sql", originalSql);
////            }
//            return invocation.proceed();
//        }
        return null;
    }

    /**
     * 权限sql包装
     *
     * @author GaoYuan
     * @date 2018/4/17 上午9:51
     */
    protected String permissionSql(String sql) {
        StringBuilder sbSql = new StringBuilder(sql);
//        String userMethodPath = PermissionConfig.getConfig("permission.client.userid.method");
//        //当前登录人
//        String userId = (String)ReflectUtil.reflectByPath(userMethodPath);
//        //如果用户为 1 则只能查询第一条
//        if("1".equals(userId)){
//            //sbSql = sbSql.append(" limit 1 ");
//            //如果有动态参数 regionCd
//            if(true){
//                String premission_param = "regionCd";
//                //select * from (select id,name,region_cd from sys_exam ) where region_cd like '${}%'
//                String methodPath = PermissionConfig.getConfig("permission.client.params." + premission_param);
//                String regionCd = (String)ReflectUtil.reflectByPath(methodPath);
//                sbSql = new StringBuilder("select * from (").append(sbSql).append(" ) s where s.regionCd like concat("+ regionCd +",'%')  ");
//            }
//
//        }
        return sbSql.toString();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }
//
//    /**
//     * 查找参数是否包括DataScope对象
//     *
//     * @param parameterObj 参数列表
//     * @return DataScope
//     */
//    private DataScope findDataScopeObject(Object parameterObj) {
//        if (parameterObj instanceof DataScope) {
//            return (DataScope) parameterObj;
//        } else if (parameterObj instanceof Map) {
//            for (Object val : ((Map<?, ?>) parameterObj).values()) {
//                if (val instanceof DataScope) {
//                    return (DataScope) val;
//                }
//            }
//        }
//        return null;
//    }

}
