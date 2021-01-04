package com.coco.fastpublish.service.impl;

import com.coco.fastpublish.service.SqlFragmentProducer;

import java.util.Map;

public class DefaultSqlFragmentProducer implements SqlFragmentProducer {

    @Override
    public String getSqlFragment(String sqlFragmentKey, String defaultSqlFragment, Object param, Map constraintMap) {
        return defaultSqlFragment;
    }
}
