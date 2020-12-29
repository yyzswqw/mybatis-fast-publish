package com.coco.fastpublish.service.impl;

import cn.hutool.core.util.IdUtil;
import com.coco.fastpublish.config.FastPublishProperty;
import com.coco.fastpublish.entity.SqlFragment;
import com.coco.fastpublish.mapper.SqlFragmentMapper;
import com.coco.fastpublish.service.SqlFragmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SqlFragmentServiceImpl implements SqlFragmentService {

    @Autowired
    private SqlFragmentMapper sqlFragmentMapper;

    @Autowired
    private FastPublishProperty fastPublishProperty;

    @Override
    public int create(SqlFragment fragment) {
        fragment.setId(IdUtil.createSnowflake(0, 1).nextId());
        return sqlFragmentMapper.insert(fragment);
    }

    @Override
    public boolean createRetBool(SqlFragment fragment) {
        fragment.setId(IdUtil.createSnowflake(0, 1).nextId());
        return sqlFragmentMapper.insert(fragment)>0;
    }

    @Override
    public int updateById(SqlFragment fragment) {
        return sqlFragmentMapper.updateById(fragment);
    }

    @Override
    public boolean updateByIdRetBool(SqlFragment fragment) {
        return sqlFragmentMapper.updateById(fragment)>0;
    }

    @Override
    public int updateByCode(SqlFragment fragment) {
        return sqlFragmentMapper.updateByCode(fragment);
    }

    @Override
    public boolean updateByCodeRetBool(SqlFragment fragment) {
        return sqlFragmentMapper.updateByCode(fragment)>0;
    }

    @Override
    public int delete(SqlFragment fragment) {
        return sqlFragmentMapper.delete(fragment);
    }

    @Override
    public boolean deleteRetBool(SqlFragment fragment) {
        return sqlFragmentMapper.delete(fragment)>0;
    }

    @Override
    public List<SqlFragment> list() {
        return sqlFragmentMapper.list();
    }

    @Override
    public List<SqlFragment> list(Integer pageNo, Integer pageSize) {
        return sqlFragmentMapper.listByPage((pageNo-1)*pageSize,pageSize,fastPublishProperty.getDriverType());
    }

    @Override
    public SqlFragment getById(Long id) {
        return sqlFragmentMapper.selectById(id);
    }

    @Override
    public SqlFragment getByCode(String code) {
        return sqlFragmentMapper.selectByCode(code);
    }


}
