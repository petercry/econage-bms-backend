package com.econage.extend.modular.bms.kanban.entity;

import com.econage.extend.modular.bms.util.ApiFileEntity;
import com.econage.extend.modular.bms.util.ApiFileEntityForAf;
import com.flowyun.cornerstone.db.mybatis.annotations.TableDef;
import com.flowyun.cornerstone.db.mybatis.annotations.TableField;
import com.flowyun.cornerstone.db.mybatis.entity.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@TableDef("bms_dev_task")
public class TaskEntity extends BaseEntity {
    private String id;
    private String requireId;
    private String code;
    @TableField(defaultUpdate = false)
    private Boolean valid;

    @TableField(defaultUpdate = false)
    private Long orderSeq;
    private Integer priority;
    private String desc;
    private Integer status;
    @TableField(exist = false)
    private String statusDesc;
    private LocalDate expectFinishDate;
    private LocalDate actualFinishDate;
    private Double estimateManHour;
    private Double actualManHour;
    private Double performManHour;
    private String dealer;

    @TableField(exist = false)
    private String dealerName;

    private String followuper;

    @TableField(exist = false)
    private String followuperName;

    private String comments;

    @TableField(exist = false)
    private String projectDesc;
    @TableField(exist = false)
    private String requireDesc;

    private String preSysFlag;
    private String preSysId;
    private String preSysParentId;
    private Integer projectFlag;
    private String projectId;
    private String title;
    private String sourceFlag;
    private String sourceDesc;
    private Integer typeId;

    @TableField(exist = false)
    private String typeDesc;
    private Integer currentRate;
    private Integer exceptionFlag;
    private String leaderConfirmDesc;
    private String leaderConfirmInputer;
    @TableField(exist = false)
    private String leaderConfirmInputerName;
    private LocalDateTime leaderConfirmCreateTime;
    @TableField(exist = false)
    private ArrayList<ApiFileEntityForAf> apiFileVForAf;

    @TableField(exist = false)
    private  Object apiFileVForAf_str;

    @TableField(exist = false)
    private ApiFileEntity[] apiFileV;

    @TableField(exist = false)
    private String devPlatformUrl;
    @TableField(exist = false)
    private String devPlatformUserid;
    @TableField(exist = false)
    private String devPlatformPwd;
    @TableField(exist = false)
    private String devPlatformDbIp;
    @TableField(exist = false)
    private String devPlatformDbSid;
    @TableField(exist = false)
    private String devVersionLib;
    @TableField(exist = false)
    private String projectManagerName;

    @TableField(exist = false)
    private String reqDataId;

    @TableField(exist = false)
    private String taskDataId;

    @TableField(exist = false)
    private String taskId;

    private String preSysTaskDataId;
    private String preSysTaskId;

    private String pmConfirmJudge;
    private String pmConfirmDesc;
    private String pmConfirmInputer;
    private LocalDateTime pmConfirmCreateTime;
    @TableField(exist = false)
    private String createUserName;
    @TableField(exist = false)
    private Integer calendarCount;

    public String getFollowuper() {
        return followuper;
    }

    public void setFollowuper(String followuper) {
        this.followuper = followuper;
    }

    public String getFollowuperName() {
        return followuperName;
    }

    public void setFollowuperName(String followuperName) {
        this.followuperName = followuperName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCalendarCount() {
        return calendarCount;
    }

    public void setCalendarCount(Integer calendarCount) {
        this.calendarCount = calendarCount;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getPmConfirmJudge() {
        return pmConfirmJudge;
    }

    public void setPmConfirmJudge(String pmConfirmJudge) {
        this.pmConfirmJudge = pmConfirmJudge;
    }

    public String getPmConfirmDesc() {
        return pmConfirmDesc;
    }

    public void setPmConfirmDesc(String pmConfirmDesc) {
        this.pmConfirmDesc = pmConfirmDesc;
    }

    public String getPmConfirmInputer() {
        return pmConfirmInputer;
    }

    public void setPmConfirmInputer(String pmConfirmInputer) {
        this.pmConfirmInputer = pmConfirmInputer;
    }

    public LocalDateTime getPmConfirmCreateTime() {
        return pmConfirmCreateTime;
    }

    public void setPmConfirmCreateTime(LocalDateTime pmConfirmCreateTime) {
        this.pmConfirmCreateTime = pmConfirmCreateTime;
    }

    public String getPreSysTaskDataId() {
        return preSysTaskDataId;
    }

    public void setPreSysTaskDataId(String preSysTaskDataId) {
        this.preSysTaskDataId = preSysTaskDataId;
    }

    public String getPreSysTaskId() {
        return preSysTaskId;
    }

    public void setPreSysTaskId(String preSysTaskId) {
        this.preSysTaskId = preSysTaskId;
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

    public Object getApiFileVForAf_str() {
        return apiFileVForAf_str;
    }

    public void setApiFileVForAf_str(Object apiFileVForAf_str) {
        this.apiFileVForAf_str = apiFileVForAf_str;
    }

    public String getReqDataId() {
        return reqDataId;
    }

    public void setReqDataId(String reqDataId) {
        this.reqDataId = reqDataId;
    }

    public String getDevPlatformUrl() {
        return devPlatformUrl;
    }

    public void setDevPlatformUrl(String devPlatformUrl) {
        this.devPlatformUrl = devPlatformUrl;
    }

    public String getDevPlatformUserid() {
        return devPlatformUserid;
    }

    public void setDevPlatformUserid(String devPlatformUserid) {
        this.devPlatformUserid = devPlatformUserid;
    }

    public String getDevPlatformPwd() {
        return devPlatformPwd;
    }

    public void setDevPlatformPwd(String devPlatformPwd) {
        this.devPlatformPwd = devPlatformPwd;
    }

    public String getDevPlatformDbIp() {
        return devPlatformDbIp;
    }

    public void setDevPlatformDbIp(String devPlatformDbIp) {
        this.devPlatformDbIp = devPlatformDbIp;
    }

    public String getDevPlatformDbSid() {
        return devPlatformDbSid;
    }

    public void setDevPlatformDbSid(String devPlatformDbSid) {
        this.devPlatformDbSid = devPlatformDbSid;
    }

    public String getDevVersionLib() {
        return devVersionLib;
    }

    public void setDevVersionLib(String devVersionLib) {
        this.devVersionLib = devVersionLib;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public ArrayList<ApiFileEntityForAf> getApiFileVForAf() {
        return apiFileVForAf;
    }

    public void setApiFileVForAf(ArrayList<ApiFileEntityForAf> apiFileVForAf) {
        this.apiFileVForAf = apiFileVForAf;
    }

    public ApiFileEntity[] getApiFileV() {
        return apiFileV;
    }

    public void setApiFileV(ApiFileEntity[] apiFileV) {
        this.apiFileV = apiFileV;
    }

    public Integer getExceptionFlag() {
        return exceptionFlag;
    }

    public void setExceptionFlag(Integer exceptionFlag) {
        this.exceptionFlag = exceptionFlag;
    }

    public String getLeaderConfirmDesc() {
        return leaderConfirmDesc;
    }

    public void setLeaderConfirmDesc(String leaderConfirmDesc) {
        this.leaderConfirmDesc = leaderConfirmDesc;
    }

    public String getLeaderConfirmInputer() {
        return leaderConfirmInputer;
    }

    public void setLeaderConfirmInputer(String leaderConfirmInputer) {
        this.leaderConfirmInputer = leaderConfirmInputer;
    }

    public String getLeaderConfirmInputerName() {
        return leaderConfirmInputerName;
    }

    public void setLeaderConfirmInputerName(String leaderConfirmInputerName) {
        this.leaderConfirmInputerName = leaderConfirmInputerName;
    }

    public LocalDateTime getLeaderConfirmCreateTime() {
        return leaderConfirmCreateTime;
    }

    public void setLeaderConfirmCreateTime(LocalDateTime leaderConfirmCreateTime) {
        this.leaderConfirmCreateTime = leaderConfirmCreateTime;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getRequireDesc() {
        return requireDesc;
    }

    public void setRequireDesc(String requireDesc) {
        this.requireDesc = requireDesc;
    }

    public Integer getProjectFlag() {
        return projectFlag;
    }

    public void setProjectFlag(Integer projectFlag) {
        this.projectFlag = projectFlag;
    }

    public Integer getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(Integer currentRate) {
        this.currentRate = currentRate;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequireId() {
        return requireId;
    }

    public void setRequireId(String requireId) {
        this.requireId = requireId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getExpectFinishDate() {
        return expectFinishDate;
    }

    public void setExpectFinishDate(LocalDate expectFinishDate) {
        this.expectFinishDate = expectFinishDate;
    }

    public LocalDate getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(LocalDate actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Double getEstimateManHour() {
        return estimateManHour;
    }

    public void setEstimateManHour(Double estimateManHour) {
        this.estimateManHour = estimateManHour;
    }

    public Double getActualManHour() {
        return actualManHour;
    }

    public void setActualManHour(Double actualManHour) {
        this.actualManHour = actualManHour;
    }

    public Double getPerformManHour() {
        return performManHour;
    }

    public void setPerformManHour(Double performManHour) {
        this.performManHour = performManHour;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getPreSysFlag() {
        return preSysFlag;
    }

    public void setPreSysFlag(String preSysFlag) {
        this.preSysFlag = preSysFlag;
    }

    public String getPreSysId() {
        return preSysId;
    }

    public void setPreSysId(String preSysId) {
        this.preSysId = preSysId;
    }

    public String getPreSysParentId() {
        return preSysParentId;
    }

    public void setPreSysParentId(String preSysParentId) {
        this.preSysParentId = preSysParentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }
}
