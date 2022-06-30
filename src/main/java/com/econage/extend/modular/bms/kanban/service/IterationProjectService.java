package com.econage.extend.modular.bms.kanban.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.kanban.entity.IterationProjectEntity;
import com.econage.extend.modular.bms.kanban.mapper.IterationProjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IterationProjectService  extends ServiceImpl<IterationProjectMapper, IterationProjectEntity> {
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeIterationProjectValid(String id , boolean valid){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        IterationProjectEntity iterationProjectEntity = new IterationProjectEntity();
        iterationProjectEntity.setId(id);
        iterationProjectEntity.setValid(valid);
        return updatePartialColumnById(iterationProjectEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(IterationProjectEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq()==null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
