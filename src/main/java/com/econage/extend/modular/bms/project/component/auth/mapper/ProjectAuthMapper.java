package com.econage.extend.modular.bms.project.component.auth.mapper;

import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthEntity;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface ProjectAuthMapper extends BaseMapper<ProjectAuthEntity> {
    @SelectProvider(type = ProjectAuthProvider.class,method = "countExistAuthSQL")
    Integer countExistAuth(@Param("projectId") String projectId , @Param("roleKey") String roleKey , @Param("roleUser") String roleUser);
}
