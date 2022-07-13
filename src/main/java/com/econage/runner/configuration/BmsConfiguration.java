package com.econage.runner.configuration;

import com.econage.extend.modular.bms.BmsPackage;
import com.econage.extend.modular.bms.ba.component.bizOppo.trival.CheckBizOppoFromTmpLibJob;
import com.econage.extend.modular.bms.config.BmsProperties;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
        basePackageClasses={BmsPackage.class},
        annotationClass= Mapper.class,
        markerInterface= BaseMapper.class,
        sqlSessionTemplateRef= EconageBatisSpringBootConst.SQL_SESSION_TEMPLATE_BEAN_NAME
)
@ComponentScan(basePackageClasses={BmsPackage.class})
public class BmsConfiguration {

    @Bean
    public CheckBizOppoFromTmpLibJob getBizOppoFromTmpLibJob(BmsProperties bmsProperties){
        return new CheckBizOppoFromTmpLibJob();
    }

}
