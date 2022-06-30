package com.econage.runner.configuration;

import com.econage.extend.modular.projectcrm88.Crm88Enabled;
import com.econage.extend.modular.projectcrm88.Crm88Package;
import com.econage.extend.modular.projectcrm88.trivial.meta.Crm88Const;
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

import static com.econage.extend.modular.projectcrm88.trivial.meta.Crm88Const.CRM_88_DATA_SOURCE_KEY;
@Configuration
@Crm88Enabled
@MapperScan(
        basePackageClasses={Crm88Package.class},
        annotationClass= Mapper.class,
        markerInterface= BaseMapper.class,
        sqlSessionTemplateRef= Crm88Const.CRM88_SQLSESSION_TEMPLATE
)
@ComponentScan(basePackageClasses={Crm88Package.class})
public class Crm88Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Crm88Configuration.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public SqlSessionTemplate crm88SqlSessionTemplate(
            EconageBatisSetting econageBatisSetting,
            DataSourceHolder dataSourceHolder,
            SqlMonitor statementMonitor
    ){
        logger.info("Setting up 88-crm SqlSessionTemplate.");
        return EconageBatisInitiator.create()
                .parse(
                        econageBatisSetting.selectExternalSetting(CRM_88_DATA_SOURCE_KEY),
                        dataSourceHolder.selectExternalDataSource(CRM_88_DATA_SOURCE_KEY),
                        statementMonitor
                ).build();
    }


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public DataSourceTransactionManager crm88TransactionManager(DataSourceHolder dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource.selectExternalDataSource(CRM_88_DATA_SOURCE_KEY));
        dataSourceTransactionManager.setEnforceReadOnly(true);
        return dataSourceTransactionManager;
    }
}
