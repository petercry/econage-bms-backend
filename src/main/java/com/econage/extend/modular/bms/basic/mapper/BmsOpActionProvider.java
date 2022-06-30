package com.econage.extend.modular.bms.basic.mapper;

import com.flowyun.cornerstone.db.mybatis.mapper.provider.MybatisProviderContext;
import org.apache.ibatis.annotations.Param;

public class BmsOpActionProvider {
    public static String extSearchOpActionSQL(MybatisProviderContext context, @Param("objType") String objType ,  @Param("objId") String objId ,  @Param("extObjType") String extObjType){
        return "select * from bms_op_action where (object_type_ = #{objType} and object_id_ = #{objId}) or (object_type_ = #{extObjType} and object_parent_id_ =  #{objId}) and valid_ = true order by date_ desc, order_seq_ desc";
    }
}
