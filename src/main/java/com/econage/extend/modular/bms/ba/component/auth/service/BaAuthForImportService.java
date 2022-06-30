package com.econage.extend.modular.bms.ba.component.auth.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthForImportEntity;
import com.econage.extend.modular.bms.ba.component.auth.mapper.BaAuthForImportMapper;
import com.econage.extend.modular.bms.ba.component.auth.trival.tempBean.OrgByMiBean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class BaAuthForImportService extends ServiceImpl<BaAuthForImportMapper, BaAuthForImportEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BaAuthForImportEntity entity){
        entity.setValid(true);
    }

    public HashMap<String , OrgByMiBean> selectOrgByMi(){
        Collection<OrgByMiBean> orgList = getMapper().selectOrgByMi();
        HashMap<String , OrgByMiBean> map = new HashMap<>();
        for(OrgByMiBean bean : orgList){
            map.put(bean.getMi() , bean);
        }
        return map;
    }
}

