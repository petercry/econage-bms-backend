package com.econage.extend.modular.bms.project.mapper;

import com.econage.extend.modular.bms.project.entity.BmsProjectForImportEntity;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface BmsProjectForImportMapper extends BaseMapper<BmsProjectForImportEntity> {
    @SelectProvider(type = BmsProjectForImportProvider.class,method = "getProjectIdInWh88By88crmSQL")
    String getProjectIdInWh88By88crm(@Param("proId88crm") String proId88crm);
}
