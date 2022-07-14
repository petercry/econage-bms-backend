package com.econage.extend.modular.bms.project.web;

import com.econage.base.organization.org.entity.DeptEntity;
import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.base.support.filemanager.entity.FileHeaderEntity;
import com.econage.base.support.filemanager.manage.FileManager;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthEntity;
import com.econage.extend.modular.bms.project.component.auth.service.ProjectAuthService;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventService;
import com.econage.extend.modular.bms.project.entity.BmsProjectEntity;
import com.econage.extend.modular.bms.project.service.BmsProjectService;
import com.econage.extend.modular.bms.project.trivial.wherelogic.ProjectWhereLogic;
import com.econage.extend.modular.bms.util.BmsHelper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/bms/project")
public class BmsProjectWebEndpoint extends BasicControllerImpl {
    private BmsProjectService bmsProjectService;
    private BmsBaService bmsBaService;
    private UserUnionQuery userUnionQuery;
    private BmsProjectEventService projectEventService;
    private ProjectAuthService projectAuthService;
    private FileManager fileManager;
    @Autowired
    protected void setService(UserUnionQuery userUnionQuery , BmsProjectService bmsProjectService , BmsBaService bmsBaService , BmsProjectEventService projectEventService , ProjectAuthService projectAuthService ,  FileManager fileManager) {
        this.userUnionQuery = userUnionQuery;
        this.bmsProjectService = bmsProjectService;
        this.bmsBaService = bmsBaService;
        this.projectEventService = projectEventService;
        this.projectAuthService = projectAuthService;
        this.fileManager = fileManager;
    }

    private final String[] defaultOrder = {"desc"};
    private final String[] defaultSort = {"order_seq_"};
    /*
     * 添加项目-post
     * */
    @PostMapping("")
    protected BmsProjectEntity newEntity(
            @RequestBody BmsProjectEntity bmsProjectEntity
    ){
        bmsProjectService.insert(bmsProjectEntity);
        if(!StringUtils.isEmpty(bmsProjectEntity.getSalesManager())) {
            UserEntity userEntity = userUnionQuery.selectSingle(bmsProjectEntity.getSalesManager());
            if (userEntity != null) {
                for (DeptEntity deptEntity : userEntity.getDepartments()) {
                    String orgId = deptEntity.getId() + "." + bmsProjectEntity.getSalesManager();
                    ProjectAuthEntity projectAuthEntity = new ProjectAuthEntity();
                    projectAuthEntity.setProjectId(bmsProjectEntity.getId());
                    projectAuthEntity.setKey("flowup");
                    projectAuthEntity.setOrgId(orgId);
                    projectAuthEntity.setLinkId(bmsProjectEntity.getSalesManager());
                    projectAuthEntity.setExpress(bmsProjectEntity.getSalesManager());
                    projectAuthEntity.setType(1);
                    projectAuthService.insert(projectAuthEntity);
                }
            }
        }
        return bmsProjectService.selectById(bmsProjectEntity.getId());
    }
    /*
     * 几乎全部字段-put
     * */
    @PutMapping("/{projectId}")
    protected BmsProjectEntity updateEntity(
            @PathVariable("projectId") String baId,
            @RequestBody BmsProjectEntity bmsProjectEntity
    ){
        bmsProjectEntity.setId(baId);
        //bmsProjectService.updateById(bmsProjectEntity);
        bmsProjectService.updateAllColumnById(bmsProjectEntity);
        return bmsProjectService.selectById(baId);
    }
    /*
     * 查询-get
     * */
    @GetMapping("/search")
    protected BasicDataGridRows search(ProjectWhereLogic whereLogic, @RequestParam("sl_projectViewType") Integer sl_projectViewType ){
        //sl_projectViewType:  1: "所有项目"，即管理员获取全部客户信息；2: "我的项目"，走个人权限
        if(sl_projectViewType == 1){
        }else {
            RuntimeUserDetails runtimeUserDetails = SecurityHelper.getRuntimeUserAccount();
            Collection<String> s = new ArrayList<>();
            s.addAll(runtimeUserDetails.getUnionRefExpress());
            whereLogic.setProjectSelectAuthExpress(s);
        }
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        if(whereLogic.getActiveParam()!=null){
            if(whereLogic.getActiveParam()==1){
                whereLogic.setActiveFlag(true);
            }else if(whereLogic.getActiveParam()==0){
                whereLogic.setActiveFlag(false);
            }
        }
        if(whereLogic.getHasBidHitDoc()!=null){
            if(whereLogic.getHasBidHitDoc()==1){
                whereLogic.setBidHitDocFileIdIsNotNull(true);
            }else if(whereLogic.getHasBidHitDoc()==0){
                whereLogic.setBidHitDocFileIdIsNull(true);
            }
        }
        if(whereLogic.getHasContractDoc()!=null){
            if(whereLogic.getHasContractDoc()==1){
                whereLogic.setContractDocFileIdIsNotNull(true);
            }else if(whereLogic.getHasContractDoc()==0){
                whereLogic.setContractDocFileIdIsNull(true);
            }
        }
        if(whereLogic.getHasCheckAcceptDoc()!=null){
            if(whereLogic.getHasCheckAcceptDoc()==1){
                whereLogic.setCheckAcceptDocFileIdIsNotNull(true);
            }else if(whereLogic.getHasCheckAcceptDoc()==0){
                whereLogic.setCheckAcceptDocFileIdIsNull(true);
            }
        }
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(bmsProjectService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination()))
                .withTotal(bmsProjectService.selectCountByWhereLogic(whereLogic));
    }

    @GetMapping("/{projectId}")
    protected BmsProjectEntity getSingleEntity(
            @PathVariable("projectId") String projectId
    ){
        BmsProjectEntity entity = bmsProjectService.selectById(projectId);
        if(entity!=null&&!StringUtils.isEmpty(entity.getProjectPhase())&&StringUtils.isNumeric(entity.getProjectPhase())){
            entity.setProjectPhaseName(BmsHelper.getProjectPhaseName(Integer.parseInt(entity.getProjectPhase())));
        }
        if(entity!=null&&!StringUtils.isEmpty(entity.getSalesManager())){
            String mi = userUnionQuery.selectUserMi(entity.getSalesManager());
            if(StringUtils.isEmpty(mi)){
                mi = entity.getSalesManager();
            }
            entity.setSalesManagerName(mi);
        }
//        if(entity!=null && !StringUtils.isEmpty(entity.getCustomerId())){
//            BmsBaEntity baEntity = bmsBaService.selectById(entity.getCustomerId());
//            entity.setCustomerName(baEntity.getBaName());
//        }

        List<BmsBaEntity> relatedBaList = bmsProjectService.getRelatedBaList(projectId);
        entity.setRelatedBaList(relatedBaList);

        String customerName = "";
        for (BmsBaEntity bbe:relatedBaList) {
            if(customerName.equals("")) customerName = bbe.getBaName();
            else customerName += "、" + bbe.getBaName();
        }
        entity.setCustomerName(customerName);
        Double taxSum = projectEventService.getTaxSum(projectId);
        entity.setTaxSum(taxSum);
        return entity;
    }
    /*
     * 少量字段-patch
     * */
    @PatchMapping("/{projectId}/status")

    protected void patchEntityStatus(
            @PathVariable("projectId") String projectId,
            @RequestParam("action") String action
    ){
        bmsProjectService.changeProjectStatus(projectId , BooleanUtils.toBooleanObject(action));
    }
    /*
     * 获取项目相关文件
     * */
    @GetMapping("/getFileInfo/{fileId}")
    protected FileHeaderEntity getFileInfo(
            @PathVariable("fileId") String fileId
    ){
        FileHeaderEntity fileHeaderEntity = fileManager.selectFileHeaderById(fileId);
        return fileHeaderEntity;
    }
    /*
     * 上传项目文件后写入fileId
     * */
    @PostMapping("/setProjectFile")
    protected void setProjectFile(
            @RequestParam("projectId") String projectId ,
            @RequestParam("opFlag") String opFlag ,
            @RequestParam("fileId") String fileId
    ){
        BmsProjectEntity projectEntity = new BmsProjectEntity();
        projectEntity.setId(projectId);
        if(!fileId.equals("")) {
            if (opFlag.contains("bidHitDoc")) {
                projectEntity.setBidHitDocFileId(fileId);
            } else if (opFlag.contains("contractDoc")) {
                projectEntity.setContractDocFileId(fileId);
            } else if (opFlag.contains("checkAcceptDoc")) {
                projectEntity.setCheckAcceptDocFileId(fileId);
            }
            bmsProjectService.updateById(projectEntity);
        }else{
            List<String> updateCols = null;
            if (opFlag.contains("bidHitDoc")) {
                updateCols = ImmutableList.of("bidHitDocFileId","modDate","modUser");
            } else if (opFlag.contains("contractDoc")) {
                updateCols = ImmutableList.of("contractDocFileId","modDate","modUser");
            } else if (opFlag.contains("checkAcceptDoc")) {
                updateCols = ImmutableList.of("checkAcceptDocFileId","modDate","modUser");
            }
            bmsProjectService.updatePartialColumnById(projectEntity , updateCols);
        }
    }
}
