package com.coco.fastpublish.service;

public interface SqlFragmentProducer {

    /**
     * 获取sql片段
     * @param sqlFragmentKey 缓存key
     * @param defaultSqlFragment 默认sql
     * @return sql片段
     */
    default String getSqlFragment(String sqlFragmentKey,String defaultSqlFragment){
        return defaultSqlFragment;
    }

    /**
     * 获取sql片段
     * @param sqlFragmentKey 缓存key
     * @return sql片段
     */
    default String getSqlFragment(String sqlFragmentKey){
        return this.getSqlFragment(sqlFragmentKey,null);
    }
}
