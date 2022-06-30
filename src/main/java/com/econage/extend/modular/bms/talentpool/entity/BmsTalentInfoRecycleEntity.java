package com.econage.extend.modular.bms.talentpool.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Chris Bosh
 * @date: 2021/2/19 14:25
 * @description:
 */
@TableDef("bms_talent_info_recycle_")
@Data
@EqualsAndHashCode(callSuper = true)
public class BmsTalentInfoRecycleEntity extends BmsTalentInfoEntity {

}
