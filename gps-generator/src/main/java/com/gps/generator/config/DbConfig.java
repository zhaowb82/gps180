/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.generator.config;

import com.gps.generator.dao.GeneratorDao;
import com.gps.generator.dao.MySQLGeneratorDao;
import com.gps.generator.dao.OracleGeneratorDao;
import com.gps.generator.dao.PostgreSQLGeneratorDao;
import com.gps.generator.dao.SQLServerGeneratorDao;
import com.gps.generator.utils.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置
 *
 * @author Zb zb@qq.com
 */
@Configuration
public class DbConfig {
    @Value("${gps180.database: mysql}")
    private String database;
    @Autowired
    private MySQLGeneratorDao mySQLGeneratorDao;
    @Autowired
    private OracleGeneratorDao oracleGeneratorDao;
    @Autowired
    private SQLServerGeneratorDao sqlServerGeneratorDao;
    @Autowired
    private PostgreSQLGeneratorDao postgreSQLGeneratorDao;

    @Bean
    @Primary
    public GeneratorDao getGeneratorDao() {
        if ("mysql".equalsIgnoreCase(database)) {
            return mySQLGeneratorDao;
        } else if ("oracle".equalsIgnoreCase(database)) {
            return oracleGeneratorDao;
        } else if ("sqlserver".equalsIgnoreCase(database)) {
            return sqlServerGeneratorDao;
        } else if ("postgresql".equalsIgnoreCase(database)) {
            return postgreSQLGeneratorDao;
        } else {
            throw new RRException("不支持当前数据库：" + database);
        }
    }
}
