package com.econage.extend.modular.bms.talentpool.trival.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.econage.extend.modular.bms.talentpool.trival.wherelogic.parser.TalentPoolLabelParser;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;
import com.flowyun.cornerstone.db.mybatis.enums.WhereLogicOperator;
import com.flowyun.cornerstone.db.mybatis.util.MybatisConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author: Chris Bosh
 * @date: 2021/2/19 15:08
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@WhereLogic
public class TalentPoolInfoWhereLogic extends BasicWhereLogic {

    /**
     * 简历来源
     */
    private String resumeSource;
    /**
     * 收到简历日期
     */
    @WhereLogicField(wherePart = " resume_Date_ >= #{resumeDateFrom} ")
    private LocalDate resumeDateFrom;

    /**
     * 收到简历日期
     */
    @WhereLogicField(wherePart = " resume_Date_ < DATE_ADD(#{resumeDateTo},INTERVAL 1 DAY) ")
    @WhereLogicField(wherePart = " resume_Date_ < ( #{resumeDateTo} + interval '1 D' ) ",databaseId = MybatisConst.POSTGRE_DB)
    private LocalDate resumeDateTo;

    /**
     * 简历姓名
     */
    @WhereLogicField(operator = WhereLogicOperator.LIKE)
    private String name;
    @WhereLogicField(column="name_")
    private String accurateName;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    private String gender;

    /**
     * 婚育情况
     */
    private String marriage;

    /**
     * 所在省份
     */
    private String province;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 联系电话
     */
    @WhereLogicField(operator = WhereLogicOperator.LIKE)
    private String phone;
    @WhereLogicField(column="phone_")
    private String accuratePhone;

    /**
     * 行业经验
     */
    @WhereLogicField(operator = WhereLogicOperator.LIKE)
    private String experience;
    /**
     * 人才目前状态
     */
    private String currentState;

    /**
     * 简历匹配的岗位
     */
    @WhereLogicField(column = "resume_job_")
    private Collection<String> resumeJob;
    /**
     * 简历与所需岗位匹配度
     */
    @WhereLogicField(operator = WhereLogicOperator.LIKE)
    private String resumeJobMatch;

    @WhereLogicField(column = "hr_status_")
    private Collection<String> hrStatus;


    @WhereLogicField(column = "bp_status_")
    private Collection<String> bpStatus;


    /**
     * 收到简历日期
     */
    @WhereLogicField(wherePart = " follow_Next_Date_ >= #{followNextDateFrom} ")
    private LocalDate followNextDateFrom;

    /**
     * 收到简历日期
     */
    @WhereLogicField(wherePart = " follow_Next_Date_ < DATE_ADD(#{followNextDateTo},INTERVAL 1 DAY) ")
    @WhereLogicField(wherePart = " follow_Next_Date_ < (#{followNextDateTo} + interval '1 D' ) ",databaseId = MybatisConst.POSTGRE_DB)
    private LocalDate followNextDateTo;


    /**********************************************跟进情况************************************************************/

    /**
     * 跟进负责人
     */
    @WhereLogicField(wherePart = " exists (select 1 from bms_talent_follow_ where info_id_ = bms_talent_info_.id_ " +
            "   and follow_principal_ = #{followPrincipal} ) ")
    private String followPrincipal;

    /**
     * 面试人
     */
    private String roundInterviewer;

    /**
     * 面试日期
     */
    @WhereLogicField(wherePart = " round_Date_ >= #{roundDateFrom} ")
    private LocalDateTime roundDateFrom;

    /**
     * 面试日期
     */
    @WhereLogicField(wherePart = " round_Date_ <= #{roundDateTo} ")
    private LocalDateTime roundDateTo;

    /**
     * 是否入职
     */
    private Boolean join;


    /**
     * 标签搜索
     */
    @WhereLogicField(parser = TalentPoolLabelParser.class)
    private Collection<String> label;

    private Integer folderId;

}
