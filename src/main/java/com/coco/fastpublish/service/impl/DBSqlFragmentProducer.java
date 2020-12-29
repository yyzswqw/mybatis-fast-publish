package com.coco.fastpublish.service.impl;


import com.coco.fastpublish.entity.SqlFragment;
import com.coco.fastpublish.service.SqlFragmentProducer;
import com.coco.fastpublish.service.SqlFragmentService;
import org.springframework.beans.factory.annotation.Autowired;

public class DBSqlFragmentProducer implements SqlFragmentProducer {

    @Autowired
    private SqlFragmentService fragmentService;

    @Override
    public String getSqlFragment(String sqlFragmentKey, String defaultSqlFragment) {
        final SqlFragment sqlFragment = fragmentService.getByCode(sqlFragmentKey);
        if(null == sqlFragment){
            return defaultSqlFragment;
        }
        return sqlFragment.getFragment() == null?defaultSqlFragment:sqlFragment.getFragment();
    }
}
