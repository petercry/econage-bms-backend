package com.econage.extend.modular.bms.talentpool.entity;

import com.econage.extend.modular.bms.talentpool.trival.meta.FollowTypeEnum;
import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: Chris Bosh
 * @date: 2021/2/19 14:25
 * @description:
 */
@TableDef("bms_talent_follow_")
@Data
@EqualsAndHashCode(callSuper = true)
public class BmsTalentFollowEntity extends BaseEntity {
    private String id;
    @TableField(isFk = true,defaultUpdate = false)
    private String infoId;

    /**
     * 跟进类型
     */
    private FollowTypeEnum type;
    /**
     * 跟进时间
     */
    private LocalDateTime date;
    /**
     * 跟进负责人
     */
    private String followPrincipal;
    /**
     * 跟进方式
     */
    private String followMethod;
    /**
     * 面试日期
     */
    private LocalDateTime roundDate;
    /**
     * 面试人
     */
    private String roundInterviewer;

    /**
     * 面试方式
     */
    private String roundMethod;

    /**
     * 是否入职
     */
    @TableField("is_join_")
    private Boolean join;
    /**
     * 入职时间
     */
    private LocalDate joinDate;
    /**
     * 人才跟进情况
     */
    private String detail;

    @TableField(exist = false)
    public String roundInterviewerName;
    @TableField(exist = false)
    private String followPrincipalName;
    @TableField(exist = false)
    private Boolean updateEnable;

    /**
     * 跟进结果
     */
    @Deprecated
    @TableField(exist = false)
    private String followResult;


    @TableField(exist = false)
    private String hrStatus;

    @TableField(exist = false)
    private String bpStatus;

    /**
     * 下次跟进时间
     */
    @TableField(exist = false)
    private LocalDateTime followNextDate;

    //private List<String> followResultList;


}
