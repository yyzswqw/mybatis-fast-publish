package com.coco.fastpublish.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.coco.fastpublish.mapper.BaseSqlExecuterMapper;
import com.coco.fastpublish.service.SqlExecuter;
import com.coco.fastpublish.service.SqlFragmentProducer;
import com.coco.fastpublish.sqlprovider.BaseSqlExecuterProvider;
import com.coco.fastpublish.util.CheckParamUtil;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.ibatis.mapping.BoundSql;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSqlExecuterImpl implements SqlExecuter {

    private static Map<String, SoftReference<String>> sqlFragmentMap = new ConcurrentHashMap<>();
    private static Map<String, SoftReference<Map>> paramConstraintMap = new ConcurrentHashMap<>();
    private static Map<String, SoftReference<BoundSql>> boundSqlMap = new ConcurrentHashMap<>();

    @Autowired
    private BaseSqlExecuterMapper sqlExecuterMapper;

    @Autowired
    private SqlFragmentProducer sqlFragmentProducer;

    @Override
    public String getSqlFragment(String sqlFragmentKey, String defaultSqlFragment, Object param) {
        if (!(param instanceof Map)) {
            param = CheckParamUtil.objectToMap(param);
        }
        final SoftReference<String> sqlFragment = sqlFragmentMap.get(sqlFragmentKey);
        if (null == sqlFragment) {
            Map paramConstraint = new HashMap();
            String sql = sqlFragmentProducer.getSqlFragment(sqlFragmentKey, defaultSqlFragment, param, paramConstraint);
            if (null == sql) {
                Map constraint = (Map) paramConstraint.get("constraint");
                if (constraint != null && !constraint.isEmpty()) {
                    paramConstraintMap.put(sqlFragmentKey, new SoftReference<>(constraint));
                }
                return defaultSqlFragment;
            }
            sqlFragmentMap.put(sqlFragmentKey, new SoftReference<>(sql));
            return sqlFragmentMap.get(sqlFragmentKey).get();
        }
        PatriciaTrie constraintTrie = new PatriciaTrie();
        Map map = paramConstraintMap.get(sqlFragmentKey) == null ? null : paramConstraintMap.get(sqlFragmentKey).get();
        if (map == null || map.isEmpty()) {
            String paramConstraint = sqlFragmentProducer.getParamConstraint(sqlFragmentKey);
            if (paramConstraint != null) {
                map = JSON.parseObject(paramConstraint, Map.class);
            } else {
                map = new HashMap();
            }
        }
        constraintTrie.putAll(map);
        PatriciaTrie paramTrie = new PatriciaTrie();
        paramTrie.putAll((Map) param);
        StringBuilder stringBuilder = CheckParamUtil.checkParam(constraintTrie, paramTrie);
        if (stringBuilder.length() > 0) {
            throw new RuntimeException(stringBuilder.toString());
        }
        return sqlFragment.get();
    }

    @Override
    public List<Map> executeSelect(String sqlFragmentKey, Map paramMap, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, paramMap);
        setBoundSql(sqlFragmentKey,paramMap,sql);
        return sqlExecuterMapper.executeSelect(paramMap,sql);
    }

    @Override
    public List<Map> executeSelect(Map paramMap, String sqlFragment) {
        return sqlExecuterMapper.executeSelect(paramMap,sqlFragment);
    }

    @Override
    public boolean deleteSqlFragment(String sqlFragmentKey){
        sqlFragmentMap.remove(sqlFragmentKey);
        boundSqlMap.remove(sqlFragmentKey);
        return true;
    }

    @Override
    public boolean updateSqlFragment(String sqlFragmentKey){
        sqlFragmentMap.remove(sqlFragmentKey);
        boundSqlMap.remove(sqlFragmentKey);
        return true;
    }

    @Override
    public int executeUpdate(String sqlFragmentKey, Map paramMap, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, paramMap);
        setBoundSql(sqlFragmentKey,paramMap,sql);
        return sqlExecuterMapper.executeUpdate(paramMap,sql);
    }

    @Override
    public boolean executeUpdateRetBool(String sqlFragmentKey, Map paramMap, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, paramMap);
        setBoundSql(sqlFragmentKey,paramMap,sql);
        return sqlExecuterMapper.executeUpdateRetBool(paramMap,sql);
    }

    @Override
    public int executeDelete(String sqlFragmentKey, Map paramMap, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, paramMap);
        setBoundSql(sqlFragmentKey,paramMap,sql);
        return sqlExecuterMapper.executeDelete(paramMap,sql);
    }

    @Override
    public boolean executeDeleteRetBool(String sqlFragmentKey, Map paramMap, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, paramMap);
        setBoundSql(sqlFragmentKey,paramMap,sql);
        return sqlExecuterMapper.executeDeleteRetBool(paramMap,sql);
    }


    @Override
    public boolean executeRetBool(String sqlFragmentKey, Object param, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, param);
        setBoundSql(sqlFragmentKey,param,sql);
        return sqlExecuterMapper.executeRetBool(param,sql);
    }

    @Override
    public List<Map> executeSelectByBean(String sqlFragmentKey,Object param, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, param);
        setBoundSql(sqlFragmentKey,param,sql);
        return sqlExecuterMapper.executeSelectByBean(param, sql);
    }

    @Override
    public boolean simpleExecuteRetBool(String sqlFragmentKey, Object param, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, param);
        setBoundSql(sqlFragmentKey,param,sql);
        return sqlExecuterMapper.simpleExecuteRetBool(param,sql);
    }


    @Override
    public int execute(String sqlFragmentKey, Object param, String defaultSqlFragment) {
        final String sql = this.getSqlFragment(sqlFragmentKey, defaultSqlFragment, param);
        setBoundSql(sqlFragmentKey,param,sql);
        return sqlExecuterMapper.execute(param,sql);
    }

    private void setBoundSql(String sqlFragmentKey, Object param, String sqlFragment) {
        BoundSql boundSql = boundSqlMap.get(sqlFragmentKey)==null?null:boundSqlMap.get(sqlFragmentKey).get();
        if(null == boundSql){
            boundSql = BaseSqlExecuterProvider.executeParseBoundSql(param,sqlFragment);
            boundSqlMap.put(sqlFragmentKey,new SoftReference<>(boundSql));
        }
        BaseSqlExecuterProvider.setBoundSql(boundSql);
    }
}
