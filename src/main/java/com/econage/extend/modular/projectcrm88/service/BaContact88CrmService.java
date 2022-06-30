package com.econage.extend.modular.projectcrm88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.contact.entity.BmsBaContactForImportEntity;
import com.econage.extend.modular.bms.ba.component.contact.service.BmsBaContactForImportService;
import com.econage.extend.modular.projectcrm88.entity.BaContact88CrmEntity;
import com.econage.extend.modular.projectcrm88.mapper.BaContact88CrmMapper;
import com.econage.extend.modular.projectcrm88.trivial.Crm88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

@Service
@Crm88ByDbTransactional
public class BaContact88CrmService extends ServiceImpl<BaContact88CrmMapper, BaContact88CrmEntity> {
    private BmsBaContactForImportService bmsBaContactForImportService;
    @Autowired
    protected void setBmsBaContactForImportService(BmsBaContactForImportService bmsBaContactForImportService) {
        this.bmsBaContactForImportService = bmsBaContactForImportService;
    }
    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<BaContact88CrmEntity> list = (Collection<BaContact88CrmEntity>)data.getRows();
        //System.out.println("####list:" + list.size());
        Iterator it = list.iterator();
        while(it.hasNext()){
            BaContact88CrmEntity baContactWh88Entity = (BaContact88CrmEntity)it.next();
            BmsBaContactForImportEntity bmsBaContactForImportEntity = new BmsBaContactForImportEntity();
            bmsBaContactForImportEntity.setOrderSeq(baContactWh88Entity.getOrderSeq());
            bmsBaContactForImportEntity.setId(baContactWh88Entity.getId());
            bmsBaContactForImportEntity.setBaId(baContactWh88Entity.getBaId());
            bmsBaContactForImportEntity.setName(baContactWh88Entity.getName());
            bmsBaContactForImportEntity.setTitle(baContactWh88Entity.getTitle());
            bmsBaContactForImportEntity.setSex(baContactWh88Entity.getSex());
            bmsBaContactForImportEntity.setValueCode(baContactWh88Entity.getValueCode());
            bmsBaContactForImportEntity.setWorkAddr(baContactWh88Entity.getWorkAddr());
            bmsBaContactForImportEntity.setHomeAddr(baContactWh88Entity.getHomeAddr());
            bmsBaContactForImportEntity.setWorkPhone(baContactWh88Entity.getWorkPhone());
            bmsBaContactForImportEntity.setHomePhone(baContactWh88Entity.getHomePhone());
            bmsBaContactForImportEntity.setMobilePhone(baContactWh88Entity.getMobilePhone());
            bmsBaContactForImportEntity.setFaxNo(baContactWh88Entity.getFaxNo());
            bmsBaContactForImportEntity.setEmail(baContactWh88Entity.getEmail());
            bmsBaContactForImportEntity.setComments(baContactWh88Entity.getComments());
            bmsBaContactForImportEntity.setCreateUser(baContactWh88Entity.getCreateUser());
            bmsBaContactForImportEntity.setCreateDate(baContactWh88Entity.getCreateDate());
            bmsBaContactForImportEntity.setModDate(baContactWh88Entity.getModDate());
            bmsBaContactForImportEntity.setModUser(baContactWh88Entity.getModUser());
            System.out.println("$$$$$"+bmsBaContactForImportEntity.getId());
            bmsBaContactForImportService.insert(bmsBaContactForImportEntity);
        }
        return true;
    }
}
