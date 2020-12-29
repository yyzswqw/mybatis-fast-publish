package com.coco.fastpublish.service;

import java.util.List;
import java.util.Map;

public interface SqlExecuter {

    /**
     * 获取sql片段
     * @param sqlFragmentKey 缓存key
     * @param defaultSqlFragment 默认sql片段
     * @return sql片段
     */
    default String getSqlFragment(String sqlFragmentKey,String defaultSqlFragment){
        return defaultSqlFragment;
    }

    /**
     * 执行查询sql片段
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @param defaultSqlFragment 默认sql片段
     * @return 查询结果集合
     */
    default List<Map> executeSelect(String sqlFragmentKey, Map paramMap,String defaultSqlFragment){
        return null;
    }

    /**
     * 执行查询sql片段
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @return 查询结果集合
     */
    default List<Map> executeSelect(String sqlFragmentKey, Map paramMap) {
        return this.executeSelect(sqlFragmentKey,paramMap,null);
    }

    /**
     * 执行查询sql片段
     * @param paramMap 参数map集合
     * @param sqlFragment sql片段
     * @return 查询结果集合
     */
    default List<Map> executeSelect(Map paramMap, String sqlFragment){
        return null;
    }

    /**
     * 删除sql片段,使用默认的SqlExecuter(DefaultSqlExecuterImpl)在删除sqlFragment时务必调用该方法
     * @param sqlFragmentKey 缓存key
     * @return 是否删除成功
     */
    default boolean deleteSqlFragment(String sqlFragmentKey){
        return true;
    }

    /**
     * 更新sql片段,使用默认的SqlExecuter(DefaultSqlExecuterImpl)在更新sqlFragment时必须务必该方法
     * @param sqlFragmentKey 缓存key
     * @return 是否更新成功
     */
    default boolean updateSqlFragment(String sqlFragmentKey){
        return true;
    }

    /**
     * 执行更新语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @param defaultSqlFragment 默认sql片段
     * @return 影响行数
     */
    default int executeUpdate(String sqlFragmentKey, Map paramMap,String defaultSqlFragment){
        return 0;
    }

    /**
     * 执行更新语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @param defaultSqlFragment 默认sql片段
     * @return 是否更新成功
     */
    default boolean executeUpdateRetBool(String sqlFragmentKey, Map paramMap,String defaultSqlFragment){
        return this.executeUpdate(sqlFragmentKey,paramMap,defaultSqlFragment)>0;
    }

    /**
     * 执行删除语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @param defaultSqlFragment 默认sql片段
     * @return 影响行数
     */
    default int executeDelete(String sqlFragmentKey, Map paramMap,String defaultSqlFragment){
        return 0;
    }

    /**
     * 执行删除语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @param defaultSqlFragment 默认sql片段
     * @return 是否删除成功
     */
    default boolean executeDeleteRetBool(String sqlFragmentKey, Map paramMap,String defaultSqlFragment){
        return this.executeDelete(sqlFragmentKey,paramMap,defaultSqlFragment)>0;
    }

    /**
     * 执行更新语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @return 影响行数
     */
    default int executeUpdate(String sqlFragmentKey, Map paramMap) {
        return this.executeUpdate(sqlFragmentKey, paramMap,null);
    }

    /**
     * 执行更新语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @return 是否更新成功
     */
    default boolean executeUpdateRetBool(String sqlFragmentKey, Map paramMap){
        return this.executeUpdateRetBool(sqlFragmentKey, paramMap,null);
    }

    /**
     * 执行删除语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @return 影响行数
     */
    default int executeDelete(String sqlFragmentKey, Map paramMap){
        return this.executeDelete(sqlFragmentKey, paramMap,null);
    }

    /**
     * 执行删除语句
     * @param sqlFragmentKey 缓存key
     * @param paramMap 参数map集合
     * @return 是否删除成功
     */
    default boolean executeDeleteRetBool(String sqlFragmentKey, Map paramMap){
        return this.executeDeleteRetBool(sqlFragmentKey, paramMap,null);
    }

    /**
     * 执行返回整数型语句
     * @param sqlFragmentKey 缓存key
     * @param param 参数
     * @param defaultSqlFragment 默认sql片段
     * @return 整数型值
     */
    default int execute(String sqlFragmentKey, Object param,String defaultSqlFragment){
        return 0;
    }

    /**
     * 执行返回整数型语句
     * @param sqlFragmentKey 缓存key
     * @param param 参数
     * @return 整数型值
     */
    default int execute(String sqlFragmentKey, Object param){
        return this.execute(sqlFragmentKey, param,null);
    }

    /**
     * 执行返回布尔型语句
     * @param sqlFragmentKey 缓存key
     * @param param 参数
     * @param defaultSqlFragment 默认sql片段
     * @return 布尔型值
     */
    default boolean executeRetBool(String sqlFragmentKey, Object param,String defaultSqlFragment){
        return false;
    }

    /**
     * 执行返回布尔型语句
     * @param sqlFragmentKey 缓存key
     * @param param 参数
     * @return 布尔型值
     */
    default boolean executeRetBool(String sqlFragmentKey, Object param){
        return this.executeRetBool(sqlFragmentKey, param,null);
    }

    /**
     * 通过对象条件查询
     * @param sqlFragmentKey 缓存key
     * @param param 参数
     * @param defaultSqlFragment 默认sql片段
     * @return 结果集
     */
    default List<Map> executeSelectByBean(String sqlFragmentKey,Object param,String defaultSqlFragment){
        return null;
    }


    default boolean simpleExecuteRetBool(String sqlFragmentKey, Object param,String defaultSqlFragment){
        return false;
    }

}
