package com.econage.extend.modular.bms.edd.master;

import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.response.*;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.time.DateTimeConverterUtil;
import com.econage.core.basic.util.SystemClock;
import com.econage.extend.modular.bms.edd.remoting.EddForBmsRemoting;
import com.econage.integration.ali.dingding.enterprise.master.AbstractEddMaster;
import com.econage.integration.ali.dingding.enterprise.master.EddAuthMaster;
import lombok.NonNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * master类，服务业务模块，进一步隐藏接口交互细节
 * 固定继承AbstractEddMaster
 *
 * @author stone
 * @date 2021/08/17
 */
@Component
public class EddForBmsMaster extends AbstractEddMaster {

    /*
    * 必定引入，会自动处理accessToken缓存和获取
    * */
    private EddAuthMaster eddAuthMaster;

    private EddForBmsRemoting eddForBmsRemoting;

    private UserUnionQuery userServiceUnionService;

    @Autowired
    void setWried(
            EddAuthMaster eddAuthMaster,
            EddForBmsRemoting eddForBmsRemoting,
            UserUnionQuery userServiceUnionService
    ){
        this.eddAuthMaster = eddAuthMaster;
        this.eddForBmsRemoting = eddForBmsRemoting;
        this.userServiceUnionService = userServiceUnionService;
    }


    /**
     * 获取当前时间用户的考勤数据
     *
     * @param userId 用户id
     * @return 用户考勤对象
     */
    public OapiAttendanceGetupdatedataResponse.AtCheckInfoForOpenVo getCurrUserAttendanceData(String userId){
        return getUserAttendanceData(userId, SystemClock.nowDateTime());
    }

    /**
     * 获取用户某一天的考勤数据
     *
     * @param userId   内部用户id
     * @param datetime datetime
     * @return 用户考勤对象
     */
    public OapiAttendanceGetupdatedataResponse.AtCheckInfoForOpenVo getUserAttendanceData(
            String userId, @NonNull LocalDateTime datetime
    ){
        Assert.hasText(userId,"userId is empty");
        var userEntity = userServiceUnionService.selectSingle(userId);
        Assert.notNull(userEntity,"not a valid userId,can't find userEntity");
        return eddForBmsRemoting.getUserAttendanceData(
                eddAuthMaster.fetchAccessToken(),
                userEntity.getHrLink(),
                DateTimeConverterUtil.toDate(datetime)
        );
    }

    //通知审批通过
    public OapiAttendanceApproveFinishResponse.TopDurationVo attendanceApproveFinish(
            String userId,
            Long bizType , //1:加班;2:出差;3:请假
            String fromTime , // 2019-08-15 \ 2019-08-15 AM \ 2019-08-15 12:43
            String toTime ,
            Long calculateModel , //0:按自然日计算；1：按工作日计算
            String durationUnit , //2019-08-15对应day \ 2019-08-15 AM对应halfDay \ 2019-08-15 12:43对应hour
            String tagName , //请假 \ 出差 \ 外出 \ 加班
            String subType ,//子类型，年假等
            String overtimeDuration , //biz_type为1时必传，加班时长单位小时
            Long overtimeToMore  //1：加班转调休 ; 2：加班转工资
    ){
        Assert.hasText(userId,"userId is empty");
        var userEntity = userServiceUnionService.selectSingle(userId);
        Assert.notNull(userEntity,"not a valid userId,can't find userEntity");
        return eddForBmsRemoting.attendanceApproveFinish(
                eddAuthMaster.fetchAccessToken() ,
                userEntity.getHrLink() ,
                bizType ,
                fromTime ,
                toTime ,
                calculateModel  ,
                durationUnit ,
                tagName ,
                subType ,
                overtimeDuration ,
                overtimeToMore
        );
    }
    //批量查询人员排班信息
    public List<OapiAttendanceScheduleListbyusersResponse.TopScheduleVo> getAttendanceScheduleList(
            String opUserId,
            String checkUserId ,
            Long fromTime ,
            Long toTime
    ){
        Assert.hasText(opUserId,"opUserId is empty");
        var opUserEntity = userServiceUnionService.selectSingle(opUserId);
        Assert.notNull(opUserEntity,"not a valid opUserId,can't find opUserEntity");

        Assert.hasText(checkUserId,"checkUserId is empty");
        var checkUserEntity = userServiceUnionService.selectSingle(checkUserId);
        Assert.notNull(checkUserEntity,"not a valid checkUserId,can't find checkUserEntity");

        return eddForBmsRemoting.getAttendanceScheduleList(
                eddAuthMaster.fetchAccessToken() ,
                opUserEntity.getHrLink() ,
                checkUserEntity.getHrLink() ,
                fromTime ,
                toTime
        ) ;

    }
    //通知补卡通过
    public OapiAttendanceApproveCheckResponse attendanceApproveCheck(
            String userId ,
            String workDate ,
            Long punchId ,
            String punchCheckTime ,
            String userCheckTime
    ){
        Assert.hasText(userId,"userId is empty");
        var userEntity = userServiceUnionService.selectSingle(userId);
        Assert.notNull(userEntity,"not a valid userId,can't find userEntity");
        return eddForBmsRemoting.attendanceApproveCheck(
                eddAuthMaster.fetchAccessToken() ,
                userEntity.getHrLink() ,
                workDate ,
                punchId ,
                punchCheckTime ,
                userCheckTime
        );
    }
//    //发送消息到企业群
//    public OapiChatSendResponse chatGroupSendInMd(
//            String chatId ,
//            String mdTitle ,
//            String mdText
//    ){
//        Assert.hasText(chatId,"chatId is empty");
//        OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
//        msg.setMsgtype("markdown");
//        OapiChatSendRequest.Markdown md = new OapiChatSendRequest.Markdown();
//        md.setTitle(mdTitle);
//        md.setText(mdText);
//        msg.setMarkdown(md);
//        return eddForBmsRemoting.chatGroupSendInMd(eddAuthMaster.fetchAccessToken() ,chatId , msg);
//    }
//    //发送消息到企业群
//    public OapiChatSendResponse chatGroupSendInMd(
//            String accessToken ,
//            String mdTitle ,
//            String mdText
//    ){
//        Assert.hasText(accessToken,"Chat accessToken is empty");
//        OapiChatSendRequest.Msg msg = new OapiChatSendRequest.Msg();
//        msg.setMsgtype("markdown");
//        OapiChatSendRequest.Markdown md = new OapiChatSendRequest.Markdown();
//        md.setTitle(mdTitle);
//        md.setText(mdText);
//        msg.setMarkdown(md);
//
//        JSONObject msgObj = new JSONObject();
//        msgObj.put("msgtype","markdown");
//        JSONObject mdObj = new JSONObject();
//        mdObj.put( "title","ptest1规则设计院3月18日项目日报");
//        mdObj.put("text","aabbcc");
//        msgObj.put("markdown",mdObj);
//        JSONObject atObj = new JSONObject();
//        atObj.put("isAtAll",false);
//        msgObj.put("at",atObj);
//        return eddForBmsRemoting.chatGroupSendInMd(accessToken , msgObj);
//    }
}
