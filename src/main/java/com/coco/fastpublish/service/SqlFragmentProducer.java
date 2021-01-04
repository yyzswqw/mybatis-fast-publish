package com.coco.fastpublish.service;

import java.util.Map;

public interface SqlFragmentProducer {

    /**
     * 获取sql片段
     *
     * @param sqlFragmentKey     缓存key
     * @param defaultSqlFragment 默认sql
     * @param param              参数
     * @param paramConstraint    参数约束，自定义时需要回传,key为constraint，值为Map类型
     * @return sql片段
     */
    default String getSqlFragment(String sqlFragmentKey, String defaultSqlFragment, Object param, Map paramConstraint) {
        return defaultSqlFragment;
    }

    /**
     * 获取sql片段
     * @param sqlFragmentKey 缓存key
     * @return sql片段
     */
    default String getSqlFragment(String sqlFragmentKey) {
        return this.getSqlFragment(sqlFragmentKey,null,null,null);
    }
}
