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
     * 添加需求-post
     * */
    @PostMapping("/kanban/require")
    protected RequireEntity newRequireEntity(
            @RequestBody RequireEntity requireEntity
    ) throws Exception {
        if (StringUtils.isEmpty(requireEntity.getPreSysFlag()) || StringUtils.isEmpty(requireEntity.getPreSysId())) {
            throw new Exception("源系统标识及主键不能为空");
        }
        requireEntity.setId(requireEntity.getPreSysFlag() + "_" + requireEntity.getPreSysId());
        if (requireEntity.getPriority() == null || requireEntity.getPriority() == 0) {
            requireEntity.setPriority(3);
        }
        RequireEntity oriEntity = requireService.selectById(requireEntity.getId());
        if (oriEntity == null) {   //系统中不存在，则插入
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

//        }else{  //系统中已存在，则修改，但忽略状态
//            requireEntity.setStatus(null);
//            requireService.updateById(requireEntity);
        }
        return requireService.selectById(requireEntity.getId());
    }

    /*
     * 修改需求-put
     * */
    @PutMapping("/kanban/require/{preSysFlag}/{preSysId}")
    protected RequireEntity updateRequireEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestBody RequireEntity requireEntity
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("源系统标识及主键不能为空");
        }
        requireEntity.setId(preSysFlag + "_" + preSysId);
        requireEntity.setPreSysFlag(preSysFlag);
        requireEntity.setPreSysId(preSysId);
        boolean update_success_flag = requireService.updateById(requireEntity);
        if (!update_success_flag) throw new Exception("没有定位到任何需要更新的记录！");
        return requireService.selectById(requireEntity.getId());
    }

    /*
     * 修改需求状态-patch
     * */
    @PatchMapping("/kanban/require/{preSysFlag}/{preSysId}/status")
    protected void patchRequireEntityStatus(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestParam("status") Integer status
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("源系统标识及主键不能为空");
        }
        boolean update_success_flag = requireService.updateRequireStatus(preSysFlag + "_" + preSysId, status);
        if (!update_success_flag) throw new Exception("没有定位到任何需要更新的记录！");
    }

    /*
     * 作废需求-patch
     * */
    @PatchMapping("/kanban/require/{preSysFlag}/{preSysId}/remove")
    protected void removeRequireEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("源系统标识及主键不能为空");
        }
        boolean update_success_flag = requireService.changeRequireValid(preSysFlag + "_" + preSysId, false);
        if (!update_success_flag) throw new Exception("没有定位到任何需要更新的记录！");
    }

    /*
     * 添加任务-post
     * */
    @PostMapping("/kanban/task")
    protected TaskEntity newTaskEntity(
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(taskEntity.getPreSysFlag()) || StringUtils.isEmpty(taskEntity.getPreSysId())) {
            throw new Exception("源系统标识及主键不能为空");
        }
        if ("zen".equals(taskEntity.getPreSysFlag())) {  //禅道过来的，项目标识为“内部迭代”
            taskEntity.setProjectFlag(2);
        } else if ("wf".equals(taskEntity.getPreSysFlag())||"af".equals(taskEntity.getPreSysFlag())) { //88crm过来的，项目标识为“实际项目”
            taskEntity.setProjectFlag(1);
        }
        //如果是88crm过来的，并且有关联项目，则将项目id替换成wh88上的。
        if ("wf".equals(taskEntity.getPreSysFlag()) && !StringUtils.isEmpty(taskEntity.getProjectId()) && !"0".equals(taskEntity.getProjectId())) {
            String proIdWh88 = bmsProjectForImportService.getProjectIdInWh88By88crm(taskEntity.getProjectId());
            if (!StringUtils.isEmpty(proIdWh88)) {    //成功取到wh88上的项目id，替换之
                taskEntity.setProjectId(proIdWh88);
            } else {                                   //未取到wh88上对应的项目id，为其加上crm88前缀以标识
                taskEntity.setProjectId("crm88_" + taskEntity.getProjectId());
                taskEntity.setProjectFlag(2);
            }
        }
        //如果是禅道过来的，在关联的需求id前默认加上"zen_"前缀
        if ("zen".equals(taskEntity.getPreSysFlag()) && !StringUtils.isEmpty(taskEntity.getRequireId()) && !"0".equals(taskEntity.getRequireId())) {
            taskEntity.setRequireId("zen_" + taskEntity.getRequireId());
        }
        if (taskEntity.getPriority() == null || taskEntity.getPriority() == 0) {
            taskEntity.setPriority(3);
        }
        if (!StringUtils.isEmpty(taskEntity.getDealer())) {
            UserWhereLogic userWhereLogic = new UserWhereLogic();
            userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
            if (!Character.isDigit((taskEntity.getDealer()).charAt(0))) {//经办人字段传过来的首字符不是数字，则认为传了姓名，需要转成dinguserid
                userWhereLogic.setNameFuzzySearch(taskEntity.getDealer());
                List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
                if (userV != null && userV.size() > 0) {
                    UserEntity ue = userV.get(0);
                    taskEntity.setDealer(ue.getId());
                }
            } else {//经办人字段传过来的首字符是数字，则认为传了ding_user_id，需要转成user_id
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
            if (!Character.isDigit((taskEntity.getFollowuper()).charAt(0))) {//跟进人字段传过来的首字符不是数字，则认为传了姓名，需要转成dinguserid
                userWhereLogic.setNameFuzzySearch(taskEntity.getFollowuper());
                List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
                if (userV != null && userV.size() > 0) {
                    UserEntity ue = userV.get(0);
                    taskEntity.setFollowuper(ue.getId());
                }
            } else {//经办人字段传过来的首字符是数字，则认为传了ding_user_id，需要转成user_id
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

        if (oriEntity == null) {   //系统中不存在，则插入
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
//        }else{ //系统中已存在，则修改（但忽略状态和经办人）
//            taskEntity.setStatusDesc(null);
//            taskEntity.setStatus(null);
//            taskEntity.setDealer(null);
//            taskEntity.setDealerName(null);
//            taskService.updateById(taskEntity);
        }
        return taskService.selectById(taskEntity.getId());
    }
    /*
     * 查询单条任务记录-get
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
                te.setRequireDesc("#"+re.getId()+" : "+re.getTitle()+" ( 优先级："+re.getPriority()+" )");
            }
        }
        if(te.getProjectFlag()!=null&&te.getProjectFlag()!=0&&!StringUtils.isEmpty(te.getProjectId())){
            if(te.getProjectFlag()==1){ //实际项目
                BmsProjectEntity proEntity = bmsProjectService.selectById(te.getProjectId());
                if(proEntity!=null) te.setProjectDesc(proEntity.getProjectName());
            }else if(te.getProjectFlag()==2){//内部迭代
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
     * 转派任务-post
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
        mtae.setMsg("任务“" + curr_te.getTitle() + "”已转派给" + dealerName + "，请确认！");
        mtae.setToDealerName(dealerName);
        String mqTopic = "BMS:" + curr_te.getPreSysFlag();
        BmsHelper.sendMsgToAf(rocketMQTemplate , mtae , mqTopic , userService);


        return curr_te;
    }
    /*
     * 开放接口--添加任务计划日程-post
     * */
    @PostMapping("/kanban/taskCalendar/plan")
    protected TaskCalendarEntity newTaskCalenderEntityForPlan(
            @RequestBody TaskCalendarEntity taskCalendarEntity
    ){
        taskCalendarEntity.setPlanCreateTime(SystemClock.nowDateTime());
        taskCalendarEntity.setPlanInputer("system");

        if(taskCalendarEntity.getDealer()==null || StringUtils.isEmpty(taskCalendarEntity.getDealer())){//如果处理人为空，则默认任务的处理人
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
     * 开放接口--反馈任务日程-post
     * */
    @PostMapping("/kanban/taskCalendar/feedback")
    protected TaskCalendarEntity feedbackTaskCalender(
            @RequestBody TaskCalendarEntity taskCalendarEntity
    ){
        taskCalendarEntity.setFeedbackCreateTime(SystemClock.nowDateTime());
        taskCalendarEntity.setFeedbackInputer("system");
        TaskEntity curr_taskEntity = taskService.selectById(taskCalendarEntity.getTaskId());
        if(taskCalendarEntity.getFeedbackRate() > curr_taskEntity.getCurrentRate()){  //如反馈的完成度比任务当前的完成度高，则更新任务当前完成度
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(taskCalendarEntity.getTaskId());
            taskEntity.setCurrentRate(taskCalendarEntity.getFeedbackRate());
            taskService.updateById(taskEntity);
        }
        if(taskCalendarEntity.getFeedbackRate() == 100) {  //如反馈100%，并且下面再判断当前任务处于“未安排”、“已安排待办”、“进行中”这3种状态之一，则任务状态置为“已完成未确认”
            TaskEntity ori_taskEntity = taskService.selectById(taskCalendarEntity.getTaskId());
            if(ori_taskEntity.getStatus() == -1 ||ori_taskEntity.getStatus() == 10 || ori_taskEntity.getStatus() == 20 ) {
                TaskEntity taskEntity = new TaskEntity();
                taskEntity.setId(taskCalendarEntity.getTaskId());
                taskEntity.setActualFinishDate(SystemClock.nowLocalDate()); //回写实际完成时间为当前日期
                taskEntity.setActualManHour(taskCalendarEntity.getCorrectTotalTaskManHour());
                taskEntity.setStatus(30);
                if(taskEntity.getActualManHour() > ori_taskEntity.getEstimateManHour() || taskEntity.getActualFinishDate().isAfter(ori_taskEntity.getExpectFinishDate())){ //如果实际工时大于预估工时，或者实际完成时间晚于要求完成时间，则任务标记为异常，需要leader确认
                    taskEntity.setExceptionFlag(1);
                }else{  //无异常，则绩效工时即为实际工时
                    taskEntity.setPerformManHour(taskEntity.getActualManHour());
                }
                taskService.updateById(taskEntity);

                MsgToAfEntity mtae = new MsgToAfEntity();
                mtae.setModular("task");
                mtae.setModularId(taskEntity.getId());
                mtae.setSourceId(ori_taskEntity.getPreSysId());
                mtae.setAction("feedbackFinish");
                mtae.setMsg("任务“" + ori_taskEntity.getTitle() + "”已完成，请确认！");
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
     * 添加任务（用于Alpha审批接口）-post
     * */
    @PostMapping("/kanban/taskFromAf")
    protected TaskEntity newTaskEntityFromAf(
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(taskEntity.getReqDataId())) {
            throw new Exception("源系统主键不能为空");
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
            throw new Exception("任务记录已存在" + oriEntity.getId());
        }

        if (taskEntity.getPriority() == null || taskEntity.getPriority() == 0) {
            taskEntity.setPriority(3);
        }
        if (!StringUtils.isEmpty(taskEntity.getDealer())) {
            //P|1063505,1063568^产品技术中心--俞哲峰
            String focusUser = taskEntity.getDealer();
            if (focusUser.indexOf("^") > 0) {
                focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
                if (focusUser.indexOf("-") > 0) {
                    focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
                }
            }
            //顺利的话此时focusUser已存了姓名
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
            //顺利的话此时focusManager已存了姓名
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
            extInfo += "工作对接人: " + focusManager + "\r\n";
        }
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformUrl()))
            extInfo += "内部平台地址: " + taskEntity.getDevPlatformUrl() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformUserid()))
            extInfo += "内部平台登录名: " + taskEntity.getDevPlatformUserid() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformPwd()))
            extInfo += "登录密码: " + taskEntity.getDevPlatformPwd() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformDbIp()))
            extInfo += "数据库地址: " + taskEntity.getDevPlatformDbIp() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevPlatformDbSid()))
            extInfo += "数据库SID: " + taskEntity.getDevPlatformDbSid() + "\r\n";
        if (!StringUtils.isEmpty(taskEntity.getDevVersionLib())) extInfo += "版本库地址: " + taskEntity.getDevVersionLib();
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
        LOGGER.info("添加任务完成：" + taskEntity.getId() + " : " + taskEntity.getTitle());
        return taskService.selectById(taskEntity.getId());
    }

    /*
     * AF流程-项目经理评价回写
     * */
    @PostMapping("/kanban/finishTaskFromAf")
    protected TaskEntity finishTaskFromAf(
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(taskEntity.getReqDataId())) {
            throw new Exception("源系统主键不能为空");
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
        LOGGER.info("任务已完结：" + currEntity.getId() + " : " + currEntity.getTitle());
        return currEntity;
    }

    /*
     * 修改任务-put
     * */
    @PutMapping("/kanban/task/{preSysFlag}/{preSysId}")
    protected TaskEntity updateTaskEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestBody TaskEntity taskEntity
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("源系统标识及主键不能为空");
        }
        taskEntity.setId(preSysFlag + "_" + preSysId);
        taskEntity.setPreSysFlag(preSysFlag);
        taskEntity.setPreSysId(preSysId);
        boolean update_success_flag = taskService.updateById(taskEntity);
        if (!update_success_flag) throw new Exception("没有定位到任何需要更新的记录！");

        return taskService.selectById(taskEntity.getId());
    }

    /*
     * 修改任务状态-patch
     * */
    @PatchMapping("/kanban/task/{preSysFlag}/{preSysId}/status")
    protected void patchTaskEntityStatus(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId,
            @RequestParam("status") Integer status
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("源系统标识及主键不能为空");
        }
        boolean update_success_flag = taskService.updateTaskStatus(preSysFlag + "_" + preSysId, status);
        if (!update_success_flag) throw new Exception("没有定位到任何需要更新的记录！");
    }

    /*
     * 作废任务-patch
     * */
    @PatchMapping("/kanban/task/{preSysFlag}/{preSysId}/remove")
    protected void removeTaskEntity(
            @PathVariable("preSysFlag") String preSysFlag,
            @PathVariable("preSysId") String preSysId
    ) throws Exception {
        if (StringUtils.isEmpty(preSysFlag) || StringUtils.isEmpty(preSysId)) {
            throw new Exception("源系统标识及主键不能为空");
        }
        boolean update_success_flag = taskService.changeTaskValid(preSysFlag + "_" + preSysId, false);
        if (!update_success_flag) throw new Exception("没有定位到任何需要更新的记录！");
    }

    private final String[] defaultOrder = {"desc"};
    private final String[] defaultSort = {"order_seq_"};

    private final String[] defaultOrderForModDate = {"desc", "desc"};
    private final String[] defaultSortForModDate = {"mod_date_", "order_seq_"};

    /*
     * 查询项目列表-get
     * */
    @GetMapping("/project/search")
    protected BasicDataGridRows searchProject(ProjectWhereLogic whereLogic, @RequestParam("user") String user) {
        //P|0,1063568^产品技术中心-俞哲峰
        //P|0,1277169871100821506^项目一部-梁咏富
        //System.out.println("##############serach user : " + user);
        String focusUser = user;
        //focusUser = "P|0,1063568^产品技术中心-俞哲峰";
        if (focusUser.indexOf("^") > 0) {
            focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
            if (focusUser.indexOf("-") > 0) {
                focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
            }
        }
        //顺利的话此时focusUser已存了姓名
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
     * 查询项目列表(结算场景)-get
     * */
    @GetMapping("/project/searchForFinSettle")
    protected BasicDataGridRows searchProjectForFinSettle(ProjectWhereLogic whereLogic, @RequestParam("user") String user) {
        //P|0,1063568^产品技术中心-俞哲峰
        //P|0,1277169871100821506^项目一部-梁咏富
        //System.out.println("##############serach user : " + user);
        String focusUser = user;
        //focusUser = "P|0,1063568^产品技术中心-俞哲峰";
        if (focusUser.indexOf("^") > 0) {
            focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
            if (focusUser.indexOf("-") > 0) {
                focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
            }
        }
        //顺利的话此时focusUser已存了姓名
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
     * 添加任务（用于Alpha审批接口）-post
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

        HashMap<String, String> afFileIdToFileHeaderIdMap = new HashMap<>();//file_header_id与8位随机码的对应map，用于之后file_header_id与afFileId的映射
        afFileIdToFileHeaderIdMap.put(fileId, fileHeaderId);

        //String filePath = fileManager.prepareAndGetFilePath(fileHeaderEntity.getFileBody());
        //FileDownloadView dv = new FileDownloadView(fileAuditService,,fileHeaderEntity,filePath);

        ObjectNode reqItemJsonObj = new ObjectMapper().createObjectNode();
        reqItemJsonObj.put("sjwcsj", "2020-06-30");
        reqItemJsonObj.put("jbgcsfk", "太难了！");

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
                    String randomFileField = entry.getKey();//8位随机码
                    String fileIdForUpload = entry.getValue();//af回传的fileId
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
     * 查询客户列表-get
     * */
    @GetMapping("/customer/search")
    protected BasicDataGridRows searchCustomer(BaWhereLogic whereLogic, @RequestParam("user") String user) {
        //P|0,1063568^产品技术中心-俞哲峰
        //P|0,1277169871100821506^项目一部-梁咏富
        //System.out.println("##############serach customer user : " + user);
        String focusUser = user;
        //focusUser = "P|0,1063568^产品技术中心-赖明桃";
        if (focusUser.indexOf("^") > 0) {
            focusUser = focusUser.substring(focusUser.indexOf("^") + 1);
            if (focusUser.indexOf("-") > 0) {
                focusUser = focusUser.substring(focusUser.lastIndexOf("-") + 1);
            }
        }
        //顺利的话此时focusUser已存了姓名
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
        whereLogic.setAuthPart(""); //置空，表示走负责、工作人员、访客3个权限
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
//     * 分析文件-post
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
     * 添加相关数据记录-post
     * */
    @PostMapping("/dataJournal")
    protected BasicTokenStoreEntity newBmsDataJournalEntity(
            @RequestBody BmsDataJournalEntity bmsDataJournalEntity
    ) throws Exception {
        BasicTokenStoreEntity rsEntity = new BasicTokenStoreEntity();
        submitAsyncTask(() -> {
            try {
                //reqDataId流程ID作为sourceId
                bmsDataJournalEntity.setSourceId(bmsDataJournalEntity.getReqDataId());
                if (bmsDataJournalEntity.getReqDataId() == null) {
                    bmsDataJournalEntity.setId("test_" + IdWorker.getIdStr());
                } else {
                    bmsDataJournalEntity.setId("af_" + bmsDataJournalEntity.getReqDataId());
                }
                //createUser形如“P|1063505,1063568^产品技术中心--俞哲峰”，进行加工
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

                if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {  //modularInnerId2、modularDesc2这一套是关联了项目的，如果非空，则优先关联项目，否则再检查modularInnerId1、modularDesc1这一套，看是否关联客户
                    bmsDataJournalEntity.setModular("project");
                    bmsDataJournalEntity.setModularInnerId(bmsDataJournalEntity.getModularInnerId2());
                    bmsDataJournalEntity.setModularDesc(bmsDataJournalEntity.getModularDesc2());
                } else if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId1())) {
                    bmsDataJournalEntity.setModular("ba");
                    bmsDataJournalEntity.setModularInnerId(bmsDataJournalEntity.getModularInnerId1());
                    bmsDataJournalEntity.setModularDesc(bmsDataJournalEntity.getModularDesc1());
                }

                int funcFlag = bmsDataJournalEntity.getFuncFlag();
                if (funcFlag == 21) {   //Call-in客户处理流程
                    String valueCode = bmsDataJournalEntity.getDataText3();
                    String productDirection = bmsDataJournalEntity.getDataText4();
                    bmsBaService.changeBaInfoForAfCallin(bmsDataJournalEntity.getModularInnerId1() , valueCode , productDirection , createUserId);

                    BmsBaEventEntity bbee = new BmsBaEventEntity();
                    bbee.setBaId(bmsDataJournalEntity.getModularInnerId());
                    bbee.setTypeId("callin");//Call-in流程
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
                    bbee.setContactPerson("Call-in客户");
                    bbee.setActionUser(createUserName);
                    String subjectStr = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1())) {
                        subjectStr += "主要需求点：\n";
                        subjectStr += bmsDataJournalEntity.getDataText1() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2())) {
                        subjectStr += "其他信息：\n";
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
                } else if (funcFlag == 22) {   //有效商机确认流程
                } else if (funcFlag == 23) {   //客户初次拜访流程
                } else if (funcFlag == 24) {   //客户拜访流程（已停用）
                } else if (funcFlag == 46) {   //销售客户拜访记录
                    BmsBaEventEntity bbee = new BmsBaEventEntity();
                    bbee.setBaId(bmsDataJournalEntity.getModularInnerId());
                    bbee.setTypeId("2748");//上门拜访
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
                        subjectStr += "过程简述：\n";
                        subjectStr += bmsDataJournalEntity.getDataText1() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2())) {
                        subjectStr += "核心关注：\n";
                        subjectStr += bmsDataJournalEntity.getDataText2() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText3())) {
                        subjectStr += "会谈纪要：\n";
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
                } else if (funcFlag == 25) {   //合同文件审批流程
                } else if (funcFlag == 26) {   //销售转实施工作移交流程
                } else if (funcFlag == 27) {   //实施版本申请流程
                } else if (funcFlag == 28) {   //实施服务流程
                } else if (funcFlag == 29) {   //实施外出联系单
                } else if (funcFlag == 30) {   //项目实施转维护流程
                } else if (funcFlag == 31) {   //客户诉求流程
                } else if (funcFlag == 32) {   //费用报销流程
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(33);
                    }
                } else if (funcFlag == 34) {   //费用报销流程（销售）
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(55);
                    }
                } else if (funcFlag == 35) {   //费用报销流程（实施）
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(36);
                    }
                } else if (funcFlag == 45) {   //付款申请单
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(37);
                    }
                } else if (funcFlag == 38) {   //开票申请流程
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(39);
                    }
                } else if (funcFlag == 40) {   //外出联系单
                } else if (funcFlag == 41) {   //工作联系流程
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(42);
                    }
                } else if (funcFlag == 43) {   //技术人员外派申请流程
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(44);
                    }
                } else if (funcFlag == 48) {   //商务费用申请流程
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2())) {
                        bmsDataJournalEntity.setFuncFlag(51);
                    }
                } else if (funcFlag == 52) {   //大客户协助流程
                } else if (funcFlag == 53) {   //费用预支申请流程
                } else if (funcFlag == 54) {   //AF注册客户处理流程
                    BmsBaEventEntity bbee = new BmsBaEventEntity();
                    bbee.setBaId(bmsDataJournalEntity.getModularInnerId());
                    bbee.setTypeId("2750");//内部联系
                    LocalDateTime actionDate = SystemClock.nowDateTime();
                    bbee.setActionDate(actionDate);
                    bbee.setContactPerson("[AF注册流程]");
                    bbee.setActionUser(createUserName);
                    String subjectStr = "";
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1())) {
                        subjectStr += "主要需求点：\n";
                        subjectStr += bmsDataJournalEntity.getDataText1() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2())) {
                        subjectStr += "其他信息：\n";
                        subjectStr += bmsDataJournalEntity.getDataText2() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText3())) {
                        subjectStr += "项目计划：\n";
                        subjectStr += bmsDataJournalEntity.getDataText3() + "\n";
                    }
                    if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText4())) {
                        subjectStr += "其他供应商接触及评价：\n";
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
                //拼合dataText1-4作为dataText
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText1()))
                    dataText += bmsDataJournalEntity.getDataText1() + "\n";  //主要需求点
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText2()))
                    dataText += bmsDataJournalEntity.getDataText2() + "\n";  //其他信息（如信息化现状）
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText3()))
                    dataText += bmsDataJournalEntity.getDataText3() + "\n";  //项目进度计划
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText4()))
                    dataText += bmsDataJournalEntity.getDataText4() + "\n";  //其他供应商接触及评价情况
                if (!StringUtils.isEmpty(bmsDataJournalEntity.getDataText5()))
                    dataText += bmsDataJournalEntity.getDataText5();
                bmsDataJournalEntity.setDataText(dataText);
                //如果数据关联的是项目，且与费用相关，则fialRelatedProjectId直接取关联项目的ID
                if (bmsDataJournalEntity.getModular().equals("project") && BmsHelper.isDataJournalAboutFin(bmsDataJournalEntity.getFuncFlag())) {
                    bmsDataJournalEntity.setFinalRelatedProjectId(bmsDataJournalEntity.getModularInnerId());
                }
                if (bmsDataJournalEntity.getForbidFillExtContent() != null && bmsDataJournalEntity.getForbidFillExtContent() == 1) {
                    //禁止同时填充流程信息
                } else {
                    try {
                        AfWorkflowInstModel awim = afWfMaster.getWorkflowInstanceData(Long.parseLong(bmsDataJournalEntity.getReqDataId()));
                        //Map<String, AfInstanceFormItemValueModel> formData = afWfMaster.getInstanceData(Long.parseLong(bmsDataJournalEntity.getReqDataId())).getItemValues();
                        bmsDataJournalEntity.setExtContent(jsonPrintHelper.printQuite(awim));
                    } catch (Exception e1) {
                        LOGGER.error("获取流程全部数据有误（" + bmsDataJournalEntity.getReqDataId() + "）！" + e1.getMessage());
                    }
                }
                dataJournalService.insert(bmsDataJournalEntity);
                BmsDataJournalEntity updatedEntity = dataJournalService.selectById(bmsDataJournalEntity.getId());

                LOGGER.info("添加相关数据记录完成：" + objMapper.writeValueAsString(updatedEntity));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        rsEntity.setToken("1");
        return rsEntity;
    }

    /*
     * 补充流程扩展信息（反调AF获取流程全部数据接口）-post
     * */
    @PostMapping("/fillDataJournalExtContent")
    protected void fillDataJournalExtContent(
            @RequestBody BmsDataJournalEntity reqEntity
    ) throws Exception {
        submitAsyncTask(() -> {
            try {
                String checkId = "af_" + reqEntity.getReqDataId();
                BmsDataJournalEntity checkEntity = dataJournalService.selectById(checkId);
                if (checkEntity != null) { //确认数据记录存在
                    AfWorkflowInstModel awim = afWfMaster.getWorkflowInstanceData(Long.parseLong(reqEntity.getReqDataId()));
                    String newExtContent = jsonPrintHelper.printQuite(awim);
                    dataJournalService.changeExtContent(checkId, newExtContent);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                LOGGER.info("补充流程扩展信息完成：" + objectMapper.writeValueAsString(checkId));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /*
     * 获取任务日历列表-get
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
     * 钉钉对接 考勤审批通过-post
     * */
    @PostMapping("/eddAttendanceApproveFinish")
    protected void eddAttendanceApproveFinish(
            @RequestBody OapiAttendanceApproveFinishRequest req
    ) throws Exception {
        //createUser形如“P|1063505,1063568^产品技术中心--俞哲峰”，进行加工
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
                LOGGER.info("考勤审批完成：" + req.getUserid() + "%%" + objectMapper.writeValueAsString(tdv));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /*
     * 钉钉对接 考勤审批通过 时间段批量-post
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
                //createUser形如“P|0,1063568^产品技术中心-俞哲峰”，进行加工
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
                        LOGGER.info("考勤审批完成：" + oaafr.getUserid() + "%%" + objectMapper.writeValueAsString(tdv));
                    }
                }
                LOGGER.info("考勤审批全部完成：" + req.getUserid() + "%%");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

    }

    /*
     * 钉钉对接 考勤审批通过 人员批量-post
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
                //createUser形如“P|0,1063568^产品技术中心-俞哲峰;P|1198814198936625154,1198814200099663874^项目三部-王道义”，进行加工
                ArrayList<String> userIdList = new ArrayList<>();
                if (!StringUtils.isEmpty(req.getUserid())) {
                    if ((req.getUserid()).indexOf(";") > 0) {  //说明有多个
                        userIdList = new ArrayList<String>(Arrays.asList((req.getUserid()).split("\\;")));
                    } else {
                        userIdList.add(req.getUserid());
                    }
                }
                if ("加班延休".equals(req.getApplyType())) {
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
                        LOGGER.info("考勤审批完成：" + oaafr.getUserid() + "%%" + objectMapper.writeValueAsString(tdv));
                    }
                } else if ("补上班卡".equals(req.getApplyType()) || "补下班卡".equals(req.getApplyType())) {

                    String checkFromTime = req.getFromTime();
                    if (checkFromTime.length() > 10) {
                        checkFromTime = checkFromTime.substring(0, 10);
                    }
                    String checkWorkDateStr = checkFromTime;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
                    Long fromDateLong = (simpleDateFormat.parse(checkFromTime + " 08:00:00")).getTime();
                    Long toDateLong = (simpleDateFormat.parse(checkFromTime + " 18:00:00")).getTime();
                    for (String checkUserId : userIdList) {
                        Long attendancePunchId = 0L;
                        Date attendancePunchPlanCheckTime = null;
                        Date userCheckTime = null;

                        String nodeCreateUserId = BmsHelper.getUserIdByAfUserStr(userService, checkUserId);

                        List<OapiAttendanceScheduleListbyusersResponse.TopScheduleVo> attendanceScheduleList = eddForBmsMaster.getAttendanceScheduleList(nodeCreateUserId, nodeCreateUserId, fromDateLong, toDateLong);
                        for (OapiAttendanceScheduleListbyusersResponse.TopScheduleVo tsv : attendanceScheduleList) {
                            if ("补上班卡".equals(req.getApplyType()) && "OnDuty".equals(tsv.getCheckType())) {
                                attendancePunchId = tsv.getId();
                                attendancePunchPlanCheckTime = tsv.getPlanCheckTime();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(attendancePunchPlanCheckTime);
                                cal.set(Calendar.MINUTE, (cal.get(Calendar.MINUTE) - 1));
                                userCheckTime = cal.getTime();
                                break;
                            } else if ("补下班卡".equals(req.getApplyType()) && "OffDuty".equals(tsv.getCheckType())) {
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
//                        if("补上班卡".equals(req.getApplyType())){
//                            userCheckTime = checkWorkDateStr + " 08:00:00";
//                        }else if("补下班卡".equals(req.getApplyType())){
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
                LOGGER.info("考勤审批全部完成：" + req.getUserid() + "%%");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

    }

    /*
     * 日志推送项目钉钉群-post
     * */
    @PostMapping("/sendProjectDailyToDingGroup")
    protected void sendProjectDailyToDingGroup(
            @RequestBody ProjectDailyEntity projectDailyEntity
    ) throws Exception {
        submitAsyncTask(() -> {
            try {

                BmsProjectEntity bmsProjectEntity = bmsProjectService.selectById(projectDailyEntity.getProjectId());
                if (bmsProjectEntity == null || StringUtils.isEmpty(bmsProjectEntity.getDingGroupSecret()) || StringUtils.isEmpty(bmsProjectEntity.getDingGroupAccessToken())) {
                    LOGGER.info(projectDailyEntity.getProjectName() + "钉钉群信息不全，无法发送消息！");
                    return;
                }

                //String chatAccessToken = "218a4936d0ee947ba0225791750cc9164c9b7c4a27174a6ac4370cfa39259047";
                //String groupSecret = "SECd05c7c7932e331758468b831aad5c3294617dc573e72c0e05deb4be6b3b5549a";
                String chatAccessToken = bmsProjectEntity.getDingGroupAccessToken();
                String groupSecret = bmsProjectEntity.getDingGroupSecret();

                String mdTitle = "“" + projectDailyEntity.getProjectName() + "”" + projectDailyEntity.getFillDate() + "项目日报";

                String briefProjectName = projectDailyEntity.getProjectName();
                if (briefProjectName.length() > 12) {
                    briefProjectName = briefProjectName.substring(0, 12) + "...";
                }

                String mdText = "# " + briefProjectName + "\n";
                mdText += "# " + projectDailyEntity.getFillDate() + "项目日报\n";

                String fillUser = projectDailyEntity.getFillUser();
                if (fillUser.indexOf("-") > 0) {
                    fillUser = fillUser.substring(fillUser.lastIndexOf("-") + 1);
                }
                mdText += "### 填报人：<font color=#999999 size=2 weight=100>" + fillUser + "</font>\n";
                mdText += "### 总体状态：<font color=#999999 size=2 weight=100>" + projectDailyEntity.getTotalStatus() + "</font>\n";
                mdText += "### 当前阶段：<font color=#999999 size=2 weight=100>" + projectDailyEntity.getCurrPhase() + "</font>\n";
                mdText += "### 下一款项节点：<font color=#999999 size=2 weight=100>" + projectDailyEntity.getNextPayStep() + "</font>\n";
                mdText += "### 款项预计时间：<font color=#999999 size=2 weight=100>" + projectDailyEntity.getNextPayDate() + "</font>\n";
                String todayResults = projectDailyEntity.getTodayResults();
                todayResults = StringUtils.defaultIfBlank(todayResults, "无");
                todayResults = todayResults.replaceAll("\\\\n", "\n");
                todayResults = todayResults.replaceAll("\\n", "  \n> ");
                mdText += "### 今日成果：\n> " + todayResults + "\n";

                String needSupportStuffs = projectDailyEntity.getNeedSupportStuffs();
                needSupportStuffs = StringUtils.defaultIfBlank(needSupportStuffs, "无");
                needSupportStuffs = needSupportStuffs.replaceAll("\\\\n", "\n");
                needSupportStuffs = needSupportStuffs.replaceAll("\\n", "  \n> ");
                mdText += "### 需支持事项：\n> " + needSupportStuffs + "\n";
                String problems = projectDailyEntity.getProblems();
                problems = StringUtils.defaultIfBlank(problems, "无");
                problems = problems.replaceAll("\\\\n", "\n");
                problems = problems.replaceAll("\\n", "  \n> ");
                mdText += "### 问题或风险：\n> " + problems + "\n";
                String tomorrowPlans = projectDailyEntity.getTomorrowPlans();
                tomorrowPlans = StringUtils.defaultIfBlank(tomorrowPlans, "无");
                tomorrowPlans = tomorrowPlans.replaceAll("\\\\n", "\n");
                tomorrowPlans = tomorrowPlans.replaceAll("\\n", "  \n> ");
                mdText += "### 明日计划：\n> " + tomorrowPlans + "\n";
                BmsHelper.sendMsgToDingGroupInMd(restTemplate, chatAccessToken, groupSecret, mdTitle, mdText);
                LOGGER.info("推送钉钉项目群消息完成：" + projectDailyEntity.getProjectId() + "##" + projectDailyEntity.getProjectName());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /*
     * AF流程交互，根据客户数据回填项目数据-post
     * */
    @PostMapping("/adjustProjectInfoByBaInfo")
    protected BasicTokenStoreEntity adjustProjectInfoByBaInfo(
            @RequestBody BmsDataJournalEntity bmsDataJournalEntity
    ) throws Exception {
        BasicTokenStoreEntity btse = new BasicTokenStoreEntity();
        btse.setId(bmsDataJournalEntity.getModularInnerId2());
        btse.setToken(bmsDataJournalEntity.getModularDesc2());
        if (StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId1()) || StringUtils.isEmpty(bmsDataJournalEntity.getModularDesc1())) {//modularInnerId1、modularDesc1这一套是关联了客户的，如果为空，则不介入
        } else if (!StringUtils.isEmpty(bmsDataJournalEntity.getModularInnerId2()) && !StringUtils.isEmpty(bmsDataJournalEntity.getModularDesc2())) { //modularInnerId2、modularDesc2这一套是关联了项目的，如果不为空，则不介入
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
                //如果当前时间落在该项目的财务结算区间内，则该项目命中
                if (
                        projectStartDate.isBefore(LocalDate.now()) && (
                                bpe.getFinCloseDate() == null || (LocalDate.now()).isBefore(bpe.getFinCloseDate())
                        )
                ) {
                    hitProjectList.add(bpe);
                }
            }
            //当且仅当只有一个项目命中在
            if (hitProjectList.size() == 1) {
                BmsProjectEntity hitProjectEntity = hitProjectList.get(0);
                btse.setId(hitProjectEntity.getId());
                btse.setToken(hitProjectEntity.getProjectName());
            }
        }
        return btse;
    }

    /*
     * Alpha审批的Callin流程，通过若干组件值计算经办销售人员姓名-post
     * */
    @PostMapping("/computeSalesManagerNameForAfCallin")
    protected BasicTokenStoreEntity computeSalesManagerNameForAfCallin(
            @RequestParam("salesDealerDecision") String salesDealerDecision ,
            @RequestParam("salesDealer") String salesDealer ,
            @RequestParam("salesManager") String salesManager
    ) throws Exception {
        BasicTokenStoreEntity rsEntity = new BasicTokenStoreEntity();
        String dealerStr = "";
        if(salesDealerDecision.contains("本人")){ //分配了本人，则取销售经理环节审批人
            dealerStr = salesManager;
        }else{ //分配了其他人，则取交办销售人员的值
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
     * Alpha审批的Callin流程，通过负责人姓名自动添加客户负责人权限-post
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

    @PostMapping("/pushAfWfAfterEcoUiBot")  //在前端机器人走完后，自动驱动流程（适用于“Alpha审批”）
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

    @PostMapping("/pushAfhwcWfAfterEcoUiBot") //在前端机器人走完后，自动驱动流程（适用于华为云SaaS）
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
     * adjustDataJournalExtContent-post  修正因为af接口问题导致ext_content为空的情形
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
                    LOGGER.error("获取流程全部数据有误（" + bdje.getSourceId() + "）！" + e1.getMessage());
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
                        //如果该费用的发生时间落在该项目的财务结算区间内，则该项目命中
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
                    //如果finalRelatedProjectIdStr不为空，且内容中不含“#”，说明只有一个projectId，则前缀加@
                    if (!finalRelatedProjectIdStr.equals("") && finalRelatedProjectIdStr.indexOf("#") < 0) {
                        finalRelatedProjectIdStr = "@" + finalRelatedProjectIdStr;
                    }
                    if (!finalRelatedProjectIdStr.equals("")) {
                        LOGGER.info("第" + i + "个：费用ba:" + bdje.getModularDesc() + "#" + bdje.getId() + "#" + "关联项目" + finalRelatedProjectIdStr);
                        BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
                        updateDataJournalEntity.setId(bdje.getId());
                        updateDataJournalEntity.setFinalRelatedProjectId(finalRelatedProjectIdStr);
                        dataJournalService.updatePartialColumnById(updateDataJournalEntity, validUpdateCols);
                    } else {
                        LOGGER.info("第" + i + "个：费用ba:" + bdje.getModularDesc() + "#" + bdje.getId() + "#" + "没找到关联");
                    }
                } catch (Exception e1) {
                    LOGGER.error("数据有误（" + bdje.getSourceId() + "）！" + e1.getMessage());
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
                    BmsProjectEntity latest_bpe = null;//存放最近的项目
                    for (String checkProjectId : checkProjectIdList) {
                        BmsProjectEntity bpe = bmsProjectService.selectById(checkProjectId);
                        if (latest_bpe == null) {
                            latest_bpe = bpe;
                        } else {
                            LocalDate latest_bpe_StartDate = LocalDate.parse(latest_bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            LocalDate check_node_bpe_StartDate = LocalDate.parse(bpe.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            //如果当前检查的项目，比latest_bpe项目的开始时间晚，则将当前检查的项目作为样把的latest_bpe
                            if (latest_bpe_StartDate.isBefore(check_node_bpe_StartDate)) {
                                latest_bpe = bpe;
                            }
                        }
                    }
                    LOGGER.info("第" + i + "个：费用ba:" + bdje.getModularDesc() + "#" + bdje.getId() + "#" + "关联项目" + latest_bpe.getId());
                    BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
                    updateDataJournalEntity.setId(bdje.getId());
                    updateDataJournalEntity.setFinalRelatedProjectId(bdje.getFinalRelatedProjectId() + "@" + latest_bpe.getId());
                    dataJournalService.updatePartialColumnById(updateDataJournalEntity, validUpdateCols);
                } catch (Exception e1) {
                    LOGGER.error("数据有误（" + bdje.getSourceId() + "）！" + e1.getMessage());
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

//                        //如果费用关联的项目只有一个，那么就关联这个项目
//                        if(al.size()==1){
//                            BmsProjectAssociationEntity bpae = al.get(0);
//                            BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
//                            updateDataJournalEntity.setId(bdje.getId());
//                            updateDataJournalEntity.setFinalRelatedProjectId("#"+bpae.getProjectId());
//                            dataJournalService.updatePartialColumnById(updateDataJournalEntity,validUpdateCols);
//                        }

//                        //如果费用发生时间比所关联的项目中最早的那个项目的开始时间更早，那么就关联这个最早的项目
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
//                        //该比费用产生的时间比最早的项目的开始时间更早，则命中
//                        if((bdje.getCreateDate().toLocalDate()).isBefore(ealist_bpeStartDate)){
//                            LOGGER.info("第"+i+"个：费用ba:"+bdje.getModularDesc()+"#"+bdje.getId()+"#");
//                            BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
//                            updateDataJournalEntity.setId(bdje.getId());
//                            updateDataJournalEntity.setFinalRelatedProjectId("@"+ealist_bpe.getId());
//                            dataJournalService.updatePartialColumnById(updateDataJournalEntity,validUpdateCols);
//                        }else{
//                            LOGGER.info("第"+i+"个：费用ba:"+bdje.getModularDesc()+"#"+bdje.getId()+"#");
//                            BmsDataJournalEntity updateDataJournalEntity = new BmsDataJournalEntity();
//                            updateDataJournalEntity.setId(bdje.getId());
//                            updateDataJournalEntity.setFinalRelatedProjectId("#");
//                            dataJournalService.updatePartialColumnById(updateDataJournalEntity,validUpdateCols);
//                        }


                    }
                } catch (Exception e1) {
                    LOGGER.error("数据有误（" + bdje.getSourceId() + "）！" + e1.getMessage());
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
            //获取6分钟前的时间
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
            btse.setToken("类型选项" + (i + 1));
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
        String approvalBpaId = formValueObj.get("Te_2").get("value").asText();  //bpa中待发布流程的id

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
                if(checkName.contains("销售方识别号")){
                    iie.setSellerId(checkValue);
                }else if(checkName.contains("销售方名称")){
                    iie.setSellerName(checkValue);
                }else if(checkName.contains("购买方识别号")){
                    iie.setBuyerId(checkValue);
                }else if(checkName.contains("购买方名称")){
                    iie.setBuyerName(checkValue);
                }else if(checkName.equals("发票代码")){
                    iie.setInvoiceCode(checkValue);
                }else if(checkName.contains("发票名称")){
                    iie.setInvoiceName(checkValue);
                }else if(checkName.equals("发票号码")){
                    iie.setInvoiceNo(checkValue);
                }else if(checkName.contains("开票日期")){
                    iie.setMakeOutDate(checkValue);
                }else if(checkName.contains("机器编号")){
                    iie.setMachineCode(checkValue);
                }else if(checkName.contains("校验码")){
                    iie.setProofCode(checkValue);
                }else if(checkName.contains("服务名称")){ //货物或应税劳务、服务名称
                    iie.setGoodsName(checkValue);
                }else if(checkName.contains("数量")){
                    iie.setGoodsNum(checkValue);
                }else if(checkName.contains("单价")){
                    iie.setSinglePriceWithoutTax(checkValue);
                }else if(checkName.equals("金额")){
                    iie.setPriceAmountWithoutTax(checkValue);
                }else if(checkName.contains("税率")){
                    iie.setTaxRate(checkValue);
                }else if(checkName.contains("税额")){
                    iie.setTaxAmount(checkValue);
                }else if(checkName.contains("合计金额")){
                    //¥75.21
                    if(!checkValue.substring(0,1).matches("[0-9]+")){
                        checkValue = checkValue.substring(1);
                    }
                    iie.setTotalAmountWithoutTax(checkValue);
                }else if(checkName.contains("合计税额")){
                    iie.setTotalTaxAmount(checkValue);
                }else if(checkName.contains("价税合计")){
                    iie.setRealTotalAmountUpper(checkValue);
                }else if(checkName.contains("小写金额")){
                    //"¥88.00
                    if(!checkValue.substring(0,1).matches("[0-9]+")){
                        checkValue = checkValue.substring(1);
                    }
                    iie.setRealTotalAmount(checkValue);
                }else if(checkName.contains("销售方地址")){
                    iie.setSellerAddressPhone(checkValue);
                }else if(checkName.contains("销售方开户行")){
                    iie.setSellerBankInfo(checkValue);
                }else if(checkName.contains("开票人")){
                    iie.setMakeOutPerson(checkValue);
                }else if(checkName.contains("发票消费类型")){
                    iie.setInvoiceConsumeType(checkValue);
                }else if(checkName.contains("发票类型")){
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
        if(content.equals("项目预算")){
            JSONObject contentObj = new JSONObject();
            contentObj.put("content","项目预算：" + baEntity.getProjectBudget() + "万元");
            reObj.put("text",contentObj);
            reObj.put("msgtype","text");
        }else{
            JSONObject linkObj = new JSONObject();
            linkObj.put("title" , "点此打开客户详情卡片");
            linkObj.put("text" , baEntity.getBaName());
            linkObj.put("messageUrl" , "http://183.129.233.90:8890/#/webRoot/webPlatform/?compFlag=baInfo&jumpBaId="+baId);
            linkObj.put("picUrl" , "http://183.129.233.90:8890/assets/img/crm.png");
            reObj.put("link" , linkObj);
            reObj.put("msgtype","link");
        }
        return reObj;
    }
}
