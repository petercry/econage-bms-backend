package com.econage.extend.modular.bms.ba.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.entity.BmsBaForImportEntity;
import com.econage.extend.modular.bms.ba.mapper.BmsBaForImportMapper;
import org.springframework.stereotype.Service;

@Service
public class BmsBaForImportService extends ServiceImpl<BmsBaForImportMapper, BmsBaForImportEntity> {
//    private final static List<String> syncUpdateCols = ImmutableList.of("createDate","createUser","modDate","modUser");
//    @Transactional
//    public boolean syncUpdateData( BmsBaForImportEntity bmsBaEntity){
//        System.out.println("#############"+bmsBaEntity.getModDate() + "###" + bmsBaEntity.getCreateUser());
//        return updatePartialColumnById(bmsBaEntity,syncUpdateCols);
//    }

    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaForImportEntity entity){
        entity.setValid(true);
    }
}
