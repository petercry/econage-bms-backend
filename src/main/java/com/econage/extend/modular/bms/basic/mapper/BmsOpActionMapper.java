package com.econage.extend.modular.bms.basic.mapper;

import com.econage.extend.modular.bms.basic.entity.BmsOpActionEntity;
import com.flowyun.cornerstone.db.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Collection;

public interface BmsOpActionMapper extends BaseMapper<BmsOpActionEntity> {
    @SelectProvider(type = BmsOpActionProvider.class,method = "extSearchOpActionSQL")
    Collection<BmsOpActionEntity> extSearchOpAction( @Param("objType") String objType ,  @Param("objId") String objId ,  @Param("extObjType") String extObjType);
}
