package com.econage.extend.modular.bms.project.component.event.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.component.event.entity.BmsProjectEventForImportEntity;
import com.econage.extend.modular.bms.project.component.event.mapper.BmsProjectEventForImportMapper;
import org.springframework.stereotype.Service;

@Service
public class BmsProjectEventForImportService extends ServiceImpl<BmsProjectEventForImportMapper, BmsProjectEventForImportEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsProjectEventForImportEntity entity){
        entity.setValid(true);
    }
}
