package com.econage.extend.modular.projectwh88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.project.entity.BmsProjectForImportEntity;
import com.econage.extend.modular.bms.project.service.BmsProjectForImportService;
import com.econage.extend.modular.projectwh88.entity.ProjectWh88Entity;
import com.econage.extend.modular.projectwh88.mapper.ProjectWh88Mapper;
import com.econage.extend.modular.projectwh88.trivial.Wh88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

@Service
@Wh88ByDbTransactional
public class ProjectWh88Service extends ServiceImpl<ProjectWh88Mapper, ProjectWh88Entity> {
    private BmsProjectForImportService bmsProjectForImportService;
    @Autowired
    protected void setBmsProjectForImportService(BmsProjectForImportService bmsProjectForImportService) {
        this.bmsProjectForImportService = bmsProjectForImportService;
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<ProjectWh88Entity> list = (Collection<ProjectWh88Entity>)data.getRows();
        //System.out.println("####list:" + list.size());
        Iterator it = list.iterator();
        while(it.hasNext()){
            ProjectWh88Entity projectWh88Entity = (ProjectWh88Entity)it.next();
            BmsProjectForImportEntity bmsProjectForImportEntity = new BmsProjectForImportEntity();
            bmsProjectForImportEntity.setOrderSeq(projectWh88Entity.getOrderSeq());
            bmsProjectForImportEntity.setId(projectWh88Entity.getId());
            bmsProjectForImportEntity.setProjectName(projectWh88Entity.getProjectName());
            bmsProjectForImportEntity.setComments(projectWh88Entity.getComments());
            bmsProjectForImportEntity.setPriority(projectWh88Entity.getPriority());
            bmsProjectForImportEntity.setProjectType(projectWh88Entity.getProjectType());
            bmsProjectForImportEntity.setProjectNature(projectWh88Entity.getProjectNature());
            bmsProjectForImportEntity.setProjectPhase(projectWh88Entity.getProjectPhase());
            bmsProjectForImportEntity.setProductType(projectWh88Entity.getProductType());
            bmsProjectForImportEntity.setStartDate(projectWh88Entity.getStartDate());
            bmsProjectForImportEntity.setProjectStatus(projectWh88Entity.getProjectStatus());
            bmsProjectForImportEntity.setLocation(projectWh88Entity.getLocation());
            bmsProjectForImportEntity.setContractAmt(projectWh88Entity.getContractAmt());
            bmsProjectForImportEntity.setInvoicedPct(projectWh88Entity.getInvoicedPct());
            bmsProjectForImportEntity.setNextPaymtPct(projectWh88Entity.getNextPaymtPct());
            bmsProjectForImportEntity.setNextPaymtDate(projectWh88Entity.getNextPaymtDate());
            bmsProjectForImportEntity.setNextPaymtCond(projectWh88Entity.getNextPaymtCond());
            bmsProjectForImportEntity.setReceivedPaymtPct(projectWh88Entity.getReceivedPaymtPct());
            bmsProjectForImportEntity.setReceivedPaymtAmt(projectWh88Entity.getReceivedPaymtAmt());
            bmsProjectForImportEntity.setRestPaymtAmt(projectWh88Entity.getRestPaymtAmt());
            bmsProjectForImportEntity.setCreateUser(projectWh88Entity.getCreateUser());
            bmsProjectForImportEntity.setCreateDate(projectWh88Entity.getCreateDate());
            bmsProjectForImportEntity.setModDate(projectWh88Entity.getModDate());
            bmsProjectForImportEntity.setModUser(projectWh88Entity.getModUser());
            System.out.println("$$$$$"+bmsProjectForImportEntity.getId()+"###"+bmsProjectForImportEntity.getProjectName()+"###"+bmsProjectForImportEntity.getInvoicedPct());
            bmsProjectForImportService.insert(bmsProjectForImportEntity);
        }
        return true;
    }
}
