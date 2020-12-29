package com.coco.fastpublish.service;


import com.coco.fastpublish.entity.SqlFragment;

import java.util.List;

public interface SqlFragmentService {

    int create(SqlFragment fragment);

    default boolean createRetBool(SqlFragment fragment){
        return create(fragment)>0;
    }

    int updateById(SqlFragment fragment);

    default boolean updateByIdRetBool(SqlFragment fragment){
        return updateById(fragment)>0;
    }

    int updateByCode(SqlFragment fragment);

    default boolean updateByCodeRetBool(SqlFragment fragment){
        return updateByCode(fragment)>0;
    }

    int delete(SqlFragment fragment);

    default boolean deleteRetBool(SqlFragment fragment){
        return delete(fragment)>0;
    }

    List<SqlFragment> list();

    List<SqlFragment> list(Integer pageNo,Integer pageSize);

    SqlFragment getById(Long id);

    SqlFragment getByCode(String code);
}
