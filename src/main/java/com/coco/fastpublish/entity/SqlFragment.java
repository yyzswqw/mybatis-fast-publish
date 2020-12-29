package com.coco.fastpublish.entity;

import java.io.Serializable;

/**
 * @author wqw
 */
public class SqlFragment implements Serializable {

    private static final long serialVersionUID = 3087220544374354848L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一key
     */
    private String code;

    /**
     * 描述
     */
    private String sqlDesc;

    /**
     * 动态sql片段
     */
    private String fragment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSqlDesc() {
        return sqlDesc;
    }

    public void setSqlDesc(String sqlDesc) {
        this.sqlDesc = sqlDesc;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "SqlFragment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sqlDesc='" + sqlDesc + '\'' +
                ", fragment='" + fragment + '\'' +
                '}';
    }
}
