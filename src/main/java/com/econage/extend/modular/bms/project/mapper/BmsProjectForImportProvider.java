package com.econage.extend.modular.bms.project.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;

public class BmsProjectForImportProvider {
    public static String getProjectIdInWh88By88crmSQL(MybatisProviderContext context, @Param("proId88crm") String proId88crm){
        return "select pro_id_wh88_ from bms_88project_sync where pro_id_88crm_ = #{proId88crm}";
    }
}
