package com.econage.runner.configuration;

import com.econage.core.basic.time.DateTimeConst;
import com.econage.extend.modular.bms.BmsPackage;
import com.econage.extend.modular.bms.ba.component.bizOppo.trival.CheckBizOppoFromTmpLibJob;
import com.econage.extend.modular.bms.config.BmsProperties;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.JobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.util.TimeZone;

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
    public CronTriggerFactoryBean getBizOppoFromTmpLibJob(BmsProperties bmsProperties){
        CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
        cronTriggerFactory.setJobDetail(JobBuilder.newJob(CheckBizOppoFromTmpLibJob.class).withIdentity(CheckBizOppoFromTmpLibJob.class.getName()).requestRecovery(true).build());
        cronTriggerFactory.setCronExpression(bmsProperties.getCheckBizOppoFromTmpLibJobCron());
        cronTriggerFactory.setTimeZone(TimeZone.getTimeZone(DateTimeConst.getDefaultZone()));
        return cronTriggerFactory;
    }

}
