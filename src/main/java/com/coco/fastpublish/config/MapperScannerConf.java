package com.coco.fastpublish.config;

import com.coco.fastpublish.controller.BaseFastPublicController;
import com.coco.fastpublish.controller.FastPublicController;
import com.coco.fastpublish.service.SqlExecuter;
import com.coco.fastpublish.service.SqlFragmentProducer;
import com.coco.fastpublish.service.SqlFragmentService;
import com.coco.fastpublish.service.impl.DefaultSqlExecuterImpl;
import com.coco.fastpublish.service.impl.DefaultSqlFragmentProducer;
import com.coco.fastpublish.service.impl.SqlFragmentServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties({MybatisProperties.class,FastPublishProperty.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class})
public class MapperScannerConf extends MapperScannerConfigurer {

    public MapperScannerConf() {
        super.setBasePackage("com.coco.fastpublish.mapper");
    }

    @Bean
    @ConditionalOnMissingBean({SqlExecuter.class})
    public SqlExecuter baseSqlExecuter(){
        return new DefaultSqlExecuterImpl();
    }

    @Bean
    @ConditionalOnMissingBean({SqlFragmentProducer.class})
    public SqlFragmentProducer sqlFragmentProducer(){
        return new DefaultSqlFragmentProducer();
    }

    @Bean
    @ConditionalOnBean({SqlExecuter.class})
    @ConditionalOnMissingBean({FastPublicController.class})
    public FastPublicController fastPublicController(){
        return new FastPublicController();
    }

    @Bean
    @ConditionalOnBean({SqlExecuter.class})
    @ConditionalOnMissingBean({SqlFragmentService.class})
    public SqlFragmentService sqlFragmentService(){
        return new SqlFragmentServiceImpl();
    }

    @Bean
    @ConditionalOnBean({SqlExecuter.class})
    @ConditionalOnMissingBean({BaseFastPublicController.class})
    public BaseFastPublicController baseFastPublicController(){
        return new BaseFastPublicController();
    }

}
