package com.econage.extend.modular.bms.ba.component.contact.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.contact.entity.BmsBaContactForImportEntity;
import com.econage.extend.modular.bms.ba.component.contact.mapper.BmsBaContactForImportMapper;
import org.springframework.stereotype.Service;

@Service
public class BmsBaContactForImportService extends ServiceImpl<BmsBaContactForImportMapper, BmsBaContactForImportEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaContactForImportEntity entity){
        entity.setValid(true);
    }
}
