package com.econage.extend.modular.bms.kanban.web;

import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.SystemClock;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.expReport.util.SalesProjectReportDownloadRender;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.service.BmsOpActionService;
import com.econage.extend.modular.bms.kanban.component.expReport.util.TaskListDownloadRender;
import com.econage.extend.modular.bms.kanban.entity.*;
import com.econage.extend.modular.bms.kanban.service.*;
import com.econage.extend.modular.bms.kanban.trivial.wherelogic.*;
import com.econage.extend.modular.bms.project.entity.BmsProjectEntity;
import com.econage.extend.modular.bms.project.service.BmsProjectService;
import com.econage.extend.modular.bms.util.BmsHelper;
import com.econage.extend.modular.bms.util.BmsUtil;
import com.econage.extend.modular.bms.util.MsgToAfEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/bms/kanban")
public class KanbanWebEndpoint extends BasicControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(KanbanWebEndpoint.class);
    private RequireService requireService;
    private TaskService taskService;
    private TaskCalendarService taskCalendarService;
    private UserUnionQuery userService;
    private BmsOpActionService actionService;
    private ProductService productService;
    private IterationProjectService iterationProjectService;
    private BmsProjectService bmsProjectService;
    private RocketMQTemplate rocketMQTemplate;
    @Autowired(required = false)
    protected void setRocketMQTemplateService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
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
    protected void setTaskCalendarService(TaskCalendarService taskCalendarService) {
        this.taskCalendarService = taskCalendarService;
    }
    @Autowired
    protected void setActionService(BmsOpActionService actionService) {
        this.actionService = actionService;
    }
    @Autowired
    protected void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    protected void setIterationProjectService(IterationProjectService iterationProjectService) {
        this.iterationProjectService = iterationProjectService;
    }
    @Autowired
    protected void setProjectService(BmsProjectService bmsProjectService) {
        this.bmsProjectService = bmsProjectService;
    }
    private final String[] defaultOrder = {"asc","desc"};
    private final String[] defaultSort = {"priority","order_seq_"};

    private final String[] normalDefaultOrder = {"desc"};
    private final String[] normalDefaultSort = {"order_seq_"};

    private final int defaultRows = 50;
    /*
     * ????????????-post
     * */
    @PostMapping("/require")
    protected RequireEntity newRequireEntity(
            @RequestBody RequireEntity requireEntity
    ){
        requireService.insert(requireEntity);
        return requireService.selectById(requireEntity.getId());
    }

    /*
     * ????????????-put
     * */
    @PutMapping("/require/{requireId}")
    protected RequireEntity updateRequireEntity(
            @PathVariable("requireId") String requireId,
            @RequestBody RequireEntity requireEntity
    ){
        requireEntity.setId(requireId);
        requireService.updateById(requireEntity);
        return requireService.selectById(requireId);
    }

    /*
     * ??????????????????-patch
     * */
    @PatchMapping("/require/{requireId}/status")

    protected void patchRequireEntityStatus(
            @PathVariable("requireId") String requireId,
            @RequestParam("action") String action
    ){
        requireService.changeRequireValid(requireId , BooleanUtils.toBooleanObject(action));
    }

    /*
     * ????????????????????????-get
     * */
    @GetMapping("/require/{requireId}")
    protected RequireEntity getSingleRequireEntity(
            @PathVariable("requireId") String requireId
    ){
        RequireEntity re = requireService.selectById(requireId);
        re.setStatusDesc(BmsUtil.getRequireStatusDesc(re.getStatus()));
        return re;
    }
    /*
     * ????????????????????????-get
     * */
    @GetMapping("/require/count")
    protected Integer countRequire(
            RequireWhereLogic whereLogic , @RequestParam(value = "priorityStr" , required = false) String priorityStr , @RequestParam(value = "statusStr" , required = false) String statusStr
    ){
        whereLogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        if(whereLogic.getPage()==0) whereLogic.setPage(1);
        if(whereLogic.getRows()==0) whereLogic.setRows(1000);
        if(!StringUtils.isEmpty(priorityStr)){
            String[] priV = priorityStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : priV){
                v.add(Integer.parseInt(node));
            }
            whereLogic.setPriorityList(v);
        }
        if(!StringUtils.isEmpty(statusStr)){
            String[] staV = statusStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : staV){
                v.add(Integer.parseInt(node));
            }
            whereLogic.setStatusList(v);
        }
        return requireService.selectCountByWhereLogic(whereLogic);
    }
    /*
     * ????????????????????????-get
     * */
    @GetMapping("/require/search")
    protected BasicDataGridRows searchRequireList(
            RequireWhereLogic whereLogic , @RequestParam(value = "priorityStr" , required = false) String priorityStr , @RequestParam(value = "statusStr" , required = false) String statusStr
    ){
        whereLogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        if(whereLogic.getPage()==0) whereLogic.setPage(1);
        if(whereLogic.getRows()==0) whereLogic.setRows(1000);
        if(!StringUtils.isEmpty(priorityStr)){
            String[] priV = priorityStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : priV){
                v.add(Integer.parseInt(node));
            }
            whereLogic.setPriorityList(v);
        }
        if(!StringUtils.isEmpty(statusStr)){
            String[] staV = statusStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : staV){
                v.add(Integer.parseInt(node));
            }
            whereLogic.setStatusList(v);
        }
        return BasicDataGridRows.create()
                .withRows(requireService.selectListByWhereLogic(whereLogic))
                .withTotal(requireService.selectCountByWhereLogic(whereLogic));
    }

    /*
     * ????????????-post
     * */
    @PostMapping("/task")
    protected TaskEntity newTaskEntity(
            @RequestBody TaskEntity taskEntity
    ){
        taskEntity.setFollowuper(taskService.getMyUserId());
        taskService.insert(taskEntity);
        if(taskEntity.getRequireId()!=null){
            requireService.reSetSingleRequireManHour(taskEntity.getRequireId());
        }
        return taskService.selectById(taskEntity.getId());
    }
    /*
     * ????????????-put
     * */
    @PutMapping("/task/{taskId}")
    protected TaskEntity updateTaskEntity(
            @PathVariable("taskId") String taskId,
            @RequestBody TaskEntity taskEntity
    ){
        TaskEntity oriTaskEntity = taskService.selectById(taskId);
        taskEntity.setId(taskId);
        taskService.updateById(taskEntity);
        TaskEntity te = taskService.selectById(taskId);
        if(te.getRequireId()!=null){
            requireService.reSetSingleRequireManHour(te.getRequireId());
        }
        if(!StringUtils.isEmpty(oriTaskEntity.getDealer()) && !StringUtils.isEmpty(taskEntity.getDealer()) && !(oriTaskEntity.getDealer()).equals(taskEntity.getDealer())){ //??????????????????????????????????????????????????????????????????
            //????????????taskEntity????????????????????????????????????????????????dealer??????????????????
            String newDealerName = userService.selectUserMi(taskEntity.getDealer());
            taskEntity.setDealerName(newDealerName);
            MsgToAfEntity mtae = new MsgToAfEntity();
            mtae.setModular("task");
            mtae.setModularId(te.getId());
            mtae.setSourceId(te.getPreSysId());
            mtae.setAction("assignTaskDealer");
            mtae.setMsg("?????????" + te.getTitle() + "???????????????" + taskEntity.getDealerName() + "???????????????");
            mtae.setToDealerName(taskEntity.getDealerName());
            String mqTopic = "BMS:" + te.getPreSysFlag();
            BmsHelper.sendMsgToAf(rocketMQTemplate , mtae , mqTopic , userService);
        }
        return te;
    }
    /*
     * ??????????????????-put
     * */
    @PutMapping("/task/leaderConfirm/{taskId}")
    protected TaskEntity leaderConfirmTaskEntity(
            @PathVariable("taskId") String taskId,
            @RequestBody TaskEntity taskEntity
    ){
        taskEntity.setId(taskId);
        taskEntity.setLeaderConfirmCreateTime(SystemClock.nowDateTime());
        taskEntity.setLeaderConfirmInputer(taskService.getMyUserId());
        taskService.updateById(taskEntity);
        TaskEntity te = taskService.selectById(taskId);
        if(te.getRequireId()!=null){
            requireService.reSetSingleRequireManHour(te.getRequireId());
        }
        return te;
    }
    /*
     * ??????????????????-patch
     * */
    @PatchMapping("/task/{taskId}/status")

    protected void patchTaskEntityStatus(
            @PathVariable("taskId") String taskId,
            @RequestParam("action") String action
    ){
        taskService.changeTaskValid(taskId , BooleanUtils.toBooleanObject(action));
        TaskEntity te = taskService.selectById(taskId);
        if(te.getRequireId()!=null){
            requireService.reSetSingleRequireManHour(te.getRequireId());
        }
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
     * ????????????????????????
     * */
    @GetMapping("/task/count")
    protected Integer searchTaskCount(
            TaskWhereLogic whereLogic , @RequestParam(value = "userIdStr" , required = false) String userIdStr ,
            @RequestParam(value = "calendarDateArrangeStr" , required = false) String calendarDateArrangeStr ,
            @RequestParam(value = "priorityStr" , required = false) String priorityStr ,
            @RequestParam(value = "statusStr" , required = false) String statusStr
    ){
        whereLogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        if(whereLogic.getPage()==0) whereLogic.setPage(1);
        if(whereLogic.getRows()==0) whereLogic.setRows(1000);
        if(!StringUtils.isEmpty(userIdStr)){
            whereLogic.setUserSelectAuthExpress(Arrays.asList(userIdStr.split(",")));
        }
        if(!StringUtils.isEmpty(calendarDateArrangeStr)){
            whereLogic.setTaskCalendarArrangeSelectAuthExpress(Arrays.asList(calendarDateArrangeStr.split(",")));
        }
        if(!StringUtils.isEmpty(priorityStr)){
            String[] priV = priorityStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : priV){
                v.add(Integer.parseInt(node));
            }
            whereLogic.setPriorityList(v);
        }
        if(!StringUtils.isEmpty(statusStr)){
            String[] statusV = statusStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : statusV){
                v.add(Integer.parseInt(node));
            }
            whereLogic.setStatusList(v);
        }
        return taskService.selectCountByWhereLogic(whereLogic);
    }
    /*
     * ????????????????????????-get
     * */
    @GetMapping("/task/search")
    protected BasicDataGridRows searchTaskList(
            TaskWhereLogic whereLogic , @RequestParam(value = "userIdStr" , required = false) String userIdStr ,
            @RequestParam(value = "calendarDateArrangeStr" , required = false) String calendarDateArrangeStr ,
            @RequestParam(value = "priorityStr" , required = false) String priorityStr ,
            @RequestParam(value = "statusStr" , required = false) String statusStr
    ){
        whereLogic = taskService.setWhereLogicBeforeSearch(whereLogic , userIdStr , calendarDateArrangeStr , priorityStr , statusStr , defaultOrder , defaultSort);
//        whereLogic.setStatusTarget(true);
//        if(ArrayUtils.isEmpty(whereLogic.getSort())){
//            whereLogic.setSort(defaultSort);
//        }
//        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
//            whereLogic.setOrder(defaultOrder);
//        }
//        if(whereLogic.getPage()==0) whereLogic.setPage(1);
//        if(whereLogic.getRows()==0) whereLogic.setRows(1000);
//        if(!StringUtils.isEmpty(userIdStr)){
//            whereLogic.setUserSelectAuthExpress(Arrays.asList(userIdStr.split(",")));
//        }
//        if(!StringUtils.isEmpty(calendarDateArrangeStr)){
//            whereLogic.setTaskCalendarArrangeSelectAuthExpress(Arrays.asList(calendarDateArrangeStr.split(",")));
//        }
//        if(!StringUtils.isEmpty(priorityStr)){
//            String[] priV = priorityStr.split(",");
//            ArrayList<Integer> v = new ArrayList<>();
//            for(String node : priV){
//                v.add(Integer.parseInt(node));
//            }
//            whereLogic.setPriorityList(v);
//        }
//        if(!StringUtils.isEmpty(statusStr)){
//            String[] statusV = statusStr.split(",");
//            ArrayList<Integer> v = new ArrayList<>();
//            for(String node : statusV){
//                v.add(Integer.parseInt(node));
//            }
//            whereLogic.setStatusList(v);
//        }
        return BasicDataGridRows.create()
                .withRows(taskService.selectListByWhereLogic(whereLogic))
                .withTotal(taskService.selectCountByWhereLogic(whereLogic));
    }
    /*
     * ????????????????????????-get
     * */
    @GetMapping("/task/usedManHour/{taskId}")
    protected Double getTaskUserdManHour(
            @PathVariable("taskId") String taskId , @RequestParam("exceptCalendarId") String exceptCalendarId
    ){
        return taskService.sumUsedManHour(taskId , exceptCalendarId);
    }
    /*
     * ??????????????????????????????-get
     * */
    @GetMapping("/org/userList/{org_id}")
    protected List<UserEntity> getUserListByOrg(@PathVariable("org_id") String org_id){
        return userService.selectValidListWithOrderInDept(org_id);
    }
    /*
     * ????????????????????????-post
     * */
    @PostMapping("/taskCalendar/plan")
    protected TaskCalendarEntity newTaskCalenderEntityForPlan(
            @RequestBody TaskCalendarEntity taskCalendarEntity
    ){
        taskCalendarEntity.setPlanCreateTime(SystemClock.nowDateTime());
        taskCalendarEntity.setPlanInputer(taskCalendarService.getMyUserId());

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskCalendarEntity.getTaskId());
        taskEntity.setStatus(20);
        taskEntity.setDealer(taskCalendarEntity.getDealer());
        taskService.updateById(taskEntity);

        taskCalendarService.insert(taskCalendarEntity);

        return taskCalendarService.selectById(taskCalendarEntity.getId());
    }

    /*
     * ????????????????????????-get
     * */
    @GetMapping("/taskCalendar/search")
    protected BasicDataGridRows searchTaskCalendarList(
            TaskCalendarWhereLogic whereLogic , @RequestParam(value = "dealer_str" , required = false) String dealer_str
    ){
        if(!StringUtils.isEmpty(dealer_str)){
            whereLogic.setDealers(Arrays.asList(dealer_str.split(",")));
        }
        whereLogic.setPage(1);
        whereLogic.setRows(1000);
        whereLogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        return BasicDataGridRows.create()
                .withRows(taskCalendarService.selectListByWhereLogic(whereLogic));

    }
    /*
     * ??????????????????????????????-get
     * */
    @GetMapping("/taskCalendar/{taskCalendarId}")
    protected TaskCalendarEntity getSingleTaskCalendarEntity(
            @PathVariable("taskCalendarId") String taskCalendarId
    ){
        return taskCalendarService.selectById(taskCalendarId);
    }
    /*
     * ??????????????????-put
     * */
    @PutMapping("/taskCalendar/feedback/{taskCalendarId}")
    protected TaskCalendarEntity updateTaskCalendarEntity(
            @PathVariable("taskCalendarId") String taskCalendarId,
            @RequestBody TaskCalendarEntity taskCalendarEntity
    ) throws Exception {
        taskCalendarEntity.setId(taskCalendarId);
        taskCalendarEntity.setFeedbackCreateTime(SystemClock.nowDateTime());
        taskCalendarEntity.setFeedbackInputer(taskCalendarService.getMyUserId());

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
                if("af".equals(ori_taskEntity.getPreSysFlag())){ //??????af??????????????????????????????
                    taskService.writeBackTaskFeedbackToAf(ori_taskEntity , taskEntity.getActualFinishDate() , taskCalendarEntity);
                }
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
        return taskCalendarService.selectById(taskCalendarId);
    }
    /*
     * ??????????????????-patch
     * */
    @PatchMapping("/taskCalendar/{taskCalendarId}/status")
    protected void patchTaskCalendarStatus(
            @PathVariable("taskCalendarId") String taskCalendarId,
            @RequestParam("action") String action
    ){
        taskCalendarService.changeTaskCalendarValid(taskCalendarId , BooleanUtils.toBooleanObject(action));
    }
    /*
//    /*
//     * ??????????????????-post
//     * */
//    @PostMapping("/taskCalendar")
//    protected TaskCalendarEntity newTaskCalenderEntity(
//            @RequestBody TaskCalendarEntity taskCalendarEntity
//    ){
//
//        taskCalendarService.insert(taskCalendarEntity);
//        return taskCalendarService.selectById(taskCalendarEntity.getId());
//    }
    /*
     * ????????????-post
     * */
    @PostMapping("/product")
    protected ProductEntity newProductEntity(
            @RequestBody ProductEntity productEntity
    ){
        productService.insert(productEntity);
        return productService.selectById(productEntity.getId());
    }
    /*
     * ??????????????????-get
     * */
    @GetMapping("/product/search")
    protected BasicDataGridRows searchProductList(
            ProductWhereLogic whereLogic
    ){
        whereLogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(normalDefaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(normalDefaultOrder);
        }
        if(whereLogic.getPage()==0) whereLogic.setPage(1);
        if(whereLogic.getRows()==0) whereLogic.setRows(defaultRows);
        return BasicDataGridRows.create()
                .withRows(productService.selectListByWhereLogic(whereLogic));
    }
    /*
     * ????????????-post
     * */
    @PostMapping("/iterationProject")
    protected IterationProjectEntity newIterationProjectEntity(
            @RequestBody IterationProjectEntity iterationProjectEntity
    ){
        iterationProjectService.insert(iterationProjectEntity);
        return iterationProjectService.selectById(iterationProjectEntity.getId());
    }
    /*
     * ??????????????????-get
     * */
    @GetMapping("/iterationProject/search")
    protected BasicDataGridRows searchIterationProjectList(
            IterationProjectWhereLogic whereLogic
    ){
        whereLogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(normalDefaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(normalDefaultOrder);
        }
        if(whereLogic.getPage()==0) whereLogic.setPage(1);
        if(whereLogic.getRows()==0) whereLogic.setRows(defaultRows);
        return BasicDataGridRows.create()
                .withRows(iterationProjectService.selectListByWhereLogic(whereLogic));
    }
    /*
     * ??????????????????-post
     * */
    @PostMapping("/taskListXlsExp")
    public View taskListXlsExp(  @RequestBody TaskWhereLogic whereLogic , @RequestParam(value = "userIdStr" , required = false) String userIdStr ,
                                @RequestParam(value = "calendarDateArrangeStr" , required = false) String calendarDateArrangeStr ,
                                @RequestParam(value = "priorityStr" , required = false) String priorityStr ,
                                @RequestParam(value = "statusStr" , required = false) String statusStr ){
        whereLogic = taskService.setWhereLogicBeforeSearch(whereLogic , userIdStr , calendarDateArrangeStr , priorityStr , statusStr , defaultOrder , defaultSort);
        return new TaskListDownloadRender(taskService , whereLogic);
    }
}
