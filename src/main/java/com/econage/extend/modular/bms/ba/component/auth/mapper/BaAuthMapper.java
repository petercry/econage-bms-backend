package com.econage.extend.modular.bms.ba.component.auth.mapper;

import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface BaAuthMapper extends BaseMapper<BaAuthEntity> {
    @SelectProvider(type = BaAuthProvider.class,method = "countExistAuthSQL")
    Integer countExistAuth(@Param("baId") String baId , @Param("roleKey") String roleKey , @Param("roleUser") String roleUser);
}
