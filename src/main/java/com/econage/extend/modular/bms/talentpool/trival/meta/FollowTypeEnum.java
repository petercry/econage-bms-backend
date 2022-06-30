package com.econage.extend.modular.bms.talentpool.trival.meta;

import com.econage.core.basic.i18n.I18nEnum;
import com.flowyun.cornerstone.db.mybatis.enums.IEnum;

import java.io.Serializable;


/**
 * @author econage
 * 跟进类型
 */

public enum FollowTypeEnum implements IEnum, I18nEnum {
    /**
     * 沟通跟进
     */
    CONVENTIONAL(1, "bms.talent.conventional"),
    /**
     * 预约面试
     */
    INTERVIEW(2, "bms.talent.interview"),
    /**
     * 面试结果记录
     */
    RESULT(3, "bms.talent.result");

    private final Integer value;
    private final String i18nKey;

    FollowTypeEnum(Integer value, String i18nKey) {
        this.value = value;
        this.i18nKey = i18nKey;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @Override
    public String getI18nKey() {
        return i18nKey;
    }
}
