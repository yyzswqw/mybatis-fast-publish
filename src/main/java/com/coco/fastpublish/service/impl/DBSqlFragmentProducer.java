package com.coco.fastpublish.service.impl;


import com.alibaba.fastjson.JSON;
import com.coco.fastpublish.entity.SqlFragment;
import com.coco.fastpublish.service.SqlFragmentProducer;
import com.coco.fastpublish.service.SqlFragmentService;
import com.coco.fastpublish.util.CheckParamUtil;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class DBSqlFragmentProducer implements SqlFragmentProducer {

    @Autowired
    private SqlFragmentService fragmentService;

    @Override
    public String getSqlFragment(String sqlFragmentKey, String defaultSqlFragment, Object param, Map paramConstraint) {
        Map paramMap = (Map) param;
        SqlFragment sqlFragment = fragmentService.getByCode(sqlFragmentKey);
        if (null == sqlFragment || sqlFragment.getFragment() == null) {
            return defaultSqlFragment;
        }
        if (sqlFragment.getParamConstraint() != null) {
            PatriciaTrie constraintTrie = new PatriciaTrie();
            Map constraintMap = JSON.parseObject(sqlFragment.getParamConstraint(), Map.class);
            constraintTrie.putAll(constraintMap);
            PatriciaTrie paramTrie = new PatriciaTrie();
            paramTrie.putAll(paramMap);
            paramConstraint.put("constraint", constraintMap);
            StringBuilder stringBuilder = CheckParamUtil.checkParam(constraintTrie, paramTrie);
            if (stringBuilder.length() > 0) {
                throw new RuntimeException(stringBuilder.toString());
            }
        }
        return sqlFragment.getFragment();
    }

    @Override
    public String getParamConstraint(String sqlFragmentKey) {
        SqlFragment sqlFragment = fragmentService.getByCode(sqlFragmentKey);
        if (sqlFragment == null || sqlFragment.getParamConstraint() == null) {
            return null;
        }
        return sqlFragment.getParamConstraint();
    }
}
