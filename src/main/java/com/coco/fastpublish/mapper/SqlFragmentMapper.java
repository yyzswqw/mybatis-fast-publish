package com.coco.fastpublish.mapper;

import com.coco.fastpublish.entity.SqlFragment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SqlFragmentMapper {

    int insert(@Param("fragment") SqlFragment sqlFragment);

    int updateById(@Param("fragment") SqlFragment sqlFragment);

    int updateByCode(@Param("fragment") SqlFragment sqlFragment);

    int delete(@Param("fragment") SqlFragment sqlFragment);

    List<SqlFragment> listByPage(@Param("start")Integer start,@Param("pageSize")Integer pageSize,@Param("driverType")String driverType);

    List<SqlFragment> list();

    @Select("select id,name,code,sql_desc as sqlDesc,fragment,param_constraint as paramConstraint from sql_fragment where code = #{code} ")
    SqlFragment selectByCode(String code);

    @Select("select id,name,code,sql_desc as sqlDesc,fragment,param_constraint as paramConstraint from sql_fragment where id = #{id} ")
    SqlFragment selectById(Long id);

}
