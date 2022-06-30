package com.econage.extend.modular.bms.kanban.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.kanban.entity.RequireEntity;
import com.econage.extend.modular.bms.kanban.entity.TaskEntity;
import com.econage.extend.modular.bms.kanban.mapper.RequireMapper;
import com.econage.extend.modular.bms.kanban.trivial.wherelogic.TaskWhereLogic;
import com.econage.extend.modular.bms.util.BmsConst;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class RequireService extends ServiceImpl<RequireMapper, RequireEntity> {
    private TaskService taskService;
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    @Autowired
    protected void setService(TaskService taskService) {
        this.taskService = taskService;
    }

    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeRequireValid(String id , boolean valid){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        RequireEntity requireEntity = new RequireEntity();
        requireEntity.setId(id);
        requireEntity.setValid(valid);
        return updatePartialColumnById(requireEntity,validUpdateCols);
    }

    private final static List<String> statusUpdateCols = ImmutableList.of("status","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean updateRequireStatus(String id , Integer status){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        RequireEntity requireEntity = new RequireEntity();
        requireEntity.setId(id);
        requireEntity.setStatus(status);
        return updatePartialColumnById(requireEntity,statusUpdateCols);
    }

    @Override
    protected void doRefreshSingleEntityBeforeInsert(RequireEntity entity){
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
    protected void doFillFkRelationship(Collection<RequireEntity> entities){
        for(RequireEntity entity : entities){
            entity.setChildrenCount(taskService.countChildrenTaskByRequire(entity.getId()));
            if(!StringUtils.isEmpty(entity.getCreateUser())){
                String mi = userUnionQuery.selectUserMi(entity.getCreateUser());
                if(StringUtils.isEmpty(mi)){
                    mi = entity.getCreateUser();
                }
                entity.setCreateUserName(mi);
            }
        }
    }
    public Double reCalcSingleRequireManHour(String requireId){  //重新计算需求总工时
        TaskWhereLogic whereLogic = new TaskWhereLogic();
        whereLogic.setStatusTarget(true);
        whereLogic.setRequireId(requireId);
        List<TaskEntity> taskV = taskService.selectListByWhereLogic(whereLogic);
        Double manHour = 0.0;
        for(TaskEntity te : taskV){
            if(te.getPerformManHour()!=null){
                manHour += te.getPerformManHour();
            }else if(te.getActualManHour()!=null){
                manHour += te.getActualManHour();
            }else if(te.getEstimateManHour()!=null){
                manHour += te.getEstimateManHour();
            }
        }
        return manHour;
    }
    public void reSetSingleRequireManHour(String requireId){
        RequireEntity re = new RequireEntity();
        re.setId(requireId);
        re.setManHour(reCalcSingleRequireManHour(requireId));
        updateById(re);
    }
}
