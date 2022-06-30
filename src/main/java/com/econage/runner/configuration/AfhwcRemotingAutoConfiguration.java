package com.econage.runner.configuration;


import com.econage.extend.modular.bms.afhwc.AfhwcPackageInfo;
import com.econage.extend.modular.bms.afhwc.config.AfhwcRemotingEnabled;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import com.flowyun.cornerstone.db.mybatis.spring.boot.EconageBatisSpringBootConst;
import com.flowyun.cornerstone.web.client.rest.annotations.RestProxyScan;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AfhwcRemotingEnabled
@MapperScan(
        basePackageClasses={AfhwcPackageInfo.class},
        annotationClass= Mapper.class,
        markerInterface= BaseMapper.class,
        sqlSessionTemplateRef= EconageBatisSpringBootConst.SQL_SESSION_TEMPLATE_BEAN_NAME
)
@ComponentScan(basePackageClasses={AfhwcPackageInfo.class})
@RestProxyScan(
        basePackageClasses={AfhwcPackageInfo.class}
)
public class AfhwcRemotingAutoConfiguration {

}
