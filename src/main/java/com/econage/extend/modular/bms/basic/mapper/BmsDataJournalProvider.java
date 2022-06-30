package com.econage.extend.modular.bms.basic.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;

public class BmsDataJournalProvider {
    public static String sumWithDataJournalByFinalRelatedProjectIdSQL(MybatisProviderContext context, @Param("final_related_project_id") String final_related_project_id , @Param("func_flag_str") String func_flag_str){
        return "select nvl(sum(data_number_),0) from bms_data_journal where final_related_project_id_ = #{final_related_project_id} and func_flag_ in (" + func_flag_str +") and valid_ = true";
    }
    public static String sumWithDataJournalByNoFinalRelatedProjectIdJustBaSQL(MybatisProviderContext context, @Param("ba_id") String ba_id , @Param("func_flag_str") String func_flag_str){
        return "select nvl(sum(data_number_),0) from bms_data_journal where modular_ = 'ba' and modular_inner_id_ = #{ba_id} and final_related_project_id_ is null and func_flag_ in (" + func_flag_str +") and valid_ = true";
    }
}
