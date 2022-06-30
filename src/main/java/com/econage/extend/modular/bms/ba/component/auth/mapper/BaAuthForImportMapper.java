package com.econage.extend.modular.bms.ba.component.auth.mapper;

import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthForImportEntity;
import com.econage.extend.modular.bms.ba.component.auth.trival.tempBean.OrgByMiBean;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Set;

public interface BaAuthForImportMapper extends BaseMapper<BaAuthForImportEntity> {
    @SelectProvider(type = BaAuthForImportMapperProvider.class)
    Set<OrgByMiBean> selectOrgByMi();
}
