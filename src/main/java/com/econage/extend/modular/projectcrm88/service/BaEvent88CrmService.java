package com.econage.extend.modular.projectcrm88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventForImportEntity;
import com.econage.extend.modular.bms.ba.component.event.service.BmsBaEventForImportService;
import com.econage.extend.modular.projectcrm88.entity.BaEventCrm88Entity;
import com.econage.extend.modular.projectcrm88.mapper.BaEvent88CrmMapper;
import com.econage.extend.modular.projectcrm88.trivial.Crm88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

@Service
@Crm88ByDbTransactional
public class BaEvent88CrmService  extends ServiceImpl<BaEvent88CrmMapper, BaEventCrm88Entity> {
    private BmsBaEventForImportService bmsBaEventForImportService;
    @Autowired
    protected void setBmsBaEventForImportService(BmsBaEventForImportService bmsBaEventForImportService) {
        this.bmsBaEventForImportService = bmsBaEventForImportService;
    }
    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<BaEventCrm88Entity> list = (Collection<BaEventCrm88Entity>)data.getRows();
        //System.out.println("####list:" + list.size());
        Iterator it = list.iterator();
        while(it.hasNext()){
            BaEventCrm88Entity baEventWh88Entity = (BaEventCrm88Entity)it.next();
            BmsBaEventForImportEntity bmsBaEventForImportEntity = new BmsBaEventForImportEntity();
            bmsBaEventForImportEntity.setOrderSeq(baEventWh88Entity.getOrderSeq());
            bmsBaEventForImportEntity.setId(baEventWh88Entity.getId());
            bmsBaEventForImportEntity.setBaId(baEventWh88Entity.getBaId());
            bmsBaEventForImportEntity.setTypeId(baEventWh88Entity.getTypeId());
            bmsBaEventForImportEntity.setContactPerson(baEventWh88Entity.getContactPerson());
            bmsBaEventForImportEntity.setActionUser(baEventWh88Entity.getActionUser());
            bmsBaEventForImportEntity.setActionDate(baEventWh88Entity.getActionDate());
            bmsBaEventForImportEntity.setSubject(baEventWh88Entity.getSubject());
            bmsBaEventForImportEntity.setComments(baEventWh88Entity.getComments());
            bmsBaEventForImportEntity.setCreateUser(baEventWh88Entity.getCreateUser());
            bmsBaEventForImportEntity.setCreateDate(baEventWh88Entity.getCreateDate());
            bmsBaEventForImportEntity.setModDate(baEventWh88Entity.getModDate());
            bmsBaEventForImportEntity.setModUser(baEventWh88Entity.getModUser());
            System.out.println("$$$$$"+bmsBaEventForImportEntity.getId());
            bmsBaEventForImportService.insert(bmsBaEventForImportEntity);
        }
        return true;
    }
}
