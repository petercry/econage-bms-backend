package com.econage.extend.modular.bms.basic.mapper;

import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface BmsDataJournalMapper  extends BaseMapper<BmsDataJournalEntity> {
    @SelectProvider(type = BmsDataJournalProvider.class,method = "sumWithDataJournalByFinalRelatedProjectIdSQL")
    Double sumWithDataJournalByFinalRelatedProjectId(@Param("final_related_project_id") String final_related_project_id , @Param("func_flag_str") String func_flag_str);

    @SelectProvider(type = BmsDataJournalProvider.class,method = "sumWithDataJournalByNoFinalRelatedProjectIdJustBaSQL")
    Double sumWithDataJournalByNoFinalRelatedProjectIdJustBa(@Param("ba_id") String ba_id , @Param("func_flag_str") String func_flag_str);

}
