package com.coco.fastpublish.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class BaseSqlExecuterProvider {

    private static ThreadLocal<BoundSql> boundSqlCache = new ThreadLocal<>();

    private static final String SELECT_PREFIX = "<select id='getSelectSql' > ";
    private static final String SELECT_SUFFIX = " </select>";

    public static String executeParse(@Param("param")Object param,@Param("sqlFragment")String sqlFragment){
        Configuration c = new Configuration();
        BoundSql boundSql = boundSqlCache.get();
        if(boundSql == null){
            boundSql = executeParseBoundSql(param,sqlFragment);
        }
        boundSqlCache.remove();
        return boundSql.getSql();
    }

    public static String execute(@Param("param")Object param,@Param("sqlFragment")String sqlFragment){
        Configuration c = new Configuration();
        BoundSql boundSql = boundSqlCache.get();
        if(boundSql == null){
            boundSql = executeParseBoundSql(param,sqlFragment);
        }
        String sql = boundSql.getSql();
        final List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        boundSqlCache.remove();
        return BaseSqlExecuterProvider.setParameter(boundSql,parameterMappings,c,sql,param);
    }

    public static String executeSelect(@Param("map")Map paramMap, @Param("sqlFragment")String sqlFragment){
        Configuration c = new Configuration();
        BoundSql boundSql = boundSqlCache.get();
        if(boundSql == null){
            boundSql = executeParseBoundSql(paramMap,sqlFragment);
        }
        String sql = boundSql.getSql();
        final List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        boundSqlCache.remove();
        return BaseSqlExecuterProvider.setParameter(boundSql,parameterMappings,c,sql,paramMap);
    }

    public static BoundSql executeParseBoundSql(@Param("param")Object param,@Param("sqlFragment")String sqlFragment){
        Configuration c = new Configuration();
        return BaseSqlExecuterProvider.parse(param, sqlFragment,c,SELECT_PREFIX,SELECT_SUFFIX);
    }

    private static BoundSql parse(Object param,String sqlFragment,Configuration c,String prefix,String suffix){
        if(null == c){
            c = new Configuration();
        }
        String s = prefix + sqlFragment + suffix;

        ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(s.getBytes());
        XPathParser parser = new XPathParser(tInputStringStream, false, null, null);
        final List<XNode> xNodes = parser.evalNodes("/select");

        final XMLScriptBuilder xmlsb = new XMLScriptBuilder(c, xNodes.get(0));
        final SqlSource sqlSource = xmlsb.parseScriptNode();
        return sqlSource.getBoundSql(param);
    }

    private static String setParameter(BoundSql boundSql,List<ParameterMapping> parameterMappings,Configuration c,String sql,Object param){
        if(parameterMappings != null && !parameterMappings.isEmpty()) {
            final TypeHandlerRegistry typeHandlerRegistry = c.getTypeHandlerRegistry();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping pm = parameterMappings.get(i);
                if (pm.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = pm.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (param == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(param.getClass())) {
                        value = param;
                    } else {
                        MetaObject metaObject = c.newMetaObject(param);
                        propertyName = propertyName.substring(propertyName.indexOf(".")+1);
                        value = metaObject.getValue(propertyName);// 取值
                    }
                    String val = getParameterValue(value) == null?"null":Matcher.quoteReplacement(getParameterValue(value));
                    sql = sql.replaceFirst("\\?",val);
                }
            }
        }
        return sql;
    }

    /**
     * 如果参数是String，则添加单引号
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            if(obj == null){
                value = null;
            }else{
                value = "'" + obj.toString() + "'";
            }
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = null;
            }

        }
        return value;
    }

    public static void setBoundSql(BoundSql boundSql){
        boundSqlCache.set(boundSql);
    }
    public static void removeBoundSql(){
        boundSqlCache.remove();
    }


}
