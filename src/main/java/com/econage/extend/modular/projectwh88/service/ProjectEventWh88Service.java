package com.econage.extend.modular.projectwh88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventForImportEntity;
import com.econage.extend.modular.bms.project.component.event.service.BmsProjectEventForImportService;
import com.econage.extend.modular.projectwh88.entity.ProjectEventWh88Entity;
import com.econage.extend.modular.projectwh88.mapper.ProjectEventWh88Mapper;
import com.econage.extend.modular.projectwh88.trivial.Wh88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

@Service
@Wh88ByDbTransactional
public class ProjectEventWh88Service extends ServiceImpl<ProjectEventWh88Mapper, ProjectEventWh88Entity> {
    private BmsProjectEventForImportService bmsProjectEventForImportService;
    @Autowired
    protected void setBmsProjectEventForImportService(BmsProjectEventForImportService bmsProjectEventForImportService) {
        this.bmsProjectEventForImportService = bmsProjectEventForImportService;
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<ProjectEventWh88Entity> list = (Collection<ProjectEventWh88Entity>)data.getRows();
        //System.out.println("####list:" + list.size());
        Iterator it = list.iterator();
        while(it.hasNext()){
            ProjectEventWh88Entity projectEventWh88Entity = (ProjectEventWh88Entity)it.next();
            BmsProjectEventForImportEntity bmsProjectEventForImportEntity = new BmsProjectEventForImportEntity();
            bmsProjectEventForImportEntity.setOrderSeq(projectEventWh88Entity.getOrderSeq());
            bmsProjectEventForImportEntity.setId(projectEventWh88Entity.getId());
            bmsProjectEventForImportEntity.setProjectId(projectEventWh88Entity.getProjectId());
            bmsProjectEventForImportEntity.setCategoryId(projectEventWh88Entity.getCategoryId());//1:项目阶段;2:项目里程碑;3:进度检查;4:财务概要;5:收付款历史记录;6:项目事件;7:费用报销
            bmsProjectEventForImportEntity.setStartDate(projectEventWh88Entity.getStartDate());//在"项目阶段"中，是"开始时间"
            bmsProjectEventForImportEntity.setEndDate(projectEventWh88Entity.getEndDate());//在"项目阶段"中，是"结束时间"
            bmsProjectEventForImportEntity.setStage(projectEventWh88Entity.getStage());//在"收付款历史记录"中，是"种类"
            bmsProjectEventForImportEntity.setRecordPerson(projectEventWh88Entity.getRecordPerson());//在"项目阶段"中，是"记录人员"
            bmsProjectEventForImportEntity.setRecordDate(projectEventWh88Entity.getRecordDate());//在"项目阶段"中，是"记录时间"
            bmsProjectEventForImportEntity.setMilestone(projectEventWh88Entity.getMilestone());//在“里程碑”中，是“里程碑”
            bmsProjectEventForImportEntity.setCheckDate(projectEventWh88Entity.getCheckDate());//在"进度检查"中，是"检查时间"
            bmsProjectEventForImportEntity.setStatusId(projectEventWh88Entity.getStatusId());//在"进度检查"中，是"进度状况"
            bmsProjectEventForImportEntity.setDelayType(projectEventWh88Entity.getDelayType());//在"进度检查"中，是"滞后原因"
            bmsProjectEventForImportEntity.setBottleneck(projectEventWh88Entity.getBottleneck());//在"进度检查"中，是"主要瓶颈"
            bmsProjectEventForImportEntity.setComments(projectEventWh88Entity.getComments());
            bmsProjectEventForImportEntity.setExecEval(projectEventWh88Entity.getExecEval());//在"进度检查"中，是"上次工作评价"
            bmsProjectEventForImportEntity.setExecScore(projectEventWh88Entity.getExecScore());//在"进度检查"中，是"上次工作评分"
            bmsProjectEventForImportEntity.setNextPlan(projectEventWh88Entity.getNextPlan());//在"进度检查"中，是"工作计划"
            bmsProjectEventForImportEntity.setNextAssignee(projectEventWh88Entity.getNextAssignee());//在"进度检查"中，是"负责人"
            bmsProjectEventForImportEntity.setTargetDate(projectEventWh88Entity.getTargetDate());//在"进度检查"中，是"要求完成时间"
            bmsProjectEventForImportEntity.setPaymtDate(projectEventWh88Entity.getPaymtDate());
            bmsProjectEventForImportEntity.setPaymtType(projectEventWh88Entity.getPaymtType());
            bmsProjectEventForImportEntity.setPaymtAmt(projectEventWh88Entity.getPaymtAmt());
            bmsProjectEventForImportEntity.setTypeId(projectEventWh88Entity.getTypeId());//在"项目事件"中，是"联系方式"
            bmsProjectEventForImportEntity.setContactPerson(projectEventWh88Entity.getContactPerson());//在"项目事件"中，是"客户联系人"
            bmsProjectEventForImportEntity.setActionDate(projectEventWh88Entity.getActionDate());//在"项目事件"中，是"时间"
            bmsProjectEventForImportEntity.setActionUser(projectEventWh88Entity.getActionUser());//在"项目事件"中，是"经办方"
            bmsProjectEventForImportEntity.setSubject(projectEventWh88Entity.getSubject());//在"项目事件"中，是
            bmsProjectEventForImportEntity.setCreateUser(projectEventWh88Entity.getCreateUser());
            bmsProjectEventForImportEntity.setCreateDate(projectEventWh88Entity.getCreateDate());
            bmsProjectEventForImportEntity.setModDate(projectEventWh88Entity.getModDate());
            bmsProjectEventForImportEntity.setModUser(projectEventWh88Entity.getModUser());
            System.out.println("$$$$$"+bmsProjectEventForImportEntity.getId());
            bmsProjectEventForImportService.insert(bmsProjectEventForImportEntity);
        }
        return true;
    }
}
