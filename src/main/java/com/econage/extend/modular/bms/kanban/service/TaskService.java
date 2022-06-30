package com.econage.extend.modular.bms.kanban.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.support.filemanager.entity.FileBodyEntity;
import com.econage.base.support.filemanager.entity.FileHeaderEntity;
import com.econage.base.support.filemanager.manage.FileManager;
import com.econage.core.basic.file.FileSystemResourceWithNewFileName;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.kanban.entity.IterationProjectEntity;
import com.econage.extend.modular.bms.kanban.entity.RequireEntity;
import com.econage.extend.modular.bms.kanban.entity.TaskCalendarEntity;
import com.econage.extend.modular.bms.kanban.entity.TaskEntity;
import com.econage.extend.modular.bms.kanban.mapper.TaskMapper;
import com.econage.extend.modular.bms.kanban.trivial.wherelogic.TaskWhereLogic;
import com.econage.extend.modular.bms.project.entity.BmsProjectEntity;
import com.econage.extend.modular.bms.project.service.BmsProjectService;
import com.econage.extend.modular.bms.util.BmsConst;
import com.econage.extend.modular.bms.util.BmsHelper;
import com.econage.extend.modular.bms.util.BmsUtil;
import com.econage.integration.af.entity.dto.AfDriveTaskResponseDTO;
import com.econage.integration.af.entity.model.AfDriveTaskModel;
import com.econage.integration.af.master.AfWfMaster;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class TaskService extends ServiceImpl<TaskMapper, TaskEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private UserUnionQuery userService;
    private RequireService requireService;
    private IterationProjectService iterationProjectService;
    private BmsProjectService bmsProjectService;
    private FileManager fileManager;
    private AfWfMaster afWfMaster;
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    @Autowired
    protected void setAfWfMaster(AfWfMaster afWfMaster) {
        this.afWfMaster = afWfMaster;
    }
    @Autowired
    protected void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    @Autowired
    protected void setUserService(UserUnionQuery userService) {
        this.userService = userService;
    }
    @Autowired
    protected void setRequireService(RequireService requireService) {
        this.requireService = requireService;
    }
    @Autowired
    protected void setIterationProjectService(IterationProjectService iterationProjectService) {
        this.iterationProjectService = iterationProjectService;
    }
    @Autowired
    protected void setProjectService(BmsProjectService bmsProjectService) {
        this.bmsProjectService = bmsProjectService;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeTaskValid(String id , boolean valid){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setValid(valid);
        return updatePartialColumnById(taskEntity,validUpdateCols);
    }

    private final static List<String> statusUpdateCols = ImmutableList.of("status","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean updateTaskStatus(String id , Integer status){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setStatus(status);
        return updatePartialColumnById(taskEntity,statusUpdateCols);
    }

    @Override
    protected void doRefreshSingleEntityBeforeInsert(TaskEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq()==null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
        if(BmsConst.SELF_SYS_FLAG.equals(entity.getPreSysFlag())){
            if(!StringUtils.isEmpty(entity.getId())){
                entity.setPreSysId(entity.getId());
            }else {
                String id = IdWorker.getIdStr();
                entity.setId(id);
                entity.setPreSysId(id);
            }
        }
    }
    @Override
    protected void doRefreshSingleEntityAfterInsert(TaskEntity taskEntity){
        //actionService.addSingleAction(ObjTypeForOpActionEnum.TASK.getTypeFlag(),taskEntity.getId(),taskEntity.getProjectId(), OpActionEnum.CREATE.getActionFlag(),null);
    }
    public Integer countChildrenTaskByRequire(String requireId){
        return getMapper().countChildTaskByRequire(requireId);
    }
    public Integer countCalendarByTask(String taskId){
        return getMapper().countCalendarByTask(taskId);
    }
    public Double sumUsedManHour(String taskId , String exceptCalendarId){
        return getMapper().sumUsedManHour(taskId , exceptCalendarId);
    }
    @Override
    protected void doFillFkRelationship(Collection<TaskEntity> entities){
        for(TaskEntity entity : entities){
            if(!StringUtils.isEmpty(entity.getDealer())){
                entity.setDealerName(userService.selectUserMi(entity.getDealer()));
            }
            if(!StringUtils.isEmpty(entity.getFollowuper())){
                entity.setFollowuperName(userService.selectUserMi(entity.getFollowuper()));
            }
            if(!StringUtils.isEmpty(entity.getRequireId())){
                RequireEntity re = requireService.selectById(entity.getRequireId());
                if (re!=null) {
                    entity.setRequireDesc("#"+re.getId()+" : "+re.getTitle()+" ( 优先级："+re.getPriority()+" )");
                }
            }
            if(entity.getProjectFlag()!=null&&entity.getProjectFlag()!=0&&!StringUtils.isEmpty(entity.getProjectId())){
                if(entity.getProjectFlag()==1){ //实际项目
                    BmsProjectEntity proEntity = bmsProjectService.selectById(entity.getProjectId());
                    if(proEntity!=null) {
                        entity.setProjectDesc(proEntity.getProjectName());
                    }else{
                        entity.setProjectDesc(entity.getProjectId());
                    }
                }else if(entity.getProjectFlag()==2){//内部迭代
                    IterationProjectEntity proEntity = iterationProjectService.selectById(entity.getProjectId());
                    if(proEntity!=null) {
                        entity.setProjectDesc(proEntity.getName());
                    }else{
                        entity.setProjectDesc(entity.getProjectId());
                    }
                }
            }
            if(entity.getTypeId()!=null){
                entity.setTypeDesc(BmsUtil.getTaskTypeDesc(entity.getTypeId()));
            }
            entity.setStatusDesc(BmsUtil.getTaskStatusDesc(entity.getStatus()));
            if(!StringUtils.isEmpty(entity.getCreateUser())){
                String mi = userUnionQuery.selectUserMi(entity.getCreateUser());
                if(StringUtils.isEmpty(mi)){
                    mi = entity.getCreateUser();
                }
                entity.setCreateUserName(mi);
            }
            entity.setCalendarCount(countCalendarByTask(entity.getId()));
        }
    }
    public String getMyUserId(){
        return getRuntimeUserId();
    }
    public InputStream getInputStreamByUrl(String strUrl){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(),output);
            return  new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            log.error(e+"");
        }finally {
            try{
                if (conn != null) {
                    conn.disconnect();
                }
            }catch (Exception e){
                log.error(e+"");
            }
        }
        return null;
    }
    public AfDriveTaskResponseDTO writeBackTaskFeedbackToAf(TaskEntity te , LocalDate taskActualFinishDate , TaskCalendarEntity taskCalendarEntity)throws Exception {
        String reqDataId = te.getPreSysId();
        String taskDataId = te.getPreSysTaskDataId();
        String taskId = te.getPreSysTaskId();
        ObjectNode reqItemJsonObj = new ObjectMapper().createObjectNode();
        reqItemJsonObj.put("sjwcsj" , taskActualFinishDate.toString());  //实际完成时间
        reqItemJsonObj.put("jbgcsfk" , taskCalendarEntity.getFeedbackComments()); //反馈（文本描述）

        List<FileHeaderEntity> fileRows = fileManager.selectFileHeadersInSingleModel("bmsTaskCalendarFeedback",taskCalendarEntity.getId());
        ObjectNode reqFileParamObj = new ObjectMapper().createObjectNode();
        ArrayNode attachmentItemArray = new ObjectMapper().createArrayNode();
        HashMap<String  , String> afFileIdToFileHeaderIdMap = new HashMap<>();//file_header_id与8位随机码的对应map，用于之后file_header_id与afFileId的映射
        if(fileRows != null) {
            for (FileHeaderEntity fheader : fileRows) {
                String checkFileHeaderId = fheader.getId();
                Assert.notNull(fheader,"no file header,id:"+checkFileHeaderId);
                FileBodyEntity fbe = fheader.getFileBody();
                long fileSize = fbe.getSize();
                String fileType = fbe.getType();
                String fileName = fheader.getName();
                if(fileName.indexOf(".")>0){
                    fileName = fileName.substring(0 , fileName.lastIndexOf("."));
                }
                String fileId = RandomStringUtils.randomAlphanumeric(8);
                afFileIdToFileHeaderIdMap.put(fileId , checkFileHeaderId);
                ObjectNode singleAttachmentObj = new ObjectMapper().createObjectNode();
                singleAttachmentObj.put("fileSize" , fileSize);
                singleAttachmentObj.put("fileId" , fileId);
                singleAttachmentObj.put("fileType" , fileType);
                singleAttachmentObj.put("fileName" , fileName);
                attachmentItemArray.add(singleAttachmentObj);
            }
            if(attachmentItemArray.size() > 0){
                reqFileParamObj.set("jbgcsfkwd" , attachmentItemArray);
            }
        }
        AfDriveTaskResponseDTO driveTaskDTO = afWfMaster.driveTask(BmsHelper.afTenantId , BmsHelper.afPeterAccountId , reqDataId , taskDataId , taskId ,reqItemJsonObj,reqFileParamObj);
        if(driveTaskDTO.getModel()!=null){
            AfDriveTaskModel afdtm = driveTaskDTO.getModel();
            if(afdtm.getFileMap()!=null && afdtm.getFileMap().getClass() == LinkedHashMap.class){
                LinkedHashMap<String , String> fm = (LinkedHashMap<String , String>)afdtm.getFileMap();
                for(Map.Entry<String, String> entry : fm.entrySet()) {
                    //System.out.println("key:" + entry.getKey() + "   value:" + entry.getValue());
                    String randomFileField = entry.getKey();//8位随机码
                    String fileIdForUpload = entry.getValue();//af回传的fileId
                    log.debug("#############randomFileField:" + randomFileField +"##fileIdForUpload:" + fileIdForUpload);
                    if(afFileIdToFileHeaderIdMap.containsKey(randomFileField)){
                        String checkFileHeaderId = afFileIdToFileHeaderIdMap.get(randomFileField);
                        FileHeaderEntity checkFileHeaderEntity = fileManager.selectFileHeaderById(checkFileHeaderId);
                        FileSystemResourceWithNewFileName fsr = new FileSystemResourceWithNewFileName(fileManager.prepareAndGetFilePath(checkFileHeaderEntity.getFileBody()));
                        //fsr.setFileName(fileName);
                        log.debug("======fileManager.prepareAndGetFilePath(checkFileHeaderEntity.getFileBody()):" + fileManager.prepareAndGetFilePath(checkFileHeaderEntity.getFileBody()));
                        afWfMaster.uploadFile(BmsHelper.afTenantId , BmsHelper.afPeterAccountId , fileIdForUpload , fsr);
                    }

                }

            }
        }
        return driveTaskDTO;
    }
    public TaskWhereLogic setWhereLogicBeforeSearch(TaskWhereLogic oriWherelogic , String userIdStr , String calendarDateArrangeStr , String priorityStr ,String statusStr , String[] defaultOrder , String[] defaultSort){
        if(StringUtils.isEmpty(userIdStr)&&!StringUtils.isEmpty(oriWherelogic.getUserIdStr())){
            userIdStr = oriWherelogic.getUserIdStr();
        }
        if(StringUtils.isEmpty(priorityStr)&&!StringUtils.isEmpty(oriWherelogic.getPriorityStr())){
            priorityStr = oriWherelogic.getPriorityStr();
        }
        if(StringUtils.isEmpty(statusStr)&&!StringUtils.isEmpty(oriWherelogic.getStatusStr())){
            statusStr = oriWherelogic.getStatusStr();
        }
        TaskWhereLogic newWherelogic = oriWherelogic;
        newWherelogic.setStatusTarget(true);
        if(ArrayUtils.isEmpty(newWherelogic.getSort())){
            newWherelogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(newWherelogic.getOrder())){
            newWherelogic.setOrder(defaultOrder);
        }
        if(newWherelogic.getPage()==0) newWherelogic.setPage(1);
        if(newWherelogic.getRows()==0) newWherelogic.setRows(1000);
        if(!StringUtils.isEmpty(userIdStr)){
            newWherelogic.setUserSelectAuthExpress(Arrays.asList(userIdStr.split(",")));
        }
        if(!StringUtils.isEmpty(calendarDateArrangeStr)){
            newWherelogic.setTaskCalendarArrangeSelectAuthExpress(Arrays.asList(calendarDateArrangeStr.split(",")));
        }
        if(!StringUtils.isEmpty(priorityStr)){
            String[] priV = priorityStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : priV){
                v.add(Integer.parseInt(node));
            }
            newWherelogic.setPriorityList(v);
        }
        if(!StringUtils.isEmpty(statusStr)){
            String[] statusV = statusStr.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for(String node : statusV){
                v.add(Integer.parseInt(node));
            }
            newWherelogic.setStatusList(v);
        }
        return newWherelogic;
    }
}
