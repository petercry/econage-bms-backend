package com.econage.extend.modular.bms.project.component.auth.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

public class ProjectAuthForImportMapperProvider implements ProviderMethodResolver {
    public static String selectOrgByMi(){
        StringBuilder strBuf = new StringBuilder()
                .append("select u.id_ , u.mi_ ,\n" +
                        "       (select max(o.ID_) from ecl_organization_ o where o.RESOURCE_ID_ = u.ID_) as org_\n" +
                        "       from ecl_user_ u where STATUS_=1");
        return strBuf.toString();
    }
    public static String countExistAuthSQL(MybatisProviderContext context, @Param("projectId") String projectId, @Param("roleKey") String roleKey, @Param("roleUser") String roleUser){
        return "select count(1) from  bms_project_auth where project_id_ = #{projectId} and valid_ = 1 and key_= #{roleKey} and org_id_ = #{roleUser}";
    }
}
