package com.econage.extend.modular.bms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiAttendanceApproveFinishRequest;
import com.dingtalk.api.response.OapiAttendanceApproveCheckResponse;
import com.dingtalk.api.response.OapiAttendanceApproveFinishResponse;
import com.dingtalk.api.response.OapiAttendanceScheduleListbyusersResponse;
import com.econage.base.organization.org.entity.DeptEntity;
import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.organization.org.trivial.meta.UserStatusEnum;
import com.econage.base.organization.org.trivial.wherelogic.UserWhereLogic;
import com.econage.base.plat.json.JsonPrintHelper;
import com.econage.base.plat.tokenstore.BasicTokenStoreEntity;
import com.econage.base.plat.tokenstore.BasicTokenStoreService;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.base.security.userdetials.service.AggregateUserDetailsService;
import com.econage.base.support.filemanager.entity.FileBodyEntity;
import com.econage.base.support.filemanager.entity.FileHeaderEntity;
import com.econage.base.support.filemanager.manage.FileManager;
import com.econage.base.support.filemanager.service.FileAuditService;
import com.econage.core.basic.crpyto.AESCryptoUtils;
import com.econage.core.basic.file.FileSystemResourceWithNewFileName;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.basic.util.StringMore;
import com.econage.core.basic.util.SystemClock;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.controller.RestWebService;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.core.web.security.SecurityUtils;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcDriveTaskResponseDTO;
import com.econage.extend.modular.bms.afhwc.master.AfhwcWfMaster;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.econage.extend.modular.bms.ba.component.auth.service.BaAuthService;
import com.econage.extend.modular.bms.ba.component.bizOppo.entity.BizOppoEntity;
import com.econage.extend.modular.bms.ba.component.bizOppo.entity.TmpLibBizOppoEntity;
import com.econage.extend.modular.bms.ba.component.bizOppo.service.BizOppoService;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventEntity;
import com.econage.extend.modular.bms.ba.component.event.service.BmsBaEventService;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaBriefService;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsDataJournalWhereLogic;
import com.econage.extend.modular.bms.edd.master.EddForBmsMaster;
import com.econage.extend.modular.bms.kanban.entity.IterationProjectEntity;
import com.econage.extend.modular.bms.kanban.entity.RequireEntity;
import com.econage.extend.modular.bms.kanban.entity.TaskCalendarEntity;
import com.econage.extend.modular.bms.kanban.entity.TaskEntity;
import com.econage.extend.modular.bms.kanban.service.IterationProjectService;
import com.econage.extend.modular.bms.kanban.service.RequireService;
import com.econage.extend.modular.bms.kanban.service.TaskCalendarService;
import com.econage.extend.modular.bms.kanban.service.TaskService;
import com.econage.extend.modular.bms.kanban.trivial.wherelogic.TaskCalendarWhereLogic;
import com.econage.extend.modular.bms.project.entity.BmsProjectAssociationEntity;
import com.econage.extend.modular.bms.project.entity.BmsProjectEntity;
import com.econage.extend.modular.bms.project.service.*;
import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectAssociationWhereLogic;
import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectWhereLogic;
import com.econage.extend.modular.bms.util.*;
import com.econage.extend.modular.bms.util.tencentApi.InvoiceInfoEntity;
import com.econage.integration.af.entity.dto.AfDriveTaskResponseDTO;
import com.econage.integration.af.entity.model.AfDriveTaskModel;
import com.econage.integration.af.entity.model.AfWorkflowInstModel;
import com.econage.integration.af.master.AfWfMaster;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestWebService
@RequestMapping("/bms/open")
public class BmsOpenWebEndpoint extends BasicControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BmsOpenWebEndpoint.class);
    private RequireService requireService;
    private TaskService taskService;
    private UserUnionQuery userService;
    private FileManager fileManager;
    private RestTemplate restTemplate;
    private BmsProjectForImportService bmsProjectForImportService;
    private BmsProjectService bmsProjectService;
    private BmsProjectBriefService bmsProjectBriefService;
    private BmsBaService bmsBaService;
    private AfWfMaster afWfMaster;
    private AfhwcWfMaster afhwcWfMaster;
    private ThreadPoolTaskExecutor executor;
    private BmsDataJournalService dataJournalService;
    private JsonPrintHelper jsonPrintHelper;
    private FileAuditService fileAuditService;
    private BmsBaEventService bmsBaEventService;
    private TaskCalendarService taskCalendarService;
    private BmsBaBriefService bmsBaBriefService;
    private AggregateUserDetailsService aggregateUserDetailsService;
    private EddForBmsMaster eddForBmsMaster;
    private BasicTokenStoreService tokenStoreService;
    private BizOppoService bizOppoService;
    private BmsProjectAssociationService bmsProjectAssociationService;
    private ObjectMapper objMapper;
    private BmsProjectForFinSettleService bmsProjectForFinSettleService;
    private RocketMQTemplate rocketMQTemplate;
    private BaAuthService baAuthService;
    private IterationProjectService iterationProjectService;
    @Autowired(required = false)
    protected void setRocketMQTemplateService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
    @Autowired
    protected void setService(BmsProjectAssociationService bmsProjectAssociationService, ObjectMapper objMapper , BmsProjectForFinSettleService bmsProjectForFinSettleService , BaAuthService baAuthService ,IterationProjectService iterationProjectService) {
        this.bmsProjectAssociationService = bmsProjectAssociationService;
        this.objMapper = objMapper;
        this.bmsProjectForFinSettleService = bmsProjectForFinSettleService;
        this.baAuthService = baAuthService;
        this.iterationProjectService = iterationProjectService;
    }

    @Autowired
    protected void setBasicTokenStoreService(BasicTokenStoreService tokenStoreService) {
        this.tokenStoreService = tokenStoreService;
    }

    @Autowired
    protected void setEddForBmsMasterService(EddForBmsMaster eddForBmsMaster) {
        this.eddForBmsMaster = eddForBmsMaster;
    }

    @Autowired
    protected void setAggregateUserDetailsService(AggregateUserDetailsService aggregateUserDetailsService) {
        this.aggregateUserDetailsService = aggregateUserDetailsService;
    }

    @Autowired
    void autowiredFileAudit(
            FileAuditService fileAuditService) {
        this.fileAuditService = fileAuditService;
    }

    @Autowired
    protected void setJsonPrintHelper(JsonPrintHelper jsonPrintHelper) {
        this.jsonPrintHelper = jsonPrintHelper;
    }

    @Autowired
    protected void setDataJournalService(BmsDataJournalService dataJournalService) {
        this.dataJournalService = dataJournalService;
    }

    @Autowired
    protected void setAfWfMaster(AfWfMaster afWfMaster) {
        this.afWfMaster = afWfMaster;
    }

    @Autowired
    protected void setAfhwcWfMaster(AfhwcWfMaster afhwcWfMaster) {
        this.afhwcWfMaster = afhwcWfMaster;
    }

    @Autowired
    protected void setBmsProjectService(BmsProjectService bmsProjectService) {
        this.bmsProjectService = bmsProjectService;
    }

    @Autowired
    protected void setBmsProjectBriefService(BmsProjectBriefService bmsProjectBriefService) {
        this.bmsProjectBriefService = bmsProjectBriefService;
    }

    @Autowired
    protected void setBmsBaService(BmsBaService bmsBaService) {
        this.bmsBaService = bmsBaService;
    }

    @Autowired
    protected void setBmsBaBriefService(BmsBaBriefService bmsBaBriefService) {
        this.bmsBaBriefService = bmsBaBriefService;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    protected void setProps(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Autowired
    void setFileManager(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Autowired
    protected void setRequireService(RequireService requireService) {
        this.requireService = requireService;
    }

    @Autowired
    protected void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    protected void setUserService(UserUnionQuery userService) {
        this.userService = userService;
    }

    @Autowired
    protected void setBmsProjectForImportService(BmsProjectForImportService bmsProjectForImportService) {
        this.bmsProjectForImportService = bmsProjectForImportService;
    }

    @Autowired
    void setBmsBaEventService(BmsBaEventService bmsBaEventService) {
        this.bmsBaEventService = bmsBaEventService;
    }

    @Autowired
    void setTaskCalendarService(TaskCalendarService taskCalendarService) {
        this.taskCalendarService = taskCalendarService;
    }

    @Autowired
    void setBizOppoService(BizOppoService bizOppoService) {
        this.bizOppoService = bizOppoService;
    }

    private void submitAsyncTask(Runnable task) {
        Assert.notNull(task, "task is empty");
        executor.execute(task);
    }

    /*
     * ????????????-post
     * */
    @PostMapping("/kanban/require")
    protected RequireEntity newRequireEntity(
            @RequestBody RequireEntity requireEntity
    ) throws Exception {
        if (StringUtils.isEmpty(requireEntity.getPreSysFlag()) || StringUtils.isEmpty(requireEntity.getPreSysId())) {
            throw new Exception("????????????????????????????????????");
        }
        requireEntity.setId(requireEntity.getPreSysFlag() + "_" + requireEntity.getPreSysId());
        if (requireEntity.getPriority() == null || requireEntity.getPriority() == 0) {
            requireEntity.setPriority(3);
        }
        RequireEntity oriEntity = requireService.selectById(requireEntity.getId());
        if (oriEntity == null) {   //??????????????????????????????
            Double manHour = requireService.reCalcSingleRequireManHour(requireEntity.getId());
            requireEntity.setManHour(manHour);
            requireService.insert(requireEntity);
            if (requireEntity.getApiFileV() != null && requireEntity.getApiFileV().length > 0) {
                for (ApiFileEntity afe : requireEntity.getApiFileV()) {
                    //System.out.println("@@@@"+afe.getFileName() + "@@"+afe.getFileUrl());
                    Resource resp = restTemplate.getForObject(afe.getFileUrl(), Resource.class);
                    try (InputStream is = resp.getInputStream()) {
                        fileManager.newFile4WebService(
                                "bmsRequire", requireEntity.getId(),
                                afe.getFileName(),
                                is,
                                null
                        );
                    }
                }
            }

//        }else{  //????????????????????????????????????????????????
//            requireEntity.setStatus(null);
//            requireService.updateById(requireEntity);
        }
        return requireService.selectById(requireEntity.getId());
    }

    /*
     * ????????????-put
     * */
    @PutMapping("/kanban/require/{preSysFlag}/{preSysId}")
    protected RequireEntity updateRequireEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestBody RequireEntity requireEntity
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("????????????????????????????????????");
        }
        requireEntity.setId(preSysFlag + "_" + preSysId);
        requireEntity.setPreSysFlag(preSysFlag);
        requireEntity.setPreSysId(preSysId);
        boolean update_success_flag = requireService.updateById(requireEntity);
        if (!update_success_flag) throw new Exception("?????????????????????????????????????????????");
        return requireService.selectById(requireEntity.getId());
    }

    /*
     * ??????????????????-patch
     * */
    @PatchMapping("/kanban/require/{preSysFlag}/{preSysId}/status")
    protected void patchRequireEntityStatus(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestParam("status") Integer status
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("????????????????????????????????????");
        }
        boolean update_success_flag = requireService.updateRequireStatus(preSysFlag + "_" + preSysId, status);
        if (!update_success_flag) throw new Exception("?????????????????????????????????????????????");
    }

    /*
     * ????????????-patch
     * */
    @PatchMapping("/kanban/require/{preSysFlag}/{preSysId}/remove")
    protected void removeRequireEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("????????????????????????????????????");
        }
        boolean update_success_flag = requireService.changeRequireValid(preSysFlag + "_" + preSysId, false);
        if (!update_success_flag) throw new Exception("?????????????????????????????????????????????");
    }

    /*
     * ????????????-post
     * */
    @PostMapping("/kanban/task")
    protected TaskEntity newTaskEntity(
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(taskEntity.getPreSysFlag()) || StringUtils.isEmpty(taskEntity.getPreSysId())) {
            throw new Exception("????????????????????????????????????");
        }
        if ("zen".equals(taskEntity.getPreSysFlag())) {  //???????????????????????????????????????????????????
            taskEntity.setProjectFlag(2);
        } else if ("wf".equals(taskEntity.getPreSysFlag())||"af".equals(taskEntity.getPreSysFlag())) { //88crm?????????????????????????????????????????????
            taskEntity.setProjectFlag(1);
        }
        //?????????88crm????????????????????????????????????????????????id?????????wh88?????????
        if ("wf".equals(taskEntity.getPreSysFlag()) && !StringUtils.isEmpty(taskEntity.getProjectId()) && !"0".equals(taskEntity.getProjectId())) {
            String proIdWh88 = bmsProjectForImportService.getProjectIdInWh88By88crm(taskEntity.getProjectId());
            if (!StringUtils.isEmpty(proIdWh88)) {    //????????????wh88????????????id????????????
                taskEntity.setProjectId(proIdWh88);
            } else {                                   //?????????wh88??????????????????id???????????????crm88???????????????
                taskEntity.setProjectId("crm88_" + taskEntity.getProjectId());
                taskEntity.setProjectFlag(2);
            }
        }
        //?????????????????????????????????????????????id???????????????"zen_"??????
        if ("zen".equals(taskEntity.getPreSysFlag()) && !StringUtils.isEmpty(taskEntity.getRequireId()) && !"0".equals(taskEntity.getRequireId())) {
            taskEntity.setRequireId("zen_" + taskEntity.getRequireId());
        }
        if (taskEntity.getPriority() == null || taskEntity.getPriority() == 0) {
            taskEntity.setPriority(3);
        }
        if (!StringUtils.isEmpty(taskEntity.getDealer())) {
            UserWhereLogic userWhereLogic = new UserWhereLogic();
            userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
            if (!Character.isDigit((taskEntity.getDealer()).charAt(0))) {//???????????????????????????????????????????????????????????????????????????????????????dinguserid
                userWhereLogic.setNameFuzzySearch(taskEntity.getDealer());
                List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
                if (userV != null && userV.size() > 0) {
                    UserEntity ue = userV.get(0);
                    taskEntity.setDealer(ue.getId());
                }
            } else {//???????????????????????????????????????????????????????????????ding_user_id???????????????user_id
                userWhereLogic.setHrLink(taskEntity.getDealer());
                List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
                if (userV != null && userV.size() > 0) {
                    UserEntity ue = userV.get(0);
                    taskEntity.setDealer(ue.getId());
                }
            }
        }
        if (!StringUtils.isEmpty(taskEntity.getFollowuper())) {
            UserWhereLogic userWhereLogic = new UserWhereLogic();
            userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
            if (!Character.isDigit((taskEntity.getFollowuper()).charAt(0))) {//???????????????????????????????????????????????????????????????????????????????????????dinguserid
                userWhereLogic.setNameFuzzySearch(taskEntity.getFollowuper());
                List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
                if (userV != null && userV.size() > 0) {
                    UserEntity ue = userV.get(0);
                    taskEntity.setFollowuper(ue.getId());
                }
            } else {//???????????????????????????????????????????????????????????????ding_user_id???????????????user_id
                userWhereLogic.setHrLink(taskEntity.getFollowuper());
                List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
                if (userV != null && userV.size() > 0) {
                    UserEntity ue = userV.get(0);
                    taskEntity.setFollowuper(ue.getId());
                }
            }
        }
        taskEntity.setId(taskEntity.getPreSysFlag() + "_" + taskEntity.getPreSysId());
        TaskEntity oriEntity = taskService.selectById(taskEntity.getId());

        if (oriEntity == null) {   //??????????????????????????????
            taskService.insert(taskEntity);
            if (taskEntity.getApiFileV() != null && taskEntity.getApiFileV().length > 0) {
                for (ApiFileEntity afe : taskEntity.getApiFileV()) {
                    //System.out.println("@@@@"+afe.getFileName() + "@@"+afe.getFileUrl());
                    Resource resp = restTemplate.getForObject(afe.getFileUrl(), Resource.class);
                    try (InputStream is = resp.getInputStream()) {
                        fileManager.newFile4WebService(
                                "bmsTask", taskEntity.getId(),
                                afe.getFileName(),
                                is,
                                null
                        );
                    }
                }
            }
//        }else{ //???????????????????????????????????????????????????????????????
//            taskEntity.setStatusDesc(null);
//            taskEntity.setStatus(null);
//            taskEntity.setDealer(null);
//            taskEntity.setDealerName(null);
//            taskService.updateById(taskEntity);
        }
        return taskService.selectById(taskEntity.getId());
    }
    /*
     * ????????????????????????-get
     * */
    @GetMapping("/task/{taskId}")
    protected TaskEntity getSingleTaskEntity(
            @PathVariable("taskId") String taskId
    ){
        TaskEntity te = taskService.selectById(taskId);
        if(!StringUtils.isEmpty(te.getDealer())){
            te.setDealerName(userService.selectUserMi(te.getDealer()));
        }
        if(!StringUtils.isEmpty(te.getFollowuper())){
            te.setFollowuperName(userService.selectUserMi(te.getFollowuper()));
        }
        if(!StringUtils.isEmpty(te.getRequireId())){
            RequireEntity re = requireService.selectById(te.getRequireId());
            if (re!=null) {
                te.setRequireDesc("#"+re.getId()+" : "+re.getTitle()+" ( ????????????"+re.getPriority()+" )");
            }
        }
        if(te.getProjectFlag()!=null&&te.getProjectFlag()!=0&&!StringUtils.isEmpty(te.getProjectId())){
            if(te.getProjectFlag()==1){ //????????????
                BmsProjectEntity proEntity = bmsProjectService.selectById(te.getProjectId());
                if(proEntity!=null) te.setProjectDesc(proEntity.getProjectName());
            }else if(te.getProjectFlag()==2){//????????????
                IterationProjectEntity proEntity = iterationProjectService.selectById(te.getProjectId());
                if(proEntity!=null) te.setProjectDesc(proEntity.getName());
            }
        }
        if(te.getTypeId()!=null){
            te.setTypeDesc(BmsUtil.getTaskTypeDesc(te.getTypeId()));
        }
        te.setStatusDesc(BmsUtil.getTaskStatusDesc(te.getStatus()));
        return te;
    }
    /*
     * ????????????-post
     * */
    @PostMapping("/assignTask/{taskId}")
    protected TaskEntity assignTask(
            @PathVariable("taskId") String taskId,
            @RequestParam("dealerName") String dealerName
    ){
        String newDealerId = null;
        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        userWhereLogic.setNameFuzzySearch(dealerName);
        List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
        if (userV != null && userV.size() > 0) {
            UserEntity ue = userV.get(0);
            newDealerId = ue.getId();
        }
        if(StringUtils.isEmpty(newDealerId)) return null;

        TaskEntity ori_te = taskService.selectById(taskId);
        if(ori_te.getDealer().equals(newDealerId)) return null;
        TaskEntity new_te = new TaskEntity();
        new_te.setId(ori_te.getId());
        new_te.setDealer(newDealerId);
        taskService.updateById(new_te);
        TaskEntity curr_te = taskService.selectById(taskId);
        MsgToAfEntity mtae = new MsgToAfEntity();
        mtae.setModular("task");
        mtae.setModularId(curr_te.getId());
        mtae.setSourceId(curr_te.getPreSysId());
        mtae.setAction("assignTaskDealer");
        mtae.setMsg("?????????" + curr_te.getTitle() + "???????????????" + dealerName + "???????????????");
        mtae.setToDealerName(dealerName);
        String mqTopic = "BMS:" + curr_te.getPreSysFlag();
        BmsHelper.sendMsgToAf(rocketMQTemplate , mtae , mqTopic , userService);


        return curr_te;
    }
    /*
     * ????????????--????????????????????????-post
     * */
    @PostMapping("/kanban/taskCalendar/plan")
    protected TaskCalendarEntity newTaskCalenderEntityForPlan(
            @RequestBody TaskCalendarEntity taskCalendarEntity
    ){
        taskCalendarEntity.setPlanCreateTime(SystemClock.nowDateTime());
        taskCalendarEntity.setPlanInputer("system");

        if(taskCalendarEntity.getDealer()==null || StringUtils.isEmpty(taskCalendarEntity.getDealer())){//???????????????????????????????????????????????????
            TaskEntity taskEntity = taskService.selectById(taskCalendarEntity.getTaskId());
            taskCalendarEntity.setDealer(taskEntity.getDealer());
        }else{
            UserWhereLogic userWhereLogic = new UserWhereLogic();
            userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
            userWhereLogic.setNameFuzzySearch(taskCalendarEntity.getDealer());
            List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
            if (userV != null && userV.size() > 0) {
                UserEntity ue = userV.get(0);
                taskCalendarEntity.setDealer(ue.getId());
            }
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskCalendarEntity.getTaskId());
        taskEntity.setStatus(20);
        taskEntity.setDealer(taskCalendarEntity.getDealer());
        taskService.updateById(taskEntity);

        taskCalendarService.insert(taskCalendarEntity);

        return taskCalendarService.selectById(taskCalendarEntity.getId());
    }
    /*
     * ????????????--??????????????????-post
     * */
    @PostMapping("/kanban/taskCalendar/feedback")
    protected TaskCalendarEntity feedbackTaskCalender(
            @RequestBody TaskCalendarEntity taskCalendarEntity
    ){
        taskCalendarEntity.setFeedbackCreateTime(SystemClock.nowDateTime());
        taskCalendarEntity.setFeedbackInputer("system");
        TaskEntity curr_taskEntity = taskService.selectById(taskCalendarEntity.getTaskId());
        if(taskCalendarEntity.getFeedbackRate() > curr_taskEntity.getCurrentRate()){  //????????????????????????????????????????????????????????????????????????????????????
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(taskCalendarEntity.getTaskId());
            taskEntity.setCurrentRate(taskCalendarEntity.getFeedbackRate());
            taskService.updateById(taskEntity);
        }
        if(taskCalendarEntity.getFeedbackRate() == 100) {  //?????????100%??????????????????????????????????????????????????????????????????????????????????????????????????????3???????????????????????????????????????????????????????????????
            TaskEntity ori_taskEntity = taskService.selectById(taskCalendarEntity.getTaskId());
            if(ori_taskEntity.getStatus() == -1 ||ori_taskEntity.getStatus() == 10 || ori_taskEntity.getStatus() == 20 ) {
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setId(taskCalendarEntity.getTaskId());
                taskEntity.setActualFinishDate(SystemClock.nowLocalDate()); //???????????????????????????????????????
                taskEntity.setActualManHour(taskCalendarEntity.getCorrectTotalTaskManHour());
                taskEntity.setStatus(30);
                if(taskEntity.getActualManHour() > ori_taskEntity.getEstimateManHour() || taskEntity.getActualFinishDate().isAfter(ori_taskEntity.getExpectFinishDate())){ //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????leader??????
                    taskEntity.setExceptionFlag(1);
                }else{  //?????????????????????????????????????????????
                    taskEntity.setPerformManHour(taskEntity.getActualManHour());
                }
                taskService.updateById(taskEntity);

                MsgToAfEntity mtae = new MsgToAfEntity();
                mtae.setModular("task");
                mtae.setModularId(taskEntity.getId());
                mtae.setSourceId(ori_taskEntity.getPreSysId());
                mtae.setAction("feedbackFinish");
                mtae.setMsg("?????????" + ori_taskEntity.getTitle() + "???????????????????????????");
                mtae.setComment(taskCalendarEntity.getFeedbackComments());
                String mqTopic = "BMS:" + ori_taskEntity.getPreSysFlag();
                BmsHelper.sendMsgToAf(rocketMQTemplate , mtae , mqTopic , userService);

                if(ori_taskEntity.getRequireId()!=null){
                    requireService.reSetSingleRequireManHour(ori_taskEntity.getRequireId());
                }
            }
        }

        taskCalendarService.updateById(taskCalendarEntity);
        return taskCalendarService.selectById(taskCalendarEntity.getId());
    }
    /*
     * ?????????????????????Alpha???????????????-post
     * */
    @PostMapping("/kanban/taskFromAf")
    protected TaskEntity newTaskEntityFromAf(
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(taskEntity.getReqDataId())) {
            throw new Exception("???????????????????????????");
        }
        taskEntity.setPreSysId(taskEntity.getReqDataId());
        taskEntity.setPreSysTaskDataId(taskEntity.getTaskDataId());
        taskEntity.setPreSysTaskId(taskEntity.getTaskId());
        taskEntity.setProjectFlag(1);
        taskEntity.setPreSysFlag("af");
        taskEntity.setSourceFlag("pro");
        taskEntity.setStatus(-1);
        taskEntity.setId(taskEntity.getPreSysFlag() + "_" + taskEntity.getPreSysId());
        TaskEntity oriEntity = taskService.selectById(taskEntity.getId());
        if (oriEntity != null) {
            throw new Exception("?????????????????????" + oriEntity.getId());
        }

        if (taskEntity.getPriority() == null || taskEntity.getPriority() == 0) {
            taskEntity.setPriority(3);
        }
        if (!StringUtils.isEmpty(taskEntity.getDealer())) {
            //P|1063505,1063568^??????????????????--?????????
            String focusUser = taskEntity.getDealer();
            if (focusUser.indexOf("^") > 0) {
                focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
                if (focusUser.indexOf("-") > 0) {
                    focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
                }
            }
            //??????????????????focusUser???????????????
            //System.out.println("##############focusUser : " + focusUser);
            UserWhereLogic userWhereLogic = new UserWhereLogic();
            userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
            userWhereLogic.setNameFuzzySearch(focusUser);
            List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
            String focusUserId = "";
            if (userV != null && userV.size() > 0) {
                UserEntity ue = userV.get(0);
                focusUserId = ue.getId();
            }
            if (!focusUserId.equals("")) {
                taskEntity.setDealer(focusUserId);
            }
        }
        String extInfo = "";
        if (!StringUtils.isEmpty(taskEntity.getProjectManagerName())) {
            String focusManager = taskEntity.getProjectManagerName();
            if (focusManager.indexOf("^") > 0) {
                focusManager = focusManager.substring(focusManager.indexOf("^") + 1);
                if (focusManager.indexOf("-") > 0) {
                    focusManager = focusManager.substring(focusManager.lastIndexOf("-") + 1);
                }
            }
            //??????????????????focusManager???????????????
            UserWhereLogic userWhereLogic = new UserWhereLogic();
            userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
            userWhereLogic.setNameFuzzySearch(focusManager);
            List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
            String focusManagerId = "";
            if (userV != null && userV.size() > 0) {
                UserEntity ue = userV.get(0);
                focusManagerId = ue.getId();
            }
            if (!focusManagerId.equals("")) {
                taskEntity.setFollowuper(focusManagerId);
            }
            extInfo += "???????????????: " + focusManager + "\r\n";
        }
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformUrl()))
            extInfo += "??????????????????: " + taskEntity.getDevPlatformUrl() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformUserid()))
            extInfo += "?????????????????????: " + taskEntity.getDevPlatformUserid() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformPwd()))
            extInfo += "????????????: " + taskEntity.getDevPlatformPwd() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformDbIp()))
            extInfo += "???????????????: " + taskEntity.getDevPlatformDbIp() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformDbSid()))
            extInfo += "?????????SID: " + taskEntity.getDevPlatformDbSid() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevVersionLib())) extInfo += "???????????????: " + taskEntity.getDevVersionLib();
        if (!extInfo.equals("")) {
            taskEntity.setComments(extInfo);
//            if(!StringUtils.isEmpty(taskEntity.getDesc())){
//                taskEntity.setDesc(taskEntity.getDesc() + "\r\n" + extInfo);
//            }else{
//                taskEntity.setDesc(extInfo);
//            }
        }
        taskService.insert(taskEntity);

        //System.out.println("$$$$$$$$$$$$$$$$$taskEntity.getApiFileVForAf_str().getClass() : " + taskEntity.getApiFileVForAf_str().getClass());
        if (taskEntity.getApiFileVForAf_str().getClass() == java.util.ArrayList.class) {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<ApiFileEntityForAf> checkFileV = objectMapper.convertValue(taskEntity.getApiFileVForAf_str(), new TypeReference<ArrayList<ApiFileEntityForAf>>() {
            });
            taskEntity.setApiFileVForAf(checkFileV);
        }

        if (taskEntity.getApiFileVForAf() != null && taskEntity.getApiFileVForAf().size() > 0) {
            submitAsyncTask(() -> {
                try {
                    for (ApiFileEntityForAf afe : taskEntity.getApiFileVForAf()) {
                        Resource resp = restTemplate.getForObject(afe.getUrls(), Resource.class);
                        try (InputStream is = resp.getInputStream()) {
                            fileManager.newFile4WebService(
                                    "bmsTask", taskEntity.getId(),
                                    afe.getFileName() + "." + afe.getFileType(),
                                    is,
                                    null
                            );
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            });
        }
        LOGGER.info("?????????????????????" + taskEntity.getId() + " : " + taskEntity.getTitle());
        return taskService.selectById(taskEntity.getId());
    }

    /*
     * AF??????-????????????????????????
     * */
    @PostMapping("/kanban/finishTaskFromAf")
    protected TaskEntity finishTaskFromAf(
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(taskEntity.getReqDataId())) {
            throw new Exception("???????????????????????????");
        }
        TaskEntity focusEntity = new TaskEntity();
        focusEntity.setId("af_" + taskEntity.getReqDataId());
        focusEntity.setPmConfirmJudge(taskEntity.getPmConfirmJudge());
        focusEntity.setPmConfirmDesc(taskEntity.getPmConfirmDesc());
        focusEntity.setPmConfirmInputer(taskEntity.getPmConfirmInputer());
        focusEntity.setPmConfirmCreateTime(SystemClock.nowDateTime());
        focusEntity.setStatus(40);
        taskService.updateById(focusEntity);

        TaskEntity currEntity = taskService.selectById(focusEntity.getId());
        LOGGER.info("??????????????????" + currEntity.getId() + " : " + currEntity.getTitle());
        return currEntity;
    }

    /*
     * ????????????-put
     * */
    @PutMapping("/kanban/task/{preSysFlag}/{preSysId}")
    protected TaskEntity updateTaskEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("????????????????????????????????????");
        }
        taskEntity.setId(preSysFlag + "_" + preSysId);
        taskEntity.setPreSysFlag(preSysFlag);
        taskEntity.setPreSysId(preSysId);
        boolean update_success_flag = taskService.updateById(taskEntity);
        if (!update_success_flag) throw new Exception("?????????????????????????????????????????????");

        return taskService.selectById(taskEntity.getId());
    }

    /*
     * ??????????????????-patch
     * */
    @PatchMapping("/kanban/task/{preSysFlag}/{preSysId}/status")
    protected void patchTaskEntityStatus(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestParam("status") Integer status
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("????????????????????????????????????");
        }
        boolean update_success_flag = taskService.updateTaskStatus(preSysFlag + "_" + preSysId, status);
        if (!update_success_flag) throw new Exception("?????????????????????????????????????????????");
    }

    /*
     * ????????????-patch
     * */
    @PatchMapping("/kanban/task/{preSysFlag}/{preSysId}/remove")
    protected void removeTaskEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("????????????????????????????????????");
        }
        boolean update_success_flag = taskService.changeTaskValid(preSysFlag + "_" + preSysId, false);
        if (!update_success_flag) throw new Exception("?????????????????????????????????????????????");
    }

    private final String[] defaultOrder = {"desc"};
    private final String[] defaultSort = {"order_seq_"};

    private final String[] defaultOrderForModDate = {"desc", "desc"};
    private final String[] defaultSortForModDate = {"mod_date_", "order_seq_"};

    /*
     * ??????????????????-get
     * */
    @GetMapping("/project/search")
    protected BasicDataGridRows searchProject(ProjectWhereLogic whereLogic, @RequestParam("user") String user) {
        //P|0,1063568^??????????????????-?????????
        //P|0,1277169871100821506^????????????-?????????
        //System.out.println("##############serach user : " + user);
        String focusUser = user;
        //focusUser = "P|0,1063568^??????????????????-?????????";
        if (focusUser.indexOf("^") > 0) {
            focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
            if (focusUser.indexOf("-") > 0) {
                focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
            }
        }
        //??????????????????focusUser???????????????
        //System.out.println("##############focusUser : " + focusUser);

        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        userWhereLogic.setNameFuzzySearch(focusUser);
        List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
        String focusUserId = "";
        boolean canViewAllProject = false;
        if (userV != null && userV.size() > 0) {
            UserEntity ue = userV.get(0);
            focusUserId = ue.getId();
            RuntimeUserDetails rud = aggregateUserDetailsService.loadUserByUserId(ue.getId());
            if (rud.isAdmin()) {
                canViewAllProject = true;
            } else {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                        SecurityUtils.joinRoleAuthorizeStr4SpringSecurity("bms.project_bms.project.viewAllProject")
                );
                if (rud.getAuthorities().contains(authority)) {
                    canViewAllProject = true;
                }
            }
        }
        //System.out.println("##############focusUserId : " + focusUserId);
        Collection<String> s = new ArrayList<>();
        if (canViewAllProject) {
            s.add("ALL");
        } else {
            s.add(focusUserId);
        }
        whereLogic.setProjectSelectAuthExpress(s);
        if (ArrayUtils.isEmpty(whereLogic.getSort())) {
            whereLogic.setSort(defaultSortForModDate);
        }
        if (ArrayUtils.isEmpty(whereLogic.getOrder())) {
            whereLogic.setOrder(defaultOrderForModDate);
        }
        if (whereLogic.getPageNum() != null && whereLogic.getPageNum() > 0) whereLogic.setRows(whereLogic.getPageNum());
        if (whereLogic.getPage() == 0) whereLogic.setPage(1);
        if (whereLogic.getRows() == 0) whereLogic.setRows(500);
        whereLogic.setStatusTarget(true);
        whereLogic.setActiveFlag(true);

        BasicDataGridRows bdgr = BasicDataGridRows.create()
                .withRows(bmsProjectBriefService.selectListByWhereLogic(whereLogic, whereLogic.parsePagination()))
                .withTotal(bmsProjectBriefService.selectCountByWhereLogic(whereLogic));

        return bdgr;
    }

    /*
     * ??????????????????(????????????)-get
     * */
    @GetMapping("/project/searchForFinSettle")
    protected BasicDataGridRows searchProjectForFinSettle(ProjectWhereLogic whereLogic, @RequestParam("user") String user) {
        //P|0,1063568^??????????????????-?????????
        //P|0,1277169871100821506^????????????-?????????
        //System.out.println("##############serach user : " + user);
        String focusUser = user;
        //focusUser = "P|0,1063568^??????????????????-?????????";
        if (focusUser.indexOf("^") > 0) {
            focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
            if (focusUser.indexOf("-") > 0) {
                focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
            }
        }
        //??????????????????focusUser???????????????
        //System.out.println("##############focusUser : " + focusUser);

        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        userWhereLogic.setNameFuzzySearch(focusUser);
        List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
        String focusUserId = "";
        boolean canViewAllProject = false;
        if (userV != null && userV.size() > 0) {
            UserEntity ue = userV.get(0);
            focusUserId = ue.getId();
            RuntimeUserDetails rud = aggregateUserDetailsService.loadUserByUserId(ue.getId());
            if (rud.isAdmin()) {
                canViewAllProject = true;
            } else {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                        SecurityUtils.joinRoleAuthorizeStr4SpringSecurity("bms.project_bms.project.viewAllProject")
                );
                if (rud.getAuthorities().contains(authority)) {
                    canViewAllProject = true;
                }
            }
        }
        //System.out.println("##############focusUserId : " + focusUserId);
        Collection<String> s = new ArrayList<>();
        if (canViewAllProject) {
            s.add("ALL");
        } else {
            s.add(focusUserId);
        }
        whereLogic.setProjectSelectAuthExpress(s);
        if (ArrayUtils.isEmpty(whereLogic.getSort())) {
            whereLogic.setSort(defaultSortForModDate);
        }
        if (ArrayUtils.isEmpty(whereLogic.getOrder())) {
            whereLogic.setOrder(defaultOrderForModDate);
        }
        if (whereLogic.getPageNum() != null && whereLogic.getPageNum() > 0) whereLogic.setRows(whereLogic.getPageNum());
        if (whereLogic.getPage() == 0) whereLogic.setPage(1);
        if (whereLogic.getRows() == 0) whereLogic.setRows(500);
        whereLogic.setStatusTarget(true);
        whereLogic.setActiveFlag(true);

        BasicDataGridRows bdgr = BasicDataGridRows.create()
                .withRows(bmsProjectForFinSettleService.selectListByWhereLogic(whereLogic, whereLogic.parsePagination()))
                .withTotal(bmsProjectForFinSettleService.selectCountByWhereLogic(whereLogic));

        return bdgr;
    }

    /*
     * ?????????????????????Alpha???????????????-post
     * */
    @PostMapping("/callAfTest")
    protected AfDriveTaskResponseDTO callAfTest(@RequestParam("reqDataId") String reqDataId, @RequestParam("taskDataId") String taskDataId, @RequestParam("taskId") String taskId) throws Exception {
        //String access_token = BmsHelper.getAfAccessToken(restTemplate);
        //System.out.println("afAuthMaster.fetchAccessToken():" + afAuthMaster.fetchAccessToken());
        //System.out.println("access_token:" + access_token);
        //BmsHelper.driverTaskForAf(restTemplate , access_token , BmsHelper.afPeterAccountId ,reqDataId, taskDataId,taskId , fileManager);

        String fileHeaderId = "1310856707314380802";
        FileHeaderEntity fileHeaderEntity = fileManager.selectFileHeaderById(fileHeaderId);
        Assert.notNull(fileHeaderEntity, "no file header,id:" + fileHeaderId);
        FileBodyEntity fbe = fileHeaderEntity.getFileBody();
        long fileSize = fbe.getSize();
        String fileType = fbe.getType();
        String fileName = fileHeaderEntity.getName();
        String fileId = RandomStringUtils.randomAlphanumeric(8);

        HashMap<String, String> afFileIdToFileHeaderIdMap = new HashMap<>();//file_header_id???8?????????????????????map???????????????file_header_id???afFileId?????????
        afFileIdToFileHeaderIdMap.put(fileId, fileHeaderId);

        //String filePath = fileManager.prepareAndGetFilePath(fileHeaderEntity.getFileBody());
        //FileDownloadView dv = new FileDownloadView(fileAuditService,,fileHeaderEntity,filePath);

        ObjectNode reqItemJsonObj = new ObjectMapper().createObjectNode();
        reqItemJsonObj.put("sjwcsj", "2020-06-30");
        reqItemJsonObj.put("jbgcsfk", "????????????");

        ObjectNode reqFileParamObj = new ObjectMapper().createObjectNode();
        ArrayNode attachmentItemArray = new ObjectMapper().createArrayNode();
        ObjectNode singleAttachmentObj = new ObjectMapper().createObjectNode();
        singleAttachmentObj.put("fileSize", fileSize);
        singleAttachmentObj.put("fileId", fileId);
        singleAttachmentObj.put("fileType", fileType);
        singleAttachmentObj.put("fileName", fileName);
        attachmentItemArray.add(singleAttachmentObj);
        reqFileParamObj.set("jbgcsfkwd", attachmentItemArray);

        AfDriveTaskResponseDTO driveTaskDTO = afWfMaster.driveTask(BmsHelper.afTenantId, BmsHelper.afPeterAccountId, reqDataId, taskDataId, taskId, reqItemJsonObj, reqFileParamObj);

        if (driveTaskDTO.getModel() != null) {
            AfDriveTaskModel afdtm = driveTaskDTO.getModel();
            if (afdtm.getFileMap() != null && afdtm.getFileMap().getClass() == LinkedHashMap.class) {
                LinkedHashMap<String, String> fm = (LinkedHashMap<String, String>) afdtm.getFileMap();
                for (Map.Entry<String, String> entry : fm.entrySet()) {
                    //System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
                    String randomFileField = entry.getKey();//8????????????
                    String fileIdForUpload = entry.getValue();//af?????????fileId
                    if (afFileIdToFileHeaderIdMap.containsKey(randomFileField)) {
                        String checkFileHeaderId = afFileIdToFileHeaderIdMap.get(randomFileField);
                        FileHeaderEntity checkFileHeaderEntity = fileManager.selectFileHeaderById(checkFileHeaderId);
                        FileSystemResourceWithNewFileName fsr = new FileSystemResourceWithNewFileName(fileManager.prepareAndGetFilePath(checkFileHeaderEntity.getFileBody()));
                        //fsr.setFileName(fileName);
                        afWfMaster.uploadFile(BmsHelper.afTenantId, BmsHelper.afPeterAccountId, fileIdForUpload, fsr);
                    }

                }

            }
        }

        return driveTaskDTO;
    }

    /*
     * ??????????????????-get
     * */
    @GetMapping("/customer/search")
    protected BasicDataGridRows searchCustomer(BaWhereLogic whereLogic, @RequestParam("user") String user) {
        //P|0,1063568^??????????????????-?????????
        //P|0,1277169871100821506^????????????-?????????
        //System.out.println("##############serach customer user : " + user);
        String focusUser = user;
        //focusUser = "P|0,1063568^??????????????????-?????????";
        if (focusUser.indexOf("^") > 0) {
            focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
            if (focusUser.indexOf("-") > 0) {
                focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
            }
        }
        //??????????????????focusUser???????????????
        //System.out.println("##############focusUser : " + focusUser);
        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        userWhereLogic.setNameFuzzySearch(focusUser);
        List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
        String focusUserId = "";
        if (userV != null && userV.size() > 0) {
            UserEntity ue = userV.get(0);
            focusUserId = ue.getId();
        }
        //System.out.println("##############focusUserId : " + focusUserId);
        Collection<String> s = new ArrayList<>();
        s.add(focusUserId);
        whereLogic.setBaSelectAuthExpress(s);
        whereLogic.setAuthPart(""); //????????????????????????????????????????????????3?????????
        if (ArrayUtils.isEmpty(whereLogic.getSort())) {
            whereLogic.setSort(defaultSortForModDate);
        }
        if (ArrayUtils.isEmpty(whereLogic.getOrder())) {
            whereLogic.setOrder(defaultOrderForModDate);
        }
        if (whereLogic.getPageNum() != null && whereLogic.getPageNum() > 0) whereLogic.setRows(whereLogic.getPageNum());
        if (whereLogic.getPage() == 0) whereLogic.setPage(1);
        if (whereLogic.getRows() == 0) whereLogic.setRows(500);
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(bmsBaBriefService.selectListByWhereLogic(whereLogic, whereLogic.parsePagination()))
                .withTotal(bmsBaBriefService.selectCountByWhereLogic(whereLogic));
    }

    //    /*
//     * ????????????-post
//     * */
//    @PostMapping("/sales/analysisFile")
//    protected SalesContractEntity analysisFile(
//            @RequestBody SalesContractEntity salesContractEntity
//    ) throws Exception {
//        SalesContractEntity returnSce = null;
//        if(salesContractEntity.getApiFileVForAf_str().getClass() == java.util.ArrayList.class){
//            ObjectMapper objectMapper = new ObjectMapper();
//            ArrayList<ApiFileEntityForAf> checkFileV = objectMapper.convertValue(salesContractEntity.getApiFileVForAf_str(), new TypeReference< ArrayList<ApiFileEntityForAf>>() {});
//            salesContractEntity.setApiFileVForAf(checkFileV);
//        }
//        if(salesContractEntity.getApiFileVForAf()!=null&&salesContractEntity.getApiFileVForAf().size()>0){
//                try{
//                    for(ApiFileEntityForAf afe : salesContractEntity.getApiFileVForAf()){
//                        Resource resp = restTemplate.getForObject(afe.getUrls(), Resource.class);
//                        LOGGER.debug("resp.getInputStream():" + resp.getInputStream());
//                        returnSce = bmsBaService.analysisFile(resp.getInputStream());
//                    }
//                }catch(Exception e){
//                    LOGGER.error(e.getMessage(),e);
//                }
//        }
//        return returnSce;
//    }
    /*
     * ????????????????????????-post
     * */
    @PostMapping("/dataJournal")
    protected BasicTokenStoreEntity newBmsDataJournalEntity(
            @RequestBody BmsDataJournalEntity bmsDataJournalEntity
    ) throws Exception {
        BasicTokenStoreEntity rsEntity = new BasicTokenStoreEntity();
        submitAsyncTask(() -> {
            try {
                //reqDataId??????ID??????sourceId
                bmsDataJournalEntity.setSourceId(bmsDataJournalEntity.getReqDataId());
                if (bmsDataJournalEntity.getReqDataId() == null) {
                    bmsDataJournalEntity.setId("test_" + IdWorker.getIdStr());
                } else {
                    bmsDataJournalEntity.setId("af_" + bmsDataJournalEntity.getReqDataId());
                }
                //createUser?????????P|1063505,1063568^??????????????????--???????????????????????????
                String createUserId = BmsHelper.getUserIdByAfUserStr(userService, bmsDataJournalEntity.getCreateUser());
                String createUserName = BmsHelper.getUserNameByAfUserStr(bmsDataJournalEntity.getCreateUser());
                bmsDataJournalEntity.setCreateUser(createUserId);
                bmsDataJournalEntity.setModUser(createUserId);
                //modular\sourceFlag
                bmsDataJournalEntity.setSourceFlag("af");
                bmsDataJournalEntity.setModular("ba");

                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataNumberStr()) && NumberUtils.isParsable(bmsDataJournalEntity.getDataNumberStr())) {
                    bmsDataJournalEntity.setDataNumber(Double.parseDouble(bmsDataJournalEntity.getDataNumberStr()));
                }

                if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {  //modularInnerId2???modularDesc2???????????????????????????????????????????????????????????????????????????????????????modularInnerId1???modularDesc1?????????????????????????????????
                    bmsDataJournalEntity.setModular("project");
                    bmsDataJournalEntity.setModularInnerId(bmsDataJournalEntity.getModularInnerId2());
                    bmsDataJournalEntity.setModularDesc(bmsDataJournalEntity.getModularDesc2());
                } else if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId1())) {
                    bmsDataJournalEntity.setModular("ba");
                    bmsDataJournalEntity.setModularInnerId(bmsDataJournalEntity.getModularInnerId1());
                    bmsDataJournalEntity.setModularDesc(bmsDataJournalEntity.getModularDesc1());
                }

                int funcFlag = bmsDataJournalEntity.getFuncFlag();
                if (funcFlag == 21) {   //Call-in??????????????????
                    String valueCode = bmsDataJournalEntity.getDataText3();
                    String productDirection = bmsDataJournalEntity.getDataText4();
                    bmsBaService.changeBaInfoForAfCallin(bmsDataJournalEntity.getModularInnerId1() , valueCode , productDirection , createUserId);

                    BmsBaEventEntity bbee = new BmsBaEventEntity();
                    bbee.setBaId(bmsDataJournalEntity.getModularInnerId());
                    bbee.setTypeId("callin");//Call-in??????
                    LocalDateTime actionDate = SystemClock.nowDateTime();
                    try {
                        if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText())) {
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            actionDate = LocalDateTime.parse(bmsDataJournalEntity.getDataText()+" 00:00", df);
                        }
                    } catch (Exception t_e) {
                        LOGGER.error("error format time : " + t_e.getMessage());
                    }
                    bbee.setActionDate(actionDate);
                    bbee.setContactPerson("Call-in??????");
                    bbee.setActionUser(createUserName);
                    String subjectStr = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1())) {
                        subjectStr += "??????????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText1() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2())) {
                        subjectStr += "???????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText2() + "\n";
                    }
                    bbee.setSubject(subjectStr);
                    String nextPlan = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText5())) {
                        nextPlan = bmsDataJournalEntity.getDataText5();
                    }
                    bbee.setNextPlan(nextPlan);
                    bbee.setDataJournalId(bmsDataJournalEntity.getId());
                    bmsBaEventService.insert(bbee);
                } else if (funcFlag == 22) {   //????????????????????????
                } else if (funcFlag == 23) {   //????????????????????????
                } else if (funcFlag == 24) {   //?????????????????????????????????
                } else if (funcFlag == 46) {   //????????????????????????
                    BmsBaEventEntity bbee = new BmsBaEventEntity();
                    bbee.setBaId(bmsDataJournalEntity.getModularInnerId());
                    bbee.setTypeId("2748");//????????????
                    LocalDateTime actionDate = SystemClock.nowDateTime();
                    try {
                        if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText4())) {
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            actionDate = LocalDateTime.parse(bmsDataJournalEntity.getDataText4(), df);
                        }
                    } catch (Exception t_e) {
                        LOGGER.error("error format time : " + t_e.getMessage());
                    }
                    bbee.setActionDate(actionDate);
                    bbee.setContactPerson(bmsDataJournalEntity.getDataText());
                    bbee.setActionUser(createUserName);
                    String subjectStr = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1())) {
                        subjectStr += "???????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText1() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2())) {
                        subjectStr += "???????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText2() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText3())) {
                        subjectStr += "???????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText3() + "\n";
                    }
                    bbee.setSubject(subjectStr);
                    String nextPlan = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText5())) {
                        nextPlan = bmsDataJournalEntity.getDataText5();
                    }
                    bbee.setNextPlan(nextPlan);
                    bbee.setDataJournalId(bmsDataJournalEntity.getId());
                    bmsBaEventService.insert(bbee);
                } else if (funcFlag == 25) {   //????????????????????????
                } else if (funcFlag == 26) {   //?????????????????????????????????
                } else if (funcFlag == 27) {   //????????????????????????
                } else if (funcFlag == 28) {   //??????????????????
                } else if (funcFlag == 29) {   //?????????????????????
                } else if (funcFlag == 30) {   //???????????????????????????
                } else if (funcFlag == 31) {   //??????????????????
                } else if (funcFlag == 32) {   //??????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(33);
                    }
                } else if (funcFlag == 34) {   //??????????????????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(55);
                    }
                } else if (funcFlag == 35) {   //??????????????????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(36);
                    }
                } else if (funcFlag == 45) {   //???????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(37);
                    }
                } else if (funcFlag == 38) {   //??????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(39);
                    }
                } else if (funcFlag == 40) {   //???????????????
                } else if (funcFlag == 41) {   //??????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(42);
                    }
                } else if (funcFlag == 43) {   //??????????????????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(44);
                    }
                } else if (funcFlag == 48) {   //????????????????????????
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(51);
                    }
                } else if (funcFlag == 52) {   //?????????????????????
                } else if (funcFlag == 53) {   //????????????????????????
                } else if (funcFlag == 54) {   //AF????????????????????????
                    BmsBaEventEntity bbee = new BmsBaEventEntity();
                    bbee.setBaId(bmsDataJournalEntity.getModularInnerId());
                    bbee.setTypeId("2750");//????????????
                    LocalDateTime actionDate = SystemClock.nowDateTime();
                    bbee.setActionDate(actionDate);
                    bbee.setContactPerson("[AF????????????]");
                    bbee.setActionUser(createUserName);
                    String subjectStr = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1())) {
                        subjectStr += "??????????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText1() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2())) {
                        subjectStr += "???????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText2() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText3())) {
                        subjectStr += "???????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText3() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText4())) {
                        subjectStr += "?????????????????????????????????\n";
                        subjectStr += bmsDataJournalEntity.getDataText4() + "\n";
                    }
                    bbee.setSubject(subjectStr);
                    String nextPlan = "";
                    bbee.setNextPlan(nextPlan);
                    bbee.setDataJournalId(bmsDataJournalEntity.getId());
                    bmsBaEventService.insert(bbee);
                }
                String dataText = StringUtils.defaultString(bmsDataJournalEntity.getDataText(), "");
                if (!StringUtils.isEmpty(dataText)) dataText += "\n";
                //??????dataText1-4??????dataText
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1()))
                    dataText += bmsDataJournalEntity.getDataText1() + "\n";  //???????????????
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2()))
                    dataText += bmsDataJournalEntity.getDataText2() + "\n";  //????????????????????????????????????
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText3()))
                    dataText += bmsDataJournalEntity.getDataText3() + "\n";  //??????????????????
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText4()))
                    dataText += bmsDataJournalEntity.getDataText4() + "\n";  //????????????????????????????????????
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText5()))
                    dataText += bmsDataJournalEntity.getDataText5();
                bmsDataJournalEntity.setDataText(dataText);
                //?????????????????????????????????????????????????????????fialRelatedProjectId????????????????????????ID
                if (bmsDataJournalEntity.getModular().equals("project") && BmsHelper.isDataJournalAboutFin(bmsDataJournalEntity.getFuncFlag())) {
                    bmsDataJournalEntity.setFinalRelatedProjectId(bmsDataJournalEntity.getModularInnerId());
                }
                if (bmsDataJournalEntity.getForbidFillExtContent() != null && bmsDataJournalEntity.getForbidFillExtContent() == 1) {
                    //??????????????????????????????
                } else {
                    try {
                        AfWorkflowInstModel awim = afWfMaster.getWorkflowInstanceData(Long.parseLong(bmsDataJournalEntity.getReqDataId()));
                        //Map<String, AfInstanceFormItemValueModel> formData = afWfMaster.getInstanceData(Long.parseLong(bmsDataJournalEntity.getReqDataId())).getItemValues();
                        bmsDataJournalEntity.setExtContent(jsonPrintHelper.printQuite(awim));
                    } catch (Exception e1) {
                        LOGGER.error("?????????????????????????????????" + bmsDataJournalEntity.getReqDataId() + "??????" + e1.getMessage());
                    }
                }
                dataJournalService.insert(bmsDataJournalEntity);
                BmsDataJournalEntity updatedEntity = dataJournalService.selectById(bmsDataJournalEntity.getId());

                LOGGER.info("?????????????????????????????????" + objMapper.writeValueAsString(updatedEntity));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        rsEntity.setToken("1");
        return rsEntity;
    }

    /*
     * ?????????????????????????????????AF?????????????????????????????????-post
     * */
    @PostMapping("/fillDataJournalExtContent")
    protected void fillDataJournalExtContent(
            @RequestBody BmsDataJournalEntity reqEntity
    ) throws Exception {
        submitAsyncTask(() -> {
            try {
                String checkId = "af_" + reqEntity.getReqDataId();
                BmsDataJournalEntity checkEntity = dataJournalService.selectById(checkId);
                if (checkEntity != null) { //????????????????????????
                    AfWorkflowInstModel awim = afWfMaster.getWorkflowInstanceData(Long.parseLong(reqEntity.getReqDataId()));
                    String newExtContent = jsonPrintHelper.printQuite(awim);
                    dataJournalService.changeExtContent(checkId, newExtContent);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                LOGGER.info("?????????????????????????????????" + objectMapper.writeValueAsString(checkId));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /*
     * ????????????????????????-get
     * */
    @GetMapping("/taskCalendar/search")
    protected BasicDataGridRows searchTaskCalendarList(
            TaskCalendarWhereLogic whereLogic, @RequestParam(value = "dealer_str", required = false) String dealer_str
    ) {
        String[] dealerNameList = dealer_str.split(",");
        Collection<String> dealerIdList = new ArrayList<>();
        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        for (String checkName : dealerNameList) {
            userWhereLogic.setNameFuzzySearch(checkName);
            List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
            if (userV != null && userV.size() > 0) {
                UserEntity ue = userV.get(0);
                dealerIdList.add(ue.getId());
            }
        }
        if (dealerIdList.size() > 0) {
            whereLogic.setDealers(dealerIdList);
        }
        whereLogic.setPage(1);
        whereLogic.setRows(1000);
        whereLogic.setStatusTarget(true);
        if (ArrayUtils.isEmpty(whereLogic.getSort())) {
            whereLogic.setSort(defaultSort);
        }
        if (ArrayUtils.isEmpty(whereLogic.getOrder())) {
            whereLogic.setOrder(defaultOrder);
        }
        List<TaskCalendarEntity> tceList = taskCalendarService.selectListByWhereLogic(whereLogic);
        for (TaskCalendarEntity tce : tceList) {
            TaskEntity te = taskService.selectById(tce.getTaskId());
            if (te != null) {
                tce.setTaskName(te.getTitle());
            }
        }
        return BasicDataGridRows.create()
                .withRows(tceList);

    }

    /*
     * ???????????? ??????????????????-post
     * */
    @PostMapping("/eddAttendanceApproveFinish")
    protected void eddAttendanceApproveFinish(
            @RequestBody OapiAttendanceApproveFinishRequest req
    ) throws Exception {
        //createUser?????????P|1063505,1063568^??????????????????--???????????????????????????
        if (!StringUtils.isEmpty(req.getUserid())) {
            String createUserId = BmsHelper.getUserIdByAfUserStr(userService, req.getUserid());
            req.setUserid(createUserId);
        }
        if (req.getCalculateModel() == null) {
            req.setCalculateModel(1L);
        }
        if (req.getOvertimeToMore() == null) {
            req.setOvertimeToMore(1L);
        }
        submitAsyncTask(() -> {
            try {
                OapiAttendanceApproveFinishResponse.TopDurationVo tdv = eddForBmsMaster.attendanceApproveFinish(
                        req.getUserid(),
                        req.getBizType(),
                        req.getFromTime(),
                        req.getToTime(),
                        req.getCalculateModel(),
                        req.getDurationUnit(),
                        req.getTagName(),
                        req.getSubType(),
                        req.getOvertimeDuration(),
                        req.getOvertimeToMore()
                );
                ObjectMapper objectMapper = new ObjectMapper();
                LOGGER.info("?????????????????????" + req.getUserid() + "%%" + objectMapper.writeValueAsString(tdv));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /*
     * ???????????? ?????????????????? ???????????????-post
     * */
    @PostMapping("/eddAttendanceApproveFinishTimeBatch")
    protected void eddAttendanceApproveFinishTimeBatch(
            @RequestBody OapiAttendanceApproveFinishBatchEntity req
    ) throws Exception {
        if (req.getCalculateModel() == null) {
            req.setCalculateModel(1L);
        }
        if (req.getOvertimeToMore() == null) {
            req.setOvertimeToMore(1L);
        }
        submitAsyncTask(() -> {
            try {
                //createUser?????????P|0,1063568^??????????????????-???????????????????????????
                if (req.getTimeStr() != null && req.getTimeStr().size() > 0) {
                    for (LinkedHashMap<String, String> checkTimeNode : req.getTimeStr()) {
                        OapiAttendanceApproveFinishRequest oaafr = new OapiAttendanceApproveFinishRequest();
                        oaafr.setBizType(req.getBizType());
                        oaafr.setCalculateModel(req.getCalculateModel());
                        oaafr.setDurationUnit(req.getDurationUnit());
                        oaafr.setTagName(req.getTagName());
                        oaafr.setSubType(req.getSubType());
                        oaafr.setOvertimeDuration(req.getOvertimeDuration());
                        oaafr.setOvertimeToMore(req.getOvertimeToMore());

                        String nodeCreateUserId = BmsHelper.getUserIdByAfUserStr(userService, req.getUserid());
                        oaafr.setUserid(nodeCreateUserId);

                        oaafr.setFromTime(checkTimeNode.get("fromTime"));
                        oaafr.setToTime(checkTimeNode.get("toTime"));
                        OapiAttendanceApproveFinishResponse.TopDurationVo tdv = eddForBmsMaster.attendanceApproveFinish(
                                oaafr.getUserid(),
                                oaafr.getBizType(),
                                oaafr.getFromTime(),
                                oaafr.getToTime(),
                                oaafr.getCalculateModel(),
                                oaafr.getDurationUnit(),
                                oaafr.getTagName(),
                                oaafr.getSubType(),
                                oaafr.getOvertimeDuration(),
                                oaafr.getOvertimeToMore()
                        );
                        ObjectMapper objectMapper = new ObjectMapper();
                        LOGGER.info("?????????????????????" + oaafr.getUserid() + "%%" + objectMapper.writeValueAsString(tdv));
                    }
                }
                LOGGER.info("???????????????????????????" + req.getUserid() + "%%");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

    }

    /*
     * ???????????? ?????????????????? ????????????-post
     * */
    @PostMapping("/eddAttendanceApproveFinishUserBatch")
    protected void eddAttendanceApproveFinishUserBatch(
            @RequestBody OapiAttendanceApproveFinishBatchEntity req
    ) throws Exception {

        submitAsyncTask(() -> {
            try {
                if (req.getCalculateModel() == null) {
                    req.setCalculateModel(1L);
                }
                if (req.getOvertimeToMore() == null) {
                    req.setOvertimeToMore(1L);
                }
                //createUser?????????P|0,1063568^??????????????????-?????????;P|1198814198936625154,1198814200099663874^????????????-???????????????????????????
                ArrayList<String> userIdList = new ArrayList<>();
                if (!StringUtils.isEmpty(req.getUserid())) {
                    if ((req.getUserid()).indexOf(";") > 0) {  //???????????????
                        userIdList = new ArrayList<String>(Arrays.asList((req.getUserid()).split("\\;")));
                    } else {
                        userIdList.add(req.getUserid());
                    }
                }
                if ("????????????".equals(req.getApplyType())) {
                    for (String checkUserId : userIdList) {
                        OapiAttendanceApproveFinishRequest oaafr = new OapiAttendanceApproveFinishRequest();
                        oaafr.setBizType(req.getBizType());
                        oaafr.setCalculateModel(req.getCalculateModel());
                        oaafr.setDurationUnit(req.getDurationUnit());
                        oaafr.setTagName(req.getTagName());
                        oaafr.setSubType(req.getSubType());
                        oaafr.setOvertimeDuration(req.getOvertimeDuration());
                        oaafr.setOvertimeToMore(req.getOvertimeToMore());

                        String nodeCreateUserId = BmsHelper.getUserIdByAfUserStr(userService, checkUserId);
                        oaafr.setUserid(nodeCreateUserId);

                        oaafr.setFromTime(req.getFromTime());
                        oaafr.setToTime(req.getToTime());
                        OapiAttendanceApproveFinishResponse.TopDurationVo tdv = eddForBmsMaster.attendanceApproveFinish(
                                oaafr.getUserid(),
                                oaafr.getBizType(),
                                oaafr.getFromTime(),
                                oaafr.getToTime(),
                                oaafr.getCalculateModel(),
                                oaafr.getDurationUnit(),
                                oaafr.getTagName(),
                                oaafr.getSubType(),
                                oaafr.getOvertimeDuration(),
                                oaafr.getOvertimeToMore()
                        );
                        ObjectMapper objectMapper = new ObjectMapper();
                        LOGGER.info("?????????????????????" + oaafr.getUserid() + "%%" + objectMapper.writeValueAsString(tdv));
                    }
                } else if ("????????????".equals(req.getApplyType()) || "????????????".equals(req.getApplyType())) {

                    String checkFromTime = req.getFromTime();
                    if (checkFromTime.length() > 10) {
                        checkFromTime = checkFromTime.substring(0, 10);
                    }
                    String checkWorkDateStr = checkFromTime;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//???????????????MM
                    Long fromDateLong = (simpleDateFormat.parse(checkFromTime + " 08:00:00")).getTime();
                    Long toDateLong = (simpleDateFormat.parse(checkFromTime + " 18:00:00")).getTime();
                    for (String checkUserId : userIdList) {
                        Long attendancePunchId = 0L;
                        Date attendancePunchPlanCheckTime = null;
                        Date userCheckTime = null;

                        String nodeCreateUserId = BmsHelper.getUserIdByAfUserStr(userService, checkUserId);

                        List<OapiAttendanceScheduleListbyusersResponse.TopScheduleVo> attendanceScheduleList = eddForBmsMaster.getAttendanceScheduleList(nodeCreateUserId, nodeCreateUserId, fromDateLong, toDateLong);
                        for (OapiAttendanceScheduleListbyusersResponse.TopScheduleVo tsv : attendanceScheduleList) {
                            if ("????????????".equals(req.getApplyType()) && "OnDuty".equals(tsv.getCheckType())) {
                                attendancePunchId = tsv.getId();
                                attendancePunchPlanCheckTime = tsv.getPlanCheckTime();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(attendancePunchPlanCheckTime);
                                cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE) - 1));
                                userCheckTime = cal.getTime();
                                break;
                            } else if ("????????????".equals(req.getApplyType()) && "OffDuty".equals(tsv.getCheckType())) {
                                attendancePunchId = tsv.getId();
                                attendancePunchPlanCheckTime = tsv.getPlanCheckTime();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(attendancePunchPlanCheckTime);
                                cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE) + 1));
                                userCheckTime = cal.getTime();
                                break;
                            }
                        }

                        /*
                            String userId ,
                            String workDate ,
                            Long punchId ,
                            String punchCheckTime ,
                            String userCheckTime
                         */
//                        String userCheckTime = "";
//                        if("????????????".equals(req.getApplyType())){
//                            userCheckTime = checkWorkDateStr + " 08:00:00";
//                        }else if("????????????".equals(req.getApplyType())){
//                            userCheckTime = checkWorkDateStr + " 18:00:00";
//                        }
                        OapiAttendanceApproveCheckResponse aacr = eddForBmsMaster.attendanceApproveCheck(
                                nodeCreateUserId,
                                checkWorkDateStr,
                                attendancePunchId,
                                simpleDateFormat.format(attendancePunchPlanCheckTime),
                                simpleDateFormat.format(userCheckTime)
                        );
                    }
                }
                LOGGER.info("???????????????????????????" + req.getUserid() + "%%");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

    }

    /*
     * ???????????????????????????-post
     * */
    @PostMapping("/sendProjectDailyToDingGroup")
    protected void sendProjectDailyToDingGroup(
            @RequestBody ProjectDailyEntity projectDailyEntity
    ) throws Exception {
        submitAsyncTask(() -> {
            try {

                BmsProjectEntity bmsProjectEntity = bmsProjectService.selectById(projectDailyEntity.getProjectId());
                if (bmsProjectEntity == null || StringUtils.isEmpty(bmsProjectEntity.getDingGroupSecret()) || StringUtils.isEmpty(bmsProjectEntity.getDingGroupAccessToken())) {
                    LOGGER.info(projectDailyEntity.getProjectName() + "?????????????????????????????????????????????");
                    return;
                }

                //String chatAccessToken = "218a4936d0ee947ba0225791750cc9164c9b7c4a27174a6ac4370cfa39259047";
                //String groupSecret = "SECd05c7c7932e331758468b831aad5c3294617dc573e72c0e05deb4be6b3b5549a";
                String chatAccessToken = bmsProjectEntity.getDingGroupAccessToken();
                String groupSecret = bmsProjectEntity.getDingGroupSecret();

                String mdTitle = "???" + projectDailyEntity.getProjectName() + "???" + projectDailyEntity.getFillDate() + "????????????";

                String briefProjectName = projectDailyEntity.getProjectName();
                if (briefProjectName.length() > 12) {
                    briefProjectName = briefProjectName.substring(0, 12) + "...";
                }

                String mdText = "# " + briefProjectName + "\n";
                mdText += "# " + projectDailyEntity.getFillDate() + "????????????\n";

                String fillUser = projectDailyEntity.getFillUser();
                if (fillUser.indexOf("-") > 0) {
                    fillUser = fillUser.substring(fillUser.lastIndexOf("-") + 1);
                }
                mdText += "### ????????????<font color=#999999 size=2 weight=100>" + fillUser + "</font>\n";
                mdText += "### ???????????????<font color=#999999 size=2 weight=100>" + projectDailyEntity.getTotalStatus() + "</font>\n";
                mdText += "### ???????????????<font color=#999999 size=2 weight=100>" + projectDailyEntity.getCurrPhase() + "</font>\n";
                mdText += "### ?????????????????????<font color=#999999 size=2 weight=100>" + projectDailyEntity.getNextPayStep() + "</font>\n";
                mdText += "### ?????????????????????<font color=#999999 size=2 weight=100>" + projectDailyEntity.getNextPayDate() + "</font>\n";
                String todayResults = projectDailyEntity.getTodayResults();
                todayResults = StringUtils.defaultIfBlank(todayResults, "???");
                todayResults = todayResults.replaceAll("\\\\n", "\n");
                todayResults = todayResults.replaceAll("\\n", "  \n> ");
                mdText += "### ???????????????\n> " + todayResults + "\n";

                String needSupportStuffs = projectDailyEntity.getNeedSupportStuffs();
                needSupportStuffs = StringUtils.defaultIfBlank(needSupportStuffs, "???");
                needSupportStuffs = needSupportStuffs.replaceAll("\\\\n", "\n");
                needSupportStuffs = needSupportStuffs.replaceAll("\\n", "  \n> ");
                mdText += "### ??????????????????\n> " + needSupportStuffs + "\n";
                String problems = projectDailyEntity.getProblems();
                problems = StringUtils.defaultIfBlank(problems, "???");
                problems = problems.replaceAll("\\\\n", "\n");
                problems = problems.replaceAll("\\n", "  \n> ");
                mdText += "### ??????????????????\n> " + problems + "\n";
                String tomorrowPlans = projectDailyEntity.getTomorrowPlans();
                tomorrowPlans = StringUtils.defaultIfBlank(tomorrowPlans, "???");
                tomorrowPlans = tomorrowPlans.replaceAll("\\\\n", "\n");
                tomorrowPlans = tomorrowPlans.replaceAll("\\n", "  \n> ");
                mdText += "### ???????????????\n> " + tomorrowPlans + "\n";
                BmsHelper.sendMsgToDingGroupInMd(restTemplate, chatAccessToken, groupSecret, mdTitle, mdText);
                LOGGER.info("????????????????????????????????????" + projectDailyEntity.getProjectId() + "##" + projectDailyEntity.getProjectName());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /*
     * AF???????????????????????????????????????????????????-post
     * */
    @PostMapping("/adjustProjectInfoByBaInfo")
    protected BasicTokenStoreEntity adjustProjectInfoByBaInfo(
            @RequestBody BmsDataJournalEntity bmsDataJournalEntity
    ) throws Exception {
        BasicTokenStoreEntity btse = new BasicTokenStoreEntity();
        btse.setId(bmsDataJournalEntity.getModularInnerId2());
        btse.setToken(bmsDataJournalEntity.getModularDesc2());
        if (StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId1()) || StringUtils.isEmpty(bmsDataJournalEntity.getModularDesc1())) {//modularInnerId1???modularDesc1????????????????????????????????????????????????????????????
        } else if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2()) && !StringUtils.isEmpty(bmsDataJournalEntity.getModularDesc2())) { //modularInnerId2???modularDesc2???????????????????????????????????????????????????????????????
        } else {
            ProjectAssociationWhereLogic paWhereLogic = new ProjectAssociationWhereLogic();
            paWhereLogic.setModular("ba");
            paWhereLogic.setModularInnerId(bmsDataJournalEntity.getModularInnerId1());
            List<BmsProjectAssociationEntity> al = bmsProjectAssociationService.selectListByWhereLogic(paWhereLogic);
            List<BmsProjectEntity> hitProjectList = new ArrayList<>();
            for (BmsProjectAssociationEntity bpae : al) {
                BmsProjectEntity bpe = bmsProjectService.selectById(bpae.getProjectId());
                LocalDate projectStartDate = bpe.getCreateDate().toLocalDate();
                if (!StringUtils.isEmpty(bpe.getStartDate())) {
                    projectStartDate = LocalDate.parse(bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                //??????????????????????????????????????????????????????????????????????????????
                if (
                        projectStartDate.isBefore(LocalDate.now()) && (
                                bpe.getFinCloseDate() == null || (LocalDate.now()).isBefore(bpe.getFinCloseDate())
                        )
                ) {
                    hitProjectList.add(bpe);
                }
            }
            //???????????????????????????????????????
            if (hitProjectList.size() == 1) {
                BmsProjectEntity hitProjectEntity = hitProjectList.get(0);
                btse.setId(hitProjectEntity.getId());
                btse.setToken(hitProjectEntity.getProjectName());
            }
        }
        return btse;
    }

    /*
     * Alpha?????????Callin????????????????????????????????????????????????????????????-post
     * */
    @PostMapping("/computeSalesManagerNameForAfCallin")
    protected BasicTokenStoreEntity computeSalesManagerNameForAfCallin(
            @RequestParam("salesDealerDecision") String salesDealerDecision ,
            @RequestParam("salesDealer") String salesDealer ,
            @RequestParam("salesManager") String salesManager
    ) throws Exception {
        BasicTokenStoreEntity rsEntity = new BasicTokenStoreEntity();
        String dealerStr = "";
        if(salesDealerDecision.contains("??????")){ //???????????????????????????????????????????????????
            dealerStr = salesManager;
        }else{ //???????????????????????????????????????????????????
            if(salesDealer.indexOf("-")>0){
                dealerStr = salesDealer.substring(salesDealer.lastIndexOf("-")+1);
            }else {
                dealerStr = salesDealer;
            }
        }
        rsEntity.setToken(dealerStr);
        return rsEntity;
    }

    /*
     * Alpha?????????Callin???????????????????????????????????????????????????????????????-post
     * */
    @PostMapping("/setBaAuthForAfCallin")
    protected BasicTokenStoreEntity setBaAuthForAfCallin(
            @RequestParam("salesDealerDesc") String salesDealerDesc ,
            @RequestParam("baId") String baId
    ) throws Exception {
        BasicTokenStoreEntity rsEntity = new BasicTokenStoreEntity();
        String opUserId = BmsHelper.getUserIdByAfUserStr(userService, salesDealerDesc);
        if(!StringUtils.isEmpty(opUserId)){
            UserEntity userEntity = userService.selectSingle(opUserId);
            if(userEntity != null) {
                for(DeptEntity deptEntity : userEntity.getDepartments()){
                    String orgId = deptEntity.getId() + "." + opUserId;
                    BaAuthEntity baAuthEntity = new BaAuthEntity();
                    baAuthEntity.setBaId(baId);
                    baAuthEntity.setKey("owner");
                    baAuthEntity.setOrgId(orgId);
                    baAuthEntity.setLinkId(opUserId);
                    baAuthEntity.setExpress(opUserId);
                    baAuthEntity.setType(1);
                    baAuthEntity.setCreateUser("system");
                    baAuthEntity.setModUser("system");
                    baAuthService.insert(baAuthEntity);
                }
            }
        }
        return rsEntity;
    }


    @PostMapping("/addEcoUiBot")
    protected String addEcoUiBot(@RequestBody CustomerServiceEntity customerServiceEntity) {
        String r = "";
        try {
            ObjectNode reqItemJsonObj = new ObjectMapper().createObjectNode();
            reqItemJsonObj.put("customerName", customerServiceEntity.getCustomerName());
            reqItemJsonObj.put("customerContact", customerServiceEntity.getCustomerContact());
            reqItemJsonObj.put("contactContent", customerServiceEntity.getContactContent());
            reqItemJsonObj.put("reqDataId", customerServiceEntity.getReqDataId());
            reqItemJsonObj.put("taskDataId", customerServiceEntity.getTaskDataId());
            reqItemJsonObj.put("taskId", customerServiceEntity.getTaskId());
            r = reqItemJsonObj.toString();
            BasicTokenStoreEntity btse = new BasicTokenStoreEntity();
            btse.setId(customerServiceEntity.getRpaCode());
            btse.setToken(r);
            tokenStoreService.insert(btse);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/checkEcoUiBotExist")
    protected String checkEcoUiBotExist(@RequestParam("rpaCode") String rpaCode) {
        String r = "miss";
        try {
            BasicTokenStoreEntity btse = tokenStoreService.selectById(rpaCode);
            if (btse != null) {
                r = "hit";
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/getEcoUiBotParam")
    protected String getEcoUiBotParam(@RequestParam("rpaCode") String rpaCode) {
        String r = "";
        try {
            BasicTokenStoreEntity btse = tokenStoreService.selectById(rpaCode);
            if (btse != null) {
                r = btse.getToken();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/removeEcoUiBot")
    protected String removeEcoUiBot(@RequestParam("rpaCode") String rpaCode) {
        String r = "";
        try {
            tokenStoreService.deleteById(rpaCode);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/pushAfWfAfterEcoUiBot")  //???????????????????????????????????????????????????????????????Alpha????????????
    protected String pushAfWfAfterEcoUiBot(@RequestParam("rpaCode") String rpaCode) {
        String r = "";
        try {
            BasicTokenStoreEntity btse = tokenStoreService.selectById(rpaCode);
            if (btse != null) {
                r = btse.getToken();
                ObjectMapper objectMapper = new ObjectMapper();


                //String expected = "{\"name\":\"Test\"}";
                CustomerServiceEntity customerServiceEntity = objectMapper.readValue(r, CustomerServiceEntity.class);
                //CustomerServiceEntity customerServiceEntity = objectMapper.convertValue(r, new TypeReference< CustomerServiceEntity>() {});
                ObjectNode reqItemJsonObj = new ObjectMapper().createObjectNode();
                ObjectNode reqFileParamObj = new ObjectMapper().createObjectNode();
                AfDriveTaskResponseDTO driveTaskDTO = afWfMaster.driveTask(BmsHelper.afTenantId, BmsHelper.afPeterAccountId, customerServiceEntity.getReqDataId(), customerServiceEntity.getTaskDataId(), customerServiceEntity.getTaskId(), reqItemJsonObj, reqFileParamObj);
                tokenStoreService.deleteById(rpaCode);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/pushAfhwcWfAfterEcoUiBot") //?????????????????????????????????????????????????????????????????????SaaS???
    protected String pushAfhwcWfAfterEcoUiBot(@RequestParam("rpaCode") String rpaCode) {
        String r = "";
        try {
            BasicTokenStoreEntity btse = tokenStoreService.selectById(rpaCode);
            if (btse != null) {
                r = btse.getToken();
                ObjectMapper objectMapper = new ObjectMapper();
                //String expected = "{\"name\":\"Test\"}";
                CustomerServiceEntity customerServiceEntity = objectMapper.readValue(r, CustomerServiceEntity.class);
                //CustomerServiceEntity customerServiceEntity = objectMapper.convertValue(r, new TypeReference< CustomerServiceEntity>() {});
                ObjectNode reqItemJsonObj = new ObjectMapper().createObjectNode();
                ObjectNode reqFileParamObj = new ObjectMapper().createObjectNode();
                AfhwcDriveTaskResponseDTO driveTaskDTO = afhwcWfMaster.driveTask(BmsHelper.afhwcTenantId, BmsHelper.afhwcYayaAccountId, customerServiceEntity.getReqDataId(), customerServiceEntity.getTaskDataId(), customerServiceEntity.getTaskId(), reqItemJsonObj, reqFileParamObj);
                tokenStoreService.deleteById(rpaCode);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }
    /*
     * adjustDataJournalExtContent-post  ????????????af??????????????????ext_content???????????????
     * */

    @PostMapping("/adjustDataJournalExtContent")
    protected String adjustDataJournalExtContent() {
        String r = "";
        try {
            List<String> validUpdateCols = ImmutableList.of("extContent");
            BmsDataJournalWhereLogic whereLogic = new BmsDataJournalWhereLogic();
            whereLogic.setStatusTarget(true);
            whereLogic.setExtContentError(true);
            List<BmsDataJournalEntity> journalList = dataJournalService.selectListByWhereLogic(whereLogic);
            for (BmsDataJournalEntity bdje : journalList) {
                try {
                    AfWorkflowInstModel awim = afWfMaster.getWorkflowInstanceData(Long.parseLong(bdje.getSourceId()));
                    BmsDataJournalEntity checkEntity = new BmsDataJournalEntity();
                    checkEntity.setId(bdje.getId());
                    checkEntity.setExtContent(jsonPrintHelper.printQuite(awim));
                    dataJournalService.updatePartialColumnById(checkEntity, validUpdateCols);
                } catch (Exception e1) {
                    LOGGER.error("?????????????????????????????????" + bdje.getSourceId() + "??????" + e1.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/adjustDataJournalFinalRelatedProject")
    protected String adjustDataJournalFinalRelatedProject() {
        String r = "";
        try {
            List<String> validUpdateCols = ImmutableList.of("finalRelatedProjectId");
            BmsDataJournalWhereLogic whereLogic = new BmsDataJournalWhereLogic();
            whereLogic.setStatusTarget(true);
            whereLogic.setAboutFin(true);
            whereLogic.setModular("ba");
            whereLogic.setNoFinalRelatedProject(true);
            List<BmsDataJournalEntity> journalList = dataJournalService.selectListByWhereLogic(whereLogic);
            int i = 0;
            for (BmsDataJournalEntity bdje : journalList) {
                i++;
                try {
                    ProjectAssociationWhereLogic paWhereLogic = new ProjectAssociationWhereLogic();
                    paWhereLogic.setModular("ba");
                    paWhereLogic.setModularInnerId(bdje.getModularInnerId());
                    List<BmsProjectAssociationEntity> al = bmsProjectAssociationService.selectListByWhereLogic(paWhereLogic);
                    String finalRelatedProjectIdStr = "";
                    for (BmsProjectAssociationEntity bpae : al) {
                        BmsProjectEntity bpe = bmsProjectService.selectById(bpae.getProjectId());
                        LocalDate projectStartDate = LocalDate.parse(bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        //??????????????????????????????????????????????????????????????????????????????????????????
                        if (
                                projectStartDate.isBefore((bdje.getCreateDate()).toLocalDate()) && (
                                        bpe.getFinCloseDate() == null || ((bdje.getCreateDate()).toLocalDate()).isBefore(bpe.getFinCloseDate())
                                )
                        ) {
                            if (finalRelatedProjectIdStr.equals("")) {
                                finalRelatedProjectIdStr = bpe.getId();
                            } else {
                                finalRelatedProjectIdStr += "#" + bpe.getId();
                            }
                        }
                    }
                    //??????finalRelatedProjectIdStr?????????????????????????????????#????????????????????????projectId???????????????@
                    if (!finalRelatedProjectIdStr.equals("") && finalRelatedProjectIdStr.indexOf("#") < 0) {
                        finalRelatedProjectIdStr = "@" + finalRelatedProjectIdStr;
                    }
                    if (!finalRelatedProjectIdStr.equals("")) {
                        LOGGER.info("???" + i + "????????????ba:" + bdje.getModularDesc() + "#" + bdje.getId() + "#" + "????????????" + finalRelatedProjectIdStr);
                        BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
                        updateDataJournalEntity.setId(bdje.getId());
                        updateDataJournalEntity.setFinalRelatedProjectId(finalRelatedProjectIdStr);
                        dataJournalService.updatePartialColumnById(updateDataJournalEntity, validUpdateCols);
                    } else {
                        LOGGER.info("???" + i + "????????????ba:" + bdje.getModularDesc() + "#" + bdje.getId() + "#" + "???????????????");
                    }
                } catch (Exception e1) {
                    LOGGER.error("???????????????" + bdje.getSourceId() + "??????" + e1.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/adjustDataJournalMultiFinalRelatedProject")
    protected String adjustDataJournalMultiFinalRelatedProject() {
        String r = "";
        try {
            List<String> validUpdateCols = ImmutableList.of("finalRelatedProjectId");
            BmsDataJournalWhereLogic whereLogic = new BmsDataJournalWhereLogic();
            whereLogic.setStatusTarget(true);
            whereLogic.setAboutFin(true);
            whereLogic.setModular("ba");
            whereLogic.setMultiFinalRelatedProject(true);
            List<BmsDataJournalEntity> journalList = dataJournalService.selectListByWhereLogic(whereLogic);
            int i = 0;
            for (BmsDataJournalEntity bdje : journalList) {
                i++;
                try {

                    String checkFinalRelatedProjectIdStr = bdje.getFinalRelatedProjectId();
                    ArrayList<String> checkProjectIdList = BmsHelper.StringToArrayList(checkFinalRelatedProjectIdStr, "#");
                    BmsProjectEntity latest_bpe = null;//?????????????????????
                    for (String checkProjectId : checkProjectIdList) {
                        BmsProjectEntity bpe = bmsProjectService.selectById(checkProjectId);
                        if (latest_bpe == null) {
                            latest_bpe = bpe;
                        } else {
                            LocalDate latest_bpe_StartDate = LocalDate.parse(latest_bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            LocalDate check_node_bpe_StartDate = LocalDate.parse(bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            //?????????????????????????????????latest_bpe?????????????????????????????????????????????????????????????????????latest_bpe
                            if (latest_bpe_StartDate.isBefore(check_node_bpe_StartDate)) {
                                latest_bpe = bpe;
                            }
                        }
                    }
                    LOGGER.info("???" + i + "????????????ba:" + bdje.getModularDesc() + "#" + bdje.getId() + "#" + "????????????" + latest_bpe.getId());
                    BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
                    updateDataJournalEntity.setId(bdje.getId());
                    updateDataJournalEntity.setFinalRelatedProjectId(bdje.getFinalRelatedProjectId() + "@" + latest_bpe.getId());
                    dataJournalService.updatePartialColumnById(updateDataJournalEntity, validUpdateCols);
                } catch (Exception e1) {
                    LOGGER.error("???????????????" + bdje.getSourceId() + "??????" + e1.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    @PostMapping("/adjustDataJournalNoFinalRelatedProject")
    protected String adjustDataJournalNoFinalRelatedProject() {
        String r = "";
        try {
            List<String> validUpdateCols = ImmutableList.of("finalRelatedProjectId");
            BmsDataJournalWhereLogic whereLogic = new BmsDataJournalWhereLogic();
            whereLogic.setStatusTarget(true);
            whereLogic.setAboutFin(true);
            whereLogic.setModular("ba");
            whereLogic.setNoFinalRelatedProject(true);
            List<BmsDataJournalEntity> journalList = dataJournalService.selectListByWhereLogic(whereLogic);
            int i = 0;
            for (BmsDataJournalEntity bdje : journalList) {
                i++;
                try {
                    if (bdje.getModularInnerId() == null) continue;
                    ProjectAssociationWhereLogic paWhereLogic = new ProjectAssociationWhereLogic();
                    paWhereLogic.setModular("ba");
                    paWhereLogic.setModularInnerId(bdje.getModularInnerId());
                    List<BmsProjectAssociationEntity> al = bmsProjectAssociationService.selectListByWhereLogic(paWhereLogic);
                    if (al != null && al.size() > 0) {
                        BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
                        updateDataJournalEntity.setId(bdje.getId());
                        updateDataJournalEntity.setFinalRelatedProjectId("@" + al.size());
                        dataJournalService.updatePartialColumnById(updateDataJournalEntity, validUpdateCols);

//                        //?????????????????????????????????????????????????????????????????????
//                        if(al.size()==1){
//                            BmsProjectAssociationEntity bpae = al.get(0);
//                            BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
//                            updateDataJournalEntity.setId(bdje.getId());
//                            updateDataJournalEntity.setFinalRelatedProjectId("#"+bpae.getProjectId());
//                            dataJournalService.updatePartialColumnById(updateDataJournalEntity,validUpdateCols);
//                        }

//                        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//                        BmsProjectEntity ealist_bpe = null;
//                        for (BmsProjectAssociationEntity bpae:al) {
//                            BmsProjectEntity bpe = bmsProjectService.selectById(bpae.getProjectId());
//                            if(ealist_bpe == null){
//                                ealist_bpe = bpe;
//                            }else {
//                                LocalDate ealist_bpeStartDate = LocalDate.parse(ealist_bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                                LocalDate check_node_bpeStartDate = LocalDate.parse(bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                                if(check_node_bpeStartDate.isBefore(ealist_bpeStartDate)){
//                                    ealist_bpe = bpe;
//                                }
//                            }
//                        }
//                        LocalDate ealist_bpeStartDate = LocalDate.parse(ealist_bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                        //??????????????????????????????????????????????????????????????????????????????
//                        if((bdje.getCreateDate().toLocalDate()).isBefore(ealist_bpeStartDate)){
//                            LOGGER.info("???"+i+"????????????ba:"+bdje.getModularDesc()+"#"+bdje.getId()+"#");
//                            BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
//                            updateDataJournalEntity.setId(bdje.getId());
//                            updateDataJournalEntity.setFinalRelatedProjectId("@"+ealist_bpe.getId());
//                            dataJournalService.updatePartialColumnById(updateDataJournalEntity,validUpdateCols);
//                        }else{
//                            LOGGER.info("???"+i+"????????????ba:"+bdje.getModularDesc()+"#"+bdje.getId()+"#");
//                            BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
//                            updateDataJournalEntity.setId(bdje.getId());
//                            updateDataJournalEntity.setFinalRelatedProjectId("#");
//                            dataJournalService.updatePartialColumnById(updateDataJournalEntity,validUpdateCols);
//                        }


                    }
                } catch (Exception e1) {
                    LOGGER.error("???????????????" + bdje.getSourceId() + "??????" + e1.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    /*
     * test-post
     * */

    @PostMapping("/test")
    protected String test() {
        String r = "";
        try {
            String url = "http://192.168.0.103:30003/zentao/openapi/trial/list?dateTime=";
            //??????6??????????????????
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strTime = sdf.format((new Date()).getTime() - 6 * 60 * 1000);
            strTime = "2019-09-09 09:00:00";
            url += strTime;
            Resource resp = restTemplate.getForObject(url, Resource.class);
            String respStr = IOUtils.toString(resp.getInputStream(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            //TmpLibGeneralEntity tmpLibGeneralEntity = objectMapper.convertValue(respStr, new TypeReference< TmpLibGeneralEntity>() {});
            TmpLibGeneralEntity tmpLibGeneralEntity = objectMapper.readValue(respStr, TmpLibGeneralEntity.class);
            if (tmpLibGeneralEntity.getStatus() != null && tmpLibGeneralEntity.getStatus() == 200) {
                if (tmpLibGeneralEntity.getData().getClass() == ArrayList.class) {
                    ArrayList<TmpLibBizOppoEntity> boList = objectMapper.convertValue(tmpLibGeneralEntity.getData(), new TypeReference<ArrayList<TmpLibBizOppoEntity>>() {
                    });
                    for (TmpLibBizOppoEntity tlboe : boList) {
                        BizOppoEntity boe = new BizOppoEntity();
                        boe.setId(tlboe.getSourcePt() + "_" + tlboe.getId());
                        boe.setSourceId(tlboe.getSourceId());
                        boe.setSourceFlag(BmsHelper.getBizOppoSourceFlagByDesc(tlboe.getSourcePt()));
                        boe.setApplyPhone(tlboe.getPhone());
                        boe.setApplyContactorName(tlboe.getContractName());
                        boe.setApplyBaName(tlboe.getTenantName());
                        String dateStr = tlboe.getSourceCreateDate();
                        if (dateStr.length() == 16) dateStr += ":00";
                        boe.setSourceApplyDate(Instant.ofEpochMilli(sdf.parse(dateStr).getTime())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime());
                        try {
                            bizOppoService.insert(boe);
                        } catch (Exception ex) {
                            LOGGER.info(ex.getMessage() + "#" + boe.getId() + "#" + boe.getApplyBaName());
                        }
                    }
                }
            }

            r = "url:" + url + "$$$" + respStr;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return r;
        }
    }

    /*
     * test-post
     * */

    @PostMapping("/test2")
    protected String test2(@RequestBody String rsData) {
        String r = rsData;

        return r;
    }

    /*
     * getDataTypeTest-post
     * */
    @PostMapping("/getDataTypeTest")
    protected List<BasicTokenStoreEntity> getDataTypeTest() {
        List<BasicTokenStoreEntity> v = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BasicTokenStoreEntity btse = new BasicTokenStoreEntity();
            btse.setToken("????????????" + (i + 1));
            v.add(btse);
        }
        return v;
    }


    SecretKey secretKey = AESCryptoUtils.parseAesSecretKeyRaw("gBXVqcsJhd1TEV5A");
    /*
     * getDataTypeTest-post
     * */
    @PostMapping("/postTest")
    protected String postTest(HttpServletRequest request) throws IOException {
        var allData = IOUtils.toByteArray(request.getInputStream());
        var allStr = StringMore.newUtf8String(allData);
        var reqContext = AESCryptoUtils.aesDecryptBase64(allStr,secretKey);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(reqContext);
        JsonNode formValueObj = jsonNode.get("data").get("formInfo").get("widgetMap");
        String approvalBpaId = formValueObj.get("Te_2").get("value").asText();  //bpa?????????????????????id

        return "success";
    }


    @PostMapping("/uploadFileToCloudflowTest")
    protected String uploadFileToCloudflowTest(@RequestParam("fileAccessToken") String fileAccessToken) throws Exception {
        String fileUrl = "D:\\txtTest.txt";
        String url = "https://yunzhijia.com/docrest/doc/file/uploadfile";
        byte[] bytes = Files.readAllBytes(Paths.get(fileUrl));
        MediaType fileType = MediaType.parse("application/octet-stream");
        okhttp3.RequestBody fileBody = okhttp3.RequestBody.create(fileType, bytes);
        okhttp3.RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("file", "testupload.txt", fileBody)
                .addFormDataPart("bizkey", "cloudflow")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "multipart/form-data")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("x-accessToken", fileAccessToken)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        return responseData;
    }
    @PostMapping("/invoiceRecognize2")
    protected InvoiceInfoEntity invoiceRecognize2(@RequestParam("file") String fileStr) throws Exception {
        JSONObject fileObj =  (JSONObject) JSON.parse(fileStr);
        JSONArray attArray = fileObj.getJSONArray("attachments");
        JSONObject attNode = (JSONObject)attArray.get(0);
        //String fileHeaderId = "1543618136811462658";
        String fileHeaderId = attNode.getString("fileHeaderId");
//        String bpmAppId = "1543602807867662337";
//        String bpmAppSecret = "1543602922695122945";
        String bpmAppId = "1544576076071936002";
        String bpmAppSecret = "1544576140097986561";
        String bpmUrlPrefix = "http://192.168.1.35:82/api";
//        String getTokenUrl = "http://192.168.0.27:7077/oauth2/access-token?appid="+bpmAppId+"&secret="+bpmAppSecret;
        String getTokenUrl = bpmUrlPrefix + "/oauth2/access-token?appid="+bpmAppId+"&secret="+bpmAppSecret;
        ResponseEntity<String> getTokenRe = restTemplate.getForEntity(getTokenUrl, String.class);
        //System.out.println("## getTokenReStr:" + getTokenReStr.getBody().toString());
        String accessToken = getTokenRe.getBody();

        //String getFileUrl = "http://192.168.0.27:7077/web-service/standard/file-manager/download/single?fileHeaderId="+fileHeaderId+"&contentType=base64";
        String getFileUrl = bpmUrlPrefix + "/web-service/standard/file-manager/download/single?fileHeaderId="+fileHeaderId+"&contentType=base64";
        HttpHeaders getFileHeaders = new HttpHeaders();
        getFileHeaders.add("eco-oauth2-token" , accessToken);
        HttpEntity<String> getFileReqEntity = new HttpEntity<String>(null, getFileHeaders);
        ResponseEntity<String> getFileRe = restTemplate.exchange(getFileUrl, HttpMethod.GET, getFileReqEntity, String.class);
        String fileBase64Code = getFileRe.getBody();
        fileBase64Code=fileBase64Code.replaceAll("[\\t\\n\\r]", "");
        //fileBase64Code="abc";

        InvoiceInfoEntity iie = new InvoiceInfoEntity();
        String response_content = "";
        String host = "ocr.tencentcloudapi.com";
        String api_service = "ocr";
        String algorithm = "TC3-HMAC-SHA256";
        String SECRET_ID = "";
        String SECRET_KEY = "";
        String api_action = "VatInvoiceOCR";
        String api_imageUrl = "https://img.alicdn.com/tfs/TB1qIIfXAPoK1RjSZKbXXX1IXXa-808-523.jpg";
        int random_num = (int)(Math.random()*1000000);
        String api_version = "2018-11-19";
        String api_region = "ap-shanghai";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json; charset=utf-8\n" + "host:" + host + "\n";
        String signedHeaders = "content-type;host";

        //String payload = "{\"ImageUrl\":\""+api_imageUrl+"\"}";
        //String payload = "{\"ImageBase64\":\""+fileBase64Code+"\",\"IsPdf\":true}";
        String payload = "{\"ImageBase64\":\""+fileBase64Code+"\",\"IsPdf\":true}";
        String hashedRequestPayload = BmsHelper.sha256Hex(payload);
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n" + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.valueOf(timestamp + "000")));
        String credentialScope = date + "/" + api_service + "/" + "tc3_request";
        String hashedCanonicalRequest = BmsHelper.sha256Hex(canonicalRequest);
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        byte[] secretDate = BmsHelper.hmac256(("TC3" + SECRET_KEY).getBytes(BmsHelper.UTF8), date);
        byte[] secretService = BmsHelper.hmac256(secretDate, api_service);
        byte[] secretSigning = BmsHelper.hmac256(secretService, "tc3_request");
        String signature = Hex.encodeHexString(BmsHelper.hmac256(secretSigning, stringToSign)).toLowerCase();
        String authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://ocr.tencentcloudapi.com/";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization", authorization);
        httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.setHeader("Host", host);
        httpPost.setHeader("X-TC-Action", api_action);
        httpPost.setHeader("X-TC-Timestamp", timestamp);
        httpPost.setHeader("X-TC-Version", api_version);
        httpPost.setHeader("X-TC-Region", api_region);

        StringEntity se = new StringEntity(payload);
        se.setContentType("text/json");
        httpPost.setEntity(se);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            org.apache.http.HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                response_content = EntityUtils.toString(resEntity, "UTF-8");
            }
        } finally {
            response.close();
        }
        if(!StringUtils.isEmpty(response_content)){
            JSONObject totalObj =  (JSONObject) JSON.parse(response_content);
            JSONObject responseObj = (JSONObject)totalObj.get("Response");
            JSONArray invoiceInfoList = (JSONArray)responseObj.get("VatInvoiceInfos");
            for(int i=0;i<invoiceInfoList.size();i++) {
                JSONObject infoNode = invoiceInfoList.getJSONObject(i);
                String checkName = infoNode.getString("Name");
                String checkValue = infoNode.getString("Value");
                if(checkName.contains("??????????????????")){
                    iie.setSellerId(checkValue);
                }else if(checkName.contains("???????????????")){
                    iie.setSellerName(checkValue);
                }else if(checkName.contains("??????????????????")){
                    iie.setBuyerId(checkValue);
                }else if(checkName.contains("???????????????")){
                    iie.setBuyerName(checkValue);
                }else if(checkName.equals("????????????")){
                    iie.setInvoiceCode(checkValue);
                }else if(checkName.contains("????????????")){
                    iie.setInvoiceName(checkValue);
                }else if(checkName.equals("????????????")){
                    iie.setInvoiceNo(checkValue);
                }else if(checkName.contains("????????????")){
                    iie.setMakeOutDate(checkValue);
                }else if(checkName.contains("????????????")){
                    iie.setMachineCode(checkValue);
                }else if(checkName.contains("?????????")){
                    iie.setProofCode(checkValue);
                }else if(checkName.contains("????????????")){ //????????????????????????????????????
                    iie.setGoodsName(checkValue);
                }else if(checkName.contains("??????")){
                    iie.setGoodsNum(checkValue);
                }else if(checkName.contains("??????")){
                    iie.setSinglePriceWithoutTax(checkValue);
                }else if(checkName.equals("??????")){
                    iie.setPriceAmountWithoutTax(checkValue);
                }else if(checkName.contains("??????")){
                    iie.setTaxRate(checkValue);
                }else if(checkName.contains("??????")){
                    iie.setTaxAmount(checkValue);
                }else if(checkName.contains("????????????")){
                    //??75.21
                    if(!checkValue.substring(0,1).matches("[0-9]+")){
                        checkValue = checkValue.substring(1);
                    }
                    iie.setTotalAmountWithoutTax(checkValue);
                }else if(checkName.contains("????????????")){
                    iie.setTotalTaxAmount(checkValue);
                }else if(checkName.contains("????????????")){
                    iie.setRealTotalAmountUpper(checkValue);
                }else if(checkName.contains("????????????")){
                    //"??88.00
                    if(!checkValue.substring(0,1).matches("[0-9]+")){
                        checkValue = checkValue.substring(1);
                    }
                    iie.setRealTotalAmount(checkValue);
                }else if(checkName.contains("???????????????")){
                    iie.setSellerAddressPhone(checkValue);
                }else if(checkName.contains("??????????????????")){
                    iie.setSellerBankInfo(checkValue);
                }else if(checkName.contains("?????????")){
                    iie.setMakeOutPerson(checkValue);
                }else if(checkName.contains("??????????????????")){
                    iie.setInvoiceConsumeType(checkValue);
                }else if(checkName.contains("????????????")){
                    iie.setInvoiceType(checkValue);
                }
            }
        }
        return iie;
    }
    @PostMapping("/invoiceRecognize")
    protected String invoiceRecognize() throws Exception {
        AliCloudApiEntity acae = new AliCloudApiEntity();
        acae.setFormat("XML");
        acae.setVersion("2021-07-07");
        acae.setAction("RecognizeInvoice");
        acae.setAccessKeyId("");
        acae.setSignatureMethod("HMAC-SHA1");
        acae.setSignatureNonce(IdWorker.getIdStr());
        acae.setSignatureVersion("1.0");

        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(16)).toInstant());

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        String timeStr1 = df1.format(date);
        String timeStr2 = df2.format(date);
        String timeStr = timeStr1 + "T" + timeStr2 + "Z";
        //2021-07-07T12:00:00Z
        acae.setTimestamp(timeStr);

        String apiUrl_suffix = "AccessKeyId="+acae.getAccessKeyId()+"&Action="+acae.getAction()+"&Format="+acae.getFormat()+"&SignatureMethod="+acae.getSignatureMethod()+"&SignatureNonce="+acae.getSignatureNonce()+"&SignatureVersion="+acae.getSignatureVersion()+"&Timestamp="+acae.getTimestamp()+"&Version="+acae.getVersion();

//        String apiUrl_suffix_encode = "GET&%2F&" + java.net.URLEncoder.encode(apiUrl_suffix,"UTF-8");
//
        String apiUrl_suffix_encode = "AccessKeyId="+BmsHelper.percentEncode(acae.getAccessKeyId())+"&Action="+BmsHelper.percentEncode(acae.getAction())+"&Format="+BmsHelper.percentEncode(acae.getFormat())+"&SignatureMethod="+BmsHelper.percentEncode(acae.getSignatureMethod())+"&SignatureNonce="+BmsHelper.percentEncode(acae.getSignatureNonce())+"&SignatureVersion="+BmsHelper.percentEncode(acae.getSignatureVersion())+"&Timestamp="+BmsHelper.percentEncode(acae.getTimestamp())+"&Version="+BmsHelper.percentEncode(acae.getVersion());
        apiUrl_suffix_encode = "GET&%2F&" + BmsHelper.percentEncode(apiUrl_suffix_encode);
        String secret = "";
        secret += "&" ;

        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(apiUrl_suffix_encode.getBytes(StandardCharsets.UTF_8));

        //String signature_ori0 = new String(Base64.decodeBase64(rawHmac));

        String signature_ori1 = new String(Base64.encodeBase64(rawHmac), StandardCharsets.UTF_8);
        String signature_ori2 = Base64.encodeBase64String(rawHmac);
        String signature_ori3 = com.taobao.api.internal.util.Base64.encodeToString(rawHmac , true);
        //String signature = Base64Encoder.encode(rawHmac);

        String signature = java.net.URLEncoder.encode(signature_ori1, StandardCharsets.UTF_8);

        String apiUrl = "http://ocr-api.cn-hangzhou.aliyuncs.com/?" + apiUrl_suffix + "&Signature=" + signature ;
        System.out.println("$$apiUrl_suffix_encode:" + apiUrl_suffix_encode);
        ResponseEntity<String> reStr = restTemplate.getForEntity(apiUrl, String.class);
        System.out.println("## reStr:" + reStr);
        return reStr.toString();

    }
    @PostMapping("/crmBot")
    protected JSONObject triggerCrmBot(   @RequestBody JSONObject obj ) throws Exception {
        String content = obj.getJSONObject("text").getString("content").trim();
        String groupName = obj.getString("conversationTitle");
        String baId = "";
        if(groupName.indexOf("#")>0){
            baId = (groupName.trim()).substring(groupName.lastIndexOf("#")+1);
        }
        BmsBaEntity baEntity = bmsBaService.selectById(baId);
        JSONObject reObj = new JSONObject();
        if(content.equals("????????????")){
            JSONObject contentObj = new JSONObject();
            contentObj.put("content","???????????????" + baEntity.getProjectBudget() + "??????");
            reObj.put("text",contentObj);
            reObj.put("msgtype","text");
        }else{
            JSONObject linkObj = new JSONObject();
            linkObj.put("title" , "??????????????????????????????");
            linkObj.put("text" , baEntity.getBaName());
            linkObj.put("messageUrl" , "http://183.129.233.90:8890/#/webRoot/webPlatform/?compFlag=baInfo&jumpBaId="+baId);
            linkObj.put("picUrl" , "http://183.129.233.90:8890/assets/img/crm.png");
            reObj.put("link" , linkObj);
            reObj.put("msgtype","link");
        }
        return reObj;
    }
}
