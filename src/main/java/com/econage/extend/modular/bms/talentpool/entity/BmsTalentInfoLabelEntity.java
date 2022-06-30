package com.econage.extend.modular.bms.talentpool.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableDef("bms_talent_info_label_")
@Data
@EqualsAndHashCode(callSuper = true)
public class BmsTalentInfoLabelEntity extends BaseEntity {
    private String id;
    @TableField(isFk = true)
    private String infoId;
    private String label;
}
