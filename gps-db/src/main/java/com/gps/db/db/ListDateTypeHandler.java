package com.gps.db.db;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({List.class})
public class ListDateTypeHandler<T> extends BaseTypeHandler<List<T>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, Types.VARCHAR);
        } else {
            ps.setString(i, JSONObject.toJSONString(parameter));
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String ss = rs.getString(columnName);
        if (StringUtils.isEmpty(ss)) {
            return null;
        }
        return JSONObject.parseObject(ss, new TypeReference<List<T>>(){});
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String ss = rs.getString(columnIndex);
        if (StringUtils.isEmpty(ss)) {
            return null;
        }
        return JSONObject.parseObject(ss, new TypeReference<List<T>>(){});
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String ss = cs.getString(columnIndex);
        if (StringUtils.isEmpty(ss)) {
            return null;
        }
        return JSONObject.parseObject(ss, new TypeReference<List<T>>(){});
    }

}