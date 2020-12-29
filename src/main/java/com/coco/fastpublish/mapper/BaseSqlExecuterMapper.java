package com.coco.fastpublish.mapper;


import com.coco.fastpublish.sqlprovider.BaseSqlExecuterProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BaseSqlExecuterMapper {

    /**
     * 适用于查询语句
     * @param map 参数map封装
     * @param sqlFragment 动态sql片段
     * @return 结果集
     */
    @SelectProvider(type = BaseSqlExecuterProvider.class, method = "executeSelect")
    List<Map> executeSelect(@Param("map")Map map,@Param("sqlFragment")String sqlFragment);

    @SelectProvider(type = BaseSqlExecuterProvider.class, method = "execute")
    List<Map> executeSelectByBean(@Param("param")Object param,@Param("sqlFragment")String sqlFragment);

    /**
     * 适用于更新语句
     * @param map 参数map封装
     * @param sqlFragment 动态sql片段
     * @return 影响行数
     */
    @UpdateProvider(type = BaseSqlExecuterProvider.class, method = "executeSelect")
    int executeUpdate(@Param("map")Map map,@Param("sqlFragment")String sqlFragment);

    /**
     * 适用于更新语句
     * @param map 参数map封装
     * @param sqlFragment 动态sql片段
     * @return 是否更新成功
     */
    @UpdateProvider(type = BaseSqlExecuterProvider.class, method = "executeSelect")
    boolean executeUpdateRetBool(@Param("map")Map map,@Param("sqlFragment")String sqlFragment);

    /**
     * 适用于删除语句
     * @param map 参数map封装
     * @param sqlFragment 动态sql片段
     * @return 影响行数
     */
    @UpdateProvider(type = BaseSqlExecuterProvider.class, method = "executeSelect")
    int executeDelete(@Param("map")Map map,@Param("sqlFragment")String sqlFragment);

    /**
     * 适用于删除语句
     * @param map 参数map封装
     * @param sqlFragment 动态sql片段
     * @return 是否删除成功
     */
    @UpdateProvider(type = BaseSqlExecuterProvider.class, method = "executeSelect")
    boolean executeDeleteRetBool(@Param("map")Map map,@Param("sqlFragment")String sqlFragment);

    /**
     * 适用于返回整数值的sql
     * @param param 参数
     * @param sqlFragment 动态sql片段
     * @return int值
     */
    @InsertProvider(type = BaseSqlExecuterProvider.class, method = "execute")
    int execute(@Param("param")Object param,@Param("sqlFragment")String sqlFragment);

    /**
     * 适用于返回布尔值的sql
     * @param param 参数
     * @param sqlFragment 动态sql片段
     * @return 布尔值
     */
    @InsertProvider(type = BaseSqlExecuterProvider.class, method = "execute")
    boolean executeRetBool(@Param("param")Object param,@Param("sqlFragment")String sqlFragment);

    @InsertProvider(type = BaseSqlExecuterProvider.class, method = "executeParse")
    int simpleEexecute(@Param("param")Object param,@Param("sqlFragment")String sqlFragment);

    @InsertProvider(type = BaseSqlExecuterProvider.class, method = "executeParse")
    boolean simpleExecuteRetBool(@Param("param")Object param,@Param("sqlFragment")String sqlFragment);

}
