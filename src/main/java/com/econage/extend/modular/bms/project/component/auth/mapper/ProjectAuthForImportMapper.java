package com.econage.extend.modular.bms.project.component.auth.mapper;

import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthForImportEntity;
import com.econage.extend.modular.bms.project.component.auth.trival.tempBean.OrgByMiBean;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Set;

public interface ProjectAuthForImportMapper extends BaseMapper<ProjectAuthForImportEntity> {
    @SelectProvider(type = ProjectAuthForImportMapperProvider.class)
    Set<OrgByMiBean> selectOrgByMi();

    @SelectProvider(type = ProjectAuthForImportMapperProvider.class,method = "countExistAuthSQL")
    Integer countExistAuth(@Param("projectId") String projectId , @Param("roleKey") String roleKey , @Param("roleUser") String roleUser);
}
