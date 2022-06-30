package com.econage.extend.modular.bms.talentpool.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Chris Bosh
 * @date: 2021/2/19 14:25
 * @description:
 */
@TableDef("bms_talent_info_")
@Data
@EqualsAndHashCode(callSuper = true)
public class BmsTalentInfoEntity extends BaseEntity {
    private String id;
    /**
     * 简历来源
     */
    private String resumeSource;
    /**
     * 收到简历日期
     */
    private LocalDate resumeDate;
    /**
     * 简历姓名
     */
    private String name;
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
    private String phone;

    /**
     * 行业经验
     */
    private String experience;
    /**
     * 人才目前状态
     */
    private String currentState;
    /**
     * 简历匹配的岗位
     */
    private String resumeJob;
    /**
     * 简历与所需岗位匹配度
     */
    private String resumeJobMatch;
/*    *//**
     * 跟进状态
     *//*
    private String followStatus;*/
/*    *//**
     * 跟进结果
     *//*
    @Deprecated
    private String followResult;
    @Deprecated
    @TableField(exist = false)
    private String followResultDesc;*/

    /**
     * hr跟进状态
     */
    private String hrStatus;

    /**
     * bp跟进状态
     */
    private String bpStatus;

    /**
     * 下次跟进时间
     */
    private LocalDateTime followNextDate;

    private String comment;

    /**
     * 面试日期
     */
    @TableField(defaultUpdate = false)
    private LocalDateTime roundDate;
    /**
     * 面试人
     */
    @TableField(defaultUpdate = false)
    private String roundInterviewer;

    /**
     * 面试方式
     */
    @TableField(defaultUpdate = false)
    private String roundMethod;

    /**
     * 是否入职
     */
    @TableField(value = "is_join_",defaultUpdate = false)
    private Boolean join;
    /**
     * 入职时间
     */
    @TableField(defaultUpdate = false)
    private LocalDate joinDate;


    @TableField(exist = false)
    public String roundInterviewerName;

    //private List<String> followResultList;

    @TableField(exist = false)
    public String modUserName;

    @TableField(exist = false)
    private String hrStatusDesc;
    @TableField(exist = false)
    private String bpStatusDesc;

    private List<BmsTalentInfoLabelEntity> labels;

    private Integer folderId;

    @TableField(exist = false)
    private String interviewArrangeDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResumeSource() {
        return resumeSource;
    }

    public void setResumeSource(String resumeSource) {
        this.resumeSource = resumeSource;
    }

    public LocalDate getResumeDate() {
        return resumeDate;
    }

    public void setResumeDate(LocalDate resumeDate) {
        this.resumeDate = resumeDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getResumeJob() {
        return resumeJob;
    }

    public void setResumeJob(String resumeJob) {
        this.resumeJob = resumeJob;
    }

    public String getResumeJobMatch() {
        return resumeJobMatch;
    }

    public void setResumeJobMatch(String resumeJobMatch) {
        this.resumeJobMatch = resumeJobMatch;
    }

    public String getHrStatus() {
        return hrStatus;
    }

    public void setHrStatus(String hrStatus) {
        this.hrStatus = hrStatus;
    }

    public String getBpStatus() {
        return bpStatus;
    }

    public void setBpStatus(String bpStatus) {
        this.bpStatus = bpStatus;
    }

    public LocalDateTime getFollowNextDate() {
        return followNextDate;
    }

    public void setFollowNextDate(LocalDateTime followNextDate) {
        this.followNextDate = followNextDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getRoundDate() {
        return roundDate;
    }

    public void setRoundDate(LocalDateTime roundDate) {
        this.roundDate = roundDate;
    }

    public String getRoundInterviewer() {
        return roundInterviewer;
    }

    public void setRoundInterviewer(String roundInterviewer) {
        this.roundInterviewer = roundInterviewer;
    }

    public String getRoundMethod() {
        return roundMethod;
    }

    public void setRoundMethod(String roundMethod) {
        this.roundMethod = roundMethod;
    }

    public Boolean getJoin() {
        return join;
    }

    public void setJoin(Boolean join) {
        this.join = join;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getRoundInterviewerName() {
        return roundInterviewerName;
    }

    public void setRoundInterviewerName(String roundInterviewerName) {
        this.roundInterviewerName = roundInterviewerName;
    }

    public String getModUserName() {
        return modUserName;
    }

    public void setModUserName(String modUserName) {
        this.modUserName = modUserName;
    }

    public String getHrStatusDesc() {
        return hrStatusDesc;
    }

    public void setHrStatusDesc(String hrStatusDesc) {
        this.hrStatusDesc = hrStatusDesc;
    }

    public String getBpStatusDesc() {
        return bpStatusDesc;
    }

    public void setBpStatusDesc(String bpStatusDesc) {
        this.bpStatusDesc = bpStatusDesc;
    }

    public List<BmsTalentInfoLabelEntity> getLabels() {
        return labels;
    }

    public void setLabels(List<BmsTalentInfoLabelEntity> labels) {
        this.labels = labels;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getInterviewArrangeDesc() {
        return interviewArrangeDesc;
    }

    public void setInterviewArrangeDesc(String interviewArrangeDesc) {
        this.interviewArrangeDesc = interviewArrangeDesc;
    }
}
