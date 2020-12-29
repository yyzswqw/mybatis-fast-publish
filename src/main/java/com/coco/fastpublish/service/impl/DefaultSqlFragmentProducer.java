package com.coco.fastpublish.service.impl;

import com.coco.fastpublish.service.SqlFragmentProducer;

public class DefaultSqlFragmentProducer implements SqlFragmentProducer {

    @Override
    public String getSqlFragment(String sqlFragmentKey,String defaultSqlFragment) {
        return defaultSqlFragment;
    }
}
