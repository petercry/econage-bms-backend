package com.econage.extend.modular.projectcrm88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthForImportEntity;
import com.econage.extend.modular.bms.ba.component.auth.service.BaAuthForImportService;
import com.econage.extend.modular.bms.ba.component.auth.trival.tempBean.OrgByMiBean;
import com.econage.extend.modular.projectcrm88.entity.BaRoleUser88CrmEntity;
import com.econage.extend.modular.projectcrm88.mapper.BaRoleUsers88CrmMapper;
import com.econage.extend.modular.projectcrm88.trivial.Crm88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@Service
@Crm88ByDbTransactional
public class BaRoleUsers88CrmService extends ServiceImpl<BaRoleUsers88CrmMapper, BaRoleUser88CrmEntity> {
    private BaAuthForImportService baAuthForImportService;
    @Autowired
    protected void setBaAuthForImportService(BaAuthForImportService baAuthForImportService) {
        this.baAuthForImportService = baAuthForImportService;
    }
    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<BaRoleUser88CrmEntity> list = (Collection<BaRoleUser88CrmEntity>)data.getRows();
        //System.out.println("####list:" + list.size());
        HashMap<String , OrgByMiBean> org_hash = baAuthForImportService.selectOrgByMi();
        Iterator it = list.iterator();
        int idx=0;
        while(it.hasNext()){
            idx ++ ;
            BaRoleUser88CrmEntity baRoleUserWh88Entity = (BaRoleUser88CrmEntity)it.next();
            BaAuthForImportEntity baAuthForImportEntity = new BaAuthForImportEntity();
            if(org_hash.containsKey(baRoleUserWh88Entity.getMi())){
                OrgByMiBean bean = org_hash.get(baRoleUserWh88Entity.getMi());
                baAuthForImportEntity.setOrgId(bean.getOrg());
                baAuthForImportEntity.setLinkId(bean.getId());
                baAuthForImportEntity.setExpress(bean.getId());
                baAuthForImportEntity.setType(1);
            }else{
                continue;
            }
            baAuthForImportEntity.setOrderSeq(baRoleUserWh88Entity.getOrderSeq());
            baAuthForImportEntity.setId(baRoleUserWh88Entity.getId());
            baAuthForImportEntity.setBaId(baRoleUserWh88Entity.getBaId());
            String key = "guest";
            if(baRoleUserWh88Entity.getRoleId()==1) key = "owner";
            else if(baRoleUserWh88Entity.getRoleId()==2) key = "collabrator";
            baAuthForImportEntity.setKey(key);
            baAuthForImportEntity.setCreateUser("econage");
            baAuthForImportEntity.setCreateDate(baRoleUserWh88Entity.getCreateDate());
            baAuthForImportEntity.setModDate(baRoleUserWh88Entity.getModDate());
            baAuthForImportEntity.setModUser("econage");

            System.out.println("$$$$$"+idx + "###" +baAuthForImportEntity.getId());
            baAuthForImportService.insert(baAuthForImportEntity);
        }
        return true;
    }
}
