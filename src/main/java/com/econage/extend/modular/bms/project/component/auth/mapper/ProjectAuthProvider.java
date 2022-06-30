package com.econage.extend.modular.bms.project.component.auth.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;

public class ProjectAuthProvider {
    public static String countExistAuthSQL(MybatisProviderContext context, @Param("projectId") String projectId, @Param("roleKey") String roleKey, @Param("roleUser") String roleUser){
        return "select count(1) from  bms_project_auth where project_id_ = #{projectId} and valid_ = 1 and key_= #{roleKey} and org_id_ = #{roleUser}";
    }
}
