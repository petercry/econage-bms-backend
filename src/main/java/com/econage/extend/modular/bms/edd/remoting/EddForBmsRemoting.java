package com.econage.extend.modular.bms.edd.remoting;

import com.dingtalk.api.request.OapiAttendanceApproveCheckRequest;
import com.dingtalk.api.request.OapiAttendanceApproveFinishRequest;
import com.dingtalk.api.request.OapiAttendanceGetupdatedataRequest;
import com.dingtalk.api.request.OapiAttendanceScheduleListbyusersRequest;
import com.dingtalk.api.response.OapiAttendanceApproveCheckResponse;
import com.dingtalk.api.response.OapiAttendanceApproveFinishResponse;
import com.dingtalk.api.response.OapiAttendanceGetupdatedataResponse;
import com.dingtalk.api.response.OapiAttendanceScheduleListbyusersResponse;
import com.econage.integration.ali.dingding.enterprise.remoting.AbstractEddRemoting;
import com.econage.integration.ali.dingding.enterprise.remoting.helper.EddClient;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * remoting用于封装钉钉原始接口调用
 * 固定继承AbstractEddRemoting，提供一个改造过的带重试功能的企业钉钉客户端
 *
 * @author stone
 * @date 2021/08/17
 */
@Component
public class EddForBmsRemoting extends AbstractEddRemoting {

    /**
     * 获取用户在某一天的考勤数据
     *
     * @param accessToken 访问令牌
     * @param userHrLink  钉钉用户机构id值
     * @param date        日期
     * @return 钉钉sdk考勤对象
     */
    public OapiAttendanceGetupdatedataResponse.AtCheckInfoForOpenVo getUserAttendanceData(
            String accessToken,
            String userHrLink,
            @NonNull Date date
    ){
        Assert.hasText(accessToken,"accessToken is empty");
        Assert.hasText(userHrLink,"userHrLink is empty");

        EddClient.DingTalkClientProxy client = newClient("/topapi/attendance/getupdatedata");
        OapiAttendanceGetupdatedataRequest req = new OapiAttendanceGetupdatedataRequest();
        req.setUserid(userHrLink);
        req.setWorkDate(date);
        OapiAttendanceGetupdatedataResponse rsp = client.execute(req, accessToken);
        return rsp.getResult();
    }

    //通知审批通过
    public OapiAttendanceApproveFinishResponse.TopDurationVo attendanceApproveFinish(
            String accessToken,
            String userHrLink,
            Long bizType ,
            String fromTime ,
            String toTime ,
            Long calculateModel , //0:按自然日计算；1：按工作日计算
            String durationUnit ,
            String tagName ,
            String subType ,
            String overtimeDuration ,
            Long overtimeToMore
    ){
        Assert.hasText(accessToken,"accessToken is empty");
        Assert.hasText(userHrLink,"userHrLink is empty");

        EddClient.DingTalkClientProxy client = newClient("/topapi/attendance/approve/finish");
        OapiAttendanceApproveFinishRequest req = new OapiAttendanceApproveFinishRequest();
        req.setUserid(userHrLink);
        req.setBizType(bizType);
        req.setFromTime(fromTime);
        req.setToTime(toTime);
        req.setDurationUnit(durationUnit);
        req.setCalculateModel(calculateModel);
        req.setTagName(tagName);
        req.setSubType(subType);
        req.setApproveId("0");
        req.setJumpUrl("0");
        req.setOvertimeDuration(overtimeDuration);
        req.setOvertimeToMore(overtimeToMore);
        OapiAttendanceApproveFinishResponse rsp = client.execute(req, accessToken);
        return rsp.getResult();
    }

    //批量查询人员排班信息
    public List<OapiAttendanceScheduleListbyusersResponse.TopScheduleVo> getAttendanceScheduleList(
            String accessToken ,
            String opUserId ,
            String userIds ,
            Long fromTime ,
            Long toTime
    ){
        Assert.hasText(accessToken,"accessToken is empty");
        Assert.hasText(opUserId,"userHrLink is empty");

        EddClient.DingTalkClientProxy client = newClient("/topapi/attendance/schedule/listbyusers");
        OapiAttendanceScheduleListbyusersRequest req = new OapiAttendanceScheduleListbyusersRequest();
//        req.setOpUserId("user456");
//        req.setUserids("user123,user456");
//        req.setFromDateTime(1565591096000L);
//        req.setToDateTime(1565591096000L);
        req.setOpUserId(opUserId);
        req.setUserids(userIds);
        req.setFromDateTime(fromTime);
        req.setToDateTime(toTime);
        OapiAttendanceScheduleListbyusersResponse rsp = client.execute(req, accessToken);
        return rsp.getResult();
    }
    //通知补卡通过
    public OapiAttendanceApproveCheckResponse attendanceApproveCheck(
            String accessToken ,
            String userId ,
            String workDate ,
            Long punchId ,
            String punchCheckTime ,
            String userCheckTime
    ){
        Assert.hasText(accessToken,"accessToken is empty");
        Assert.hasText(userId,"userId is empty");

        EddClient.DingTalkClientProxy client = newClient("/topapi/attendance/approve/check");

        OapiAttendanceApproveCheckRequest req = new OapiAttendanceApproveCheckRequest();
        req.setUserid(userId);
        req.setWorkDate(workDate);
        req.setPunchId(punchId);
        req.setPunchCheckTime(punchCheckTime);
        req.setUserCheckTime(userCheckTime);
        req.setApproveId("0");
        req.setJumpUrl("0");
        req.setTagName("补卡");
        return client.execute(req, accessToken);
    }
//    //发送消息到企业群
//    public OapiChatSendResponse chatGroupSendInMd(
//            String accessToken ,
//            String chatId ,
//            OapiChatSendRequest.Msg msg
//    ){
//        Assert.hasText(accessToken,"accessToken is empty");
//        Assert.hasText(chatId,"chatId is empty");
//
//        EddClient.DingTalkClientProxy client = newClient("/chat/send");
//
//        OapiChatSendRequest req = new OapiChatSendRequest();
//        req.setChatid(chatId);
//        req.setMsg(msg);
//        return client.execute(req, accessToken);
//    }

//    //发送消息到企业群
//    public OapiChatSendResponse chatGroupSendInMd(
//            String accessToken ,
//            JSONObject msgObj
//    ){
//        Assert.hasText(accessToken,"accessToken is empty");
//
//        EddClient.DingTalkClientProxy client = newClient("/robot/send");
//
//        OapiChatSendRequest req = new OapiChatSendRequest();
//        req.setMsg(msg);
//        return client.execute(msgObj, accessToken);
//    }
}
