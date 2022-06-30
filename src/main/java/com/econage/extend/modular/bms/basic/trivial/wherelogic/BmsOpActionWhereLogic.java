package com.econage.extend.modular.bms.basic.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

@WhereLogic
public class BmsOpActionWhereLogic extends BasicWhereLogic {
    @WhereLogicField(column = "object_type_")
    private String objType;
    @WhereLogicField(column = "object_id_")
    private String objId;
}
