package com.econage.extend.modular.bms.employeeManage.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_employee_examine_detail")
public class EmployeeExamineDetailEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Long orderSeq;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    private String examineId; //考核主表ID
    private String userId; //人员ID
    private String gradeModel; //评分模型KV
    private Double leaderGrade; //上级评分
    private Double coworkerGrade; //伙伴评分
    private Double pmoGrade; //PMO评分
    private Double manhourGrade; //工时计分
    private Double posLevelGrade; //职等计分
    private Double ctoGrade; //CTO评分
    private Double productLeaderGrade; //产品Leader评分
    private Double productAssistantGrade; //产品助理评分
    private Double finalGrade; //最终绩效分
    private String comments; //备注

    private Double finalRewardScore; //最终考核奖金系数
    private Double wageBase; //薪资基数(年终考核时，通常为全年平均工资)
    private Double extraReward; //额外奖金
    private Double examineReward; //考核绩效奖金
    private Double finalTotalReward; //最终总奖金

    public Double getFinalRewardScore() {
        return finalRewardScore;
    }

    public void setFinalRewardScore(Double finalRewardScore) {
        this.finalRewardScore = finalRewardScore;
    }

    public Double getWageBase() {
        return wageBase;
    }

    public void setWageBase(Double wageBase) {
        this.wageBase = wageBase;
    }

    public Double getExtraReward() {
        return extraReward;
    }

    public void setExtraReward(Double extraReward) {
        this.extraReward = extraReward;
    }

    public Double getExamineReward() {
        return examineReward;
    }

    public void setExamineReward(Double examineReward) {
        this.examineReward = examineReward;
    }

    public Double getFinalTotalReward() {
        return finalTotalReward;
    }

    public void setFinalTotalReward(Double finalTotalReward) {
        this.finalTotalReward = finalTotalReward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGradeModel() {
        return gradeModel;
    }

    public void setGradeModel(String gradeModel) {
        this.gradeModel = gradeModel;
    }

    public Double getLeaderGrade() {
        return leaderGrade;
    }

    public void setLeaderGrade(Double leaderGrade) {
        this.leaderGrade = leaderGrade;
    }

    public Double getCoworkerGrade() {
        return coworkerGrade;
    }

    public void setCoworkerGrade(Double coworkerGrade) {
        this.coworkerGrade = coworkerGrade;
    }

    public Double getPmoGrade() {
        return pmoGrade;
    }

    public void setPmoGrade(Double pmoGrade) {
        this.pmoGrade = pmoGrade;
    }

    public Double getManhourGrade() {
        return manhourGrade;
    }

    public void setManhourGrade(Double manhourGrade) {
        this.manhourGrade = manhourGrade;
    }

    public Double getPosLevelGrade() {
        return posLevelGrade;
    }

    public void setPosLevelGrade(Double posLevelGrade) {
        this.posLevelGrade = posLevelGrade;
    }

    public Double getCtoGrade() {
        return ctoGrade;
    }

    public void setCtoGrade(Double ctoGrade) {
        this.ctoGrade = ctoGrade;
    }

    public Double getProductLeaderGrade() {
        return productLeaderGrade;
    }

    public void setProductLeaderGrade(Double productLeaderGrade) {
        this.productLeaderGrade = productLeaderGrade;
    }

    public Double getProductAssistantGrade() {
        return productAssistantGrade;
    }

    public void setProductAssistantGrade(Double productAssistantGrade) {
        this.productAssistantGrade = productAssistantGrade;
    }

    public Double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
