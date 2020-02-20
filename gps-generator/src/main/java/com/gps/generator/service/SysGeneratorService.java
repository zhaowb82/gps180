/**
 * Copyright (c) 2018-2020 GPS180 All rights reserved.
 * <p>
 * https://www.gps180.com
 * <p>
 * 版权所有，侵权必究！
 */

package com.gps.generator.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gps.generator.dao.GeneratorDao;
import com.gps.generator.utils.GenUtils;
import com.gps.generator.utils.PageUtils;
import com.gps.generator.utils.Query;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysGeneratorService {
    @Autowired
    private GeneratorDao generatorDao;

    public PageUtils queryList(Query query) {
        Page<?> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Map<String, Object>> list = generatorDao.queryList(query);

        return new PageUtils(list, (int) page.getTotal(), query.getLimit(), query.getPage());
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames, String mainPath, String moduleName, String author, String email) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, mainPath, moduleName, author, email, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
