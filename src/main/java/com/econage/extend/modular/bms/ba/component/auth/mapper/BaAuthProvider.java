package com.econage.extend.modular.bms.ba.component.auth.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;

public class BaAuthProvider {
    public static String countExistAuthSQL(MybatisProviderContext context, @Param("baId") String baId, @Param("roleKey") String roleKey, @Param("roleUser") String roleUser){
        return "select count(1) from  bms_ba_auth where ba_id_ = #{baId} and valid_ = 1 and key_= #{roleKey} and org_id_ = #{roleUser}";
    }
}
