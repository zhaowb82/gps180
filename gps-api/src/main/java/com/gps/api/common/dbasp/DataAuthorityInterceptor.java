package com.gps.api.common.dbasp;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Executor;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
        })
})
public class DataAuthorityInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String originalSql = boundSql.getSql().trim();
        Object parameterObject = boundSql.getParameterObject();
        String check_user = null;

        if (parameterObject instanceof HashMap) {
            try {
                check_user = (String) ((HashMap) parameterObject).get("check_user");
            } catch (Exception e) {
            }
        }
        if (check_user != null) {
            //获取角色
            String getRoleSql = "select a.grpcname from TBLGROUPINFO a,tblusergroup b where a.grpid=b.grpid and b.workid=?";
            Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            PreparedStatement countStmt = connection.prepareStatement(getRoleSql);
            countStmt.setString(1, check_user);
            ResultSet rs = countStmt.executeQuery();
            String role = null;
            if (rs.next()) {
                role = rs.getString(1);
            }
            rs.close();
            countStmt.close();
            connection.close();

            if (role != null) {
                if (role.equals("新沂管理员") || role.equals("系统管理员")) {
                    //查看全部
                } else if (role.equals("风险员")) {
                    if (originalSql.toLowerCase().indexOf("checker_userid") != -1) {
                        String usersSql = "select a.userid from tbldepartment_user a where a.departmentcode=(select b.departmentcode from tbldepartment_user b where b.userid='" + check_user + "')";
                        String riskSql = "select a.* from (" + originalSql + ")a  where a.checker_userid in (" + usersSql + ")";

                        BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, riskSql);
                        ParameterMap map = mappedStatement.getParameterMap();
                        //ParameterMapping mapping=new ParameterMapping.Builder(mappedStatement.getConfiguration(), check_user, String.class).build();
                        //map.getParameterMappings().add(mapping);
                        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql), map);
                        invocation.getArgs()[0] = newMs;
                    }
                } else if (role.equals("支行副行长") || role.equals("客户经理")) {
                    if (originalSql.toLowerCase().indexOf("checker_userid") != -1) {
                        String selfSql = "select a.* from (" + originalSql + ")a  where a.checker_userid='" + check_user + "'";
                        BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, selfSql);
                        ParameterMap map = mappedStatement.getParameterMap();
                        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql), map);
                        invocation.getArgs()[0] = newMs;
                    }
                } else {
                    throw new RuntimeException("角色错误");
                }
            }
        }
        Object obj = invocation.proceed();
        return obj;
    }

    public class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    /**
     * 复制MappedStatement对象
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, ParameterMap parameterMap) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        // builder.keyProperty(ms.getKeyProperty());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(parameterMap);
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 复制BoundSql对象
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    @Override
    public void setProperties(Properties arg0) {

    }

}