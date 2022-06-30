package com.econage.runner.configuration;


import com.econage.extend.modular.projectwh88.Wh88EWhEnabled;
import com.econage.extend.modular.projectwh88.Wh88Package;
import com.econage.extend.modular.projectwh88.trivial.meta.Wh88Const;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import com.flowyun.cornerstone.db.mybatis.monitor.SqlMonitor;
import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.EconageBatisInitiator;
import com.flowyun.cornerstone.db.mybatis.spring.boot.autoconfigure.EconageBatisSetting;
import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.DataSourceHolder;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import static com.econage.extend.modular.projectwh88.trivial.meta.Wh88Const.WH_88_DATA_SOURCE_KEY;

@Configuration
@Wh88EWhEnabled
@MapperScan(
        basePackageClasses={Wh88Package.class},
        annotationClass= Mapper.class,
        markerInterface= BaseMapper.class,
        sqlSessionTemplateRef= Wh88Const.WH88_SQLSESSION_TEMPLATE
)
@ComponentScan(basePackageClasses={Wh88Package.class})
public class ProjectWh88Configuration {
    private static final Logger logger = LoggerFactory.getLogger(ProjectWh88Configuration.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public SqlSessionTemplate wh88SqlSessionTemplate(
            EconageBatisSetting econageBatisSetting,
            DataSourceHolder dataSourceHolder,
            SqlMonitor statementMonitor
    ){
        logger.info("Setting up wh-88 SqlSessionTemplate.");
        return EconageBatisInitiator.create()
                .parse(
                        econageBatisSetting.selectExternalSetting(WH_88_DATA_SOURCE_KEY),
                        dataSourceHolder.selectExternalDataSource(WH_88_DATA_SOURCE_KEY),
                        statementMonitor
                ).build();
    }


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public DataSourceTransactionManager wh88TransactionManager(DataSourceHolder dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource.selectExternalDataSource(WH_88_DATA_SOURCE_KEY));
        dataSourceTransactionManager.setEnforceReadOnly(true);
        return dataSourceTransactionManager;
    }

}
