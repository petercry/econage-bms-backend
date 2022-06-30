package com.econage.extend.modular.bms.basic.entity;

import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

@TableDef("bms_data_journal")
public class BmsDataJournalEntity extends BaseEntity {
    private String id;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;

    private String modular;
    private String modularInnerId;
    private String modularDesc;

    @TableField(exist = false)
    private String modularInnerId1;
    @TableField(exist = false)
    private String modularInnerId2;

    @TableField(exist = false)
    private String modularDesc1;
    @TableField(exist = false)
    private String modularDesc2;

    private String sourceFlag;
    private String sourceId;
    private String sourceDesc;
    private Integer funcFlag;
    private String dataText;
    private Double dataNumber;

    @TableField(exist = false)
    private String dataNumberStr;

    private String extContent;
    private String comments;

    private String dataText1;
    private String dataText2;
    private String dataText3;
    private String dataText4;
    private String dataText5;
    private String finalRelatedProjectId;
    @TableField(exist = false)
    private String relatedBaName;

    public String getRelatedBaName() {
        return relatedBaName;
    }

    public void setRelatedBaName(String relatedBaName) {
        this.relatedBaName = relatedBaName;
    }

    public String getFinalRelatedProjectId() {
        return finalRelatedProjectId;
    }

    public void setFinalRelatedProjectId(String finalRelatedProjectId) {
        this.finalRelatedProjectId = finalRelatedProjectId;
    }

    @TableField(exist = false)
    private String reqDataId;

    @TableField(exist = false)
    private String taskDataId;

    @TableField(exist = false)
    private String taskId;

    @TableField(exist = false)
    private String createUserDesc;

    @TableField(exist = false)
    private Integer forbidFillExtContent;  //是否禁止同时填充流程扩展信息（回调AF的获取全部流程数据的接口），空或0时默认会取，为1时不取

    public Integer getForbidFillExtContent() {
        return forbidFillExtContent;
    }

    public void setForbidFillExtContent(Integer forbidFillExtContent) {
        this.forbidFillExtContent = forbidFillExtContent;
    }

    public String getCreateUserDesc() {
        return createUserDesc;
    }

    public void setCreateUserDesc(String createUserDesc) {
        this.createUserDesc = createUserDesc;
    }

    public String getReqDataId() {
        return reqDataId;
    }

    public void setReqDataId(String reqDataId) {
        this.reqDataId = reqDataId;
    }

    public String getTaskDataId() {
        return taskDataId;
    }

    public void setTaskDataId(String taskDataId) {
        this.taskDataId = taskDataId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDataText1() {
        return dataText1;
    }

    public void setDataText1(String dataText1) {
        this.dataText1 = dataText1;
    }

    public String getDataText2() {
        return dataText2;
    }

    public void setDataText2(String dataText2) {
        this.dataText2 = dataText2;
    }

    public String getDataText3() {
        return dataText3;
    }

    public void setDataText3(String dataText3) {
        this.dataText3 = dataText3;
    }

    public String getDataText4() {
        return dataText4;
    }

    public void setDataText4(String dataText4) {
        this.dataText4 = dataText4;
    }

    public String getDataText5() {
        return dataText5;
    }

    public void setDataText5(String dataText5) {
        this.dataText5 = dataText5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(Long orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getModularInnerId() {
        return modularInnerId;
    }

    public void setModularInnerId(String modularInnerId) {
        this.modularInnerId = modularInnerId;
    }

    public String getModularDesc() {
        return modularDesc;
    }

    public void setModularDesc(String modularDesc) {
        this.modularDesc = modularDesc;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    public Integer getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(Integer funcFlag) {
        this.funcFlag = funcFlag;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

    public Double getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(Double dataNumber) {
        this.dataNumber = dataNumber;
    }

    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getModularInnerId1() {
        return modularInnerId1;
    }

    public void setModularInnerId1(String modularInnerId1) {
        this.modularInnerId1 = modularInnerId1;
    }

    public String getModularInnerId2() {
        return modularInnerId2;
    }

    public void setModularInnerId2(String modularInnerId2) {
        this.modularInnerId2 = modularInnerId2;
    }

    public String getModularDesc1() {
        return modularDesc1;
    }

    public void setModularDesc1(String modularDesc1) {
        this.modularDesc1 = modularDesc1;
    }

    public String getModularDesc2() {
        return modularDesc2;
    }

    public void setModularDesc2(String modularDesc2) {
        this.modularDesc2 = modularDesc2;
    }

    public String getDataNumberStr() {
        return dataNumberStr;
    }

    public void setDataNumberStr(String dataNumberStr) {
        this.dataNumberStr = dataNumberStr;
    }
}
