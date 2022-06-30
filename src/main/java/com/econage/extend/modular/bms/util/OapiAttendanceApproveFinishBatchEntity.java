package com.econage.extend.modular.bms.util;

import com.dingtalk.api.response.OapiAttendanceApproveFinishResponse;
import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.internal.util.TaobaoHashMap;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OapiAttendanceApproveFinishBatchEntity extends BaseTaobaoRequest<OapiAttendanceApproveFinishResponse> {
    private String approveId;
    private Long bizType;
    private Long calculateModel;
    private String durationUnit;
    private String jumpUrl;
    private String applyType;
    private String fromTime;
    private String toTime;
    private ArrayList<LinkedHashMap<String , String >> timeStr;
    private String overtimeDuration;
    private Long overtimeToMore;
    private String subType;
    private String tagName;
    private String userid;
    private String topResponseType = "dingtalk";
    private String topHttpMethod = "POST";

    public OapiAttendanceApproveFinishBatchEntity() {
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getApproveId() {
        return this.approveId;
    }

    public void setBizType(Long bizType) {
        this.bizType = bizType;
    }

    public Long getBizType() {
        return this.bizType;
    }

    public void setCalculateModel(Long calculateModel) {
        this.calculateModel = calculateModel;
    }

    public Long getCalculateModel() {
        return this.calculateModel;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public String getDurationUnit() {
        return this.durationUnit;
    }


    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getJumpUrl() {
        return this.jumpUrl;
    }

    public ArrayList<LinkedHashMap<String, String>> getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(ArrayList<LinkedHashMap<String, String>> timeStr) {
        this.timeStr = timeStr;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubType() {
        return this.subType;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return this.tagName;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return this.userid;
    }

    public String getApiMethodName() {
        return "dingtalk.oapi.attendance.approve.finish";
    }

    public String getTopResponseType() {
        return this.topResponseType;
    }

    public void setTopResponseType(String topResponseType) {
        this.topResponseType = topResponseType;
    }

    public String getTopApiCallType() {
        return "oapi";
    }

    public String getTopHttpMethod() {
        return this.topHttpMethod;
    }

    public void setTopHttpMethod(String topHttpMethod) {
        this.topHttpMethod = topHttpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.setTopHttpMethod(httpMethod);
    }

    public String getOvertimeDuration() {
        return overtimeDuration;
    }

    public void setOvertimeDuration(String overtimeDuration) {
        this.overtimeDuration = overtimeDuration;
    }

    public Long getOvertimeToMore() {
        return overtimeToMore;
    }

    public void setOvertimeToMore(Long overtimeToMore) {
        this.overtimeToMore = overtimeToMore;
    }

    public Map<String, String> getTextParams() {
        TaobaoHashMap txtParams = new TaobaoHashMap();
        txtParams.put("approve_id", this.approveId);
        txtParams.put("biz_type", this.bizType);
        txtParams.put("calculate_model", this.calculateModel);
        txtParams.put("duration_unit", this.durationUnit);
        txtParams.put("jump_url", this.jumpUrl);
        txtParams.put("timeStr", this.timeStr);
        txtParams.put("sub_type", this.subType);
        txtParams.put("tag_name", this.tagName);
        txtParams.put("overtime_duration", this.overtimeDuration);
        txtParams.put("overtime_to_more", this.overtimeToMore);
        txtParams.put("userid", this.userid);
        if (this.udfParams != null) {
            txtParams.putAll(this.udfParams);
        }

        return txtParams;
    }

    public Class<OapiAttendanceApproveFinishResponse> getResponseClass() {
        return OapiAttendanceApproveFinishResponse.class;
    }

    public void check() throws ApiRuleException {
        RequestCheckUtils.checkMaxLength(this.approveId, 100, "approveId");
        RequestCheckUtils.checkMaxLength(this.jumpUrl, 200, "jumpUrl");
        RequestCheckUtils.checkMaxLength(this.subType, 20, "subType");
        RequestCheckUtils.checkMaxLength(this.tagName, 20, "tagName");
    }
}
