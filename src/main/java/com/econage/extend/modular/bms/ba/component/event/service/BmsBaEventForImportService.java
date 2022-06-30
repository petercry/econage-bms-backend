package com.econage.extend.modular.bms.ba.component.event.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventForImportEntity;
import com.econage.extend.modular.bms.ba.component.event.mapper.BmsBaEventForImportMapper;
import org.springframework.stereotype.Service;

@Service
public class BmsBaEventForImportService extends ServiceImpl<BmsBaEventForImportMapper, BmsBaEventForImportEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaEventForImportEntity entity){
        entity.setValid(true);
    }
}
