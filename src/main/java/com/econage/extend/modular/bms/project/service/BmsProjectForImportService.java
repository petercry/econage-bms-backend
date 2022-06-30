package com.econage.extend.modular.bms.project.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.entity.BmsProjectForImportEntity;
import com.econage.extend.modular.bms.project.mapper.BmsProjectForImportMapper;
import org.springframework.stereotype.Service;

@Service
public class BmsProjectForImportService extends ServiceImpl<BmsProjectForImportMapper, BmsProjectForImportEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsProjectForImportEntity entity){
        entity.setValid(true);
    }
    public String getProjectIdInWh88By88crm(String proId88crm){
        return getMapper().getProjectIdInWh88By88crm(proId88crm);
    }
}
