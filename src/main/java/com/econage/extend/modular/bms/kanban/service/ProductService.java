package com.econage.extend.modular.bms.kanban.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.kanban.entity.ProductEntity;
import com.econage.extend.modular.bms.kanban.mapper.ProductMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService extends ServiceImpl<ProductMapper, ProductEntity> {
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeProductValid(String id , boolean valid){
        if(StringUtils.isEmpty(id)){
            return false;
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setValid(valid);
        return updatePartialColumnById(productEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(ProductEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq()==null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
}
