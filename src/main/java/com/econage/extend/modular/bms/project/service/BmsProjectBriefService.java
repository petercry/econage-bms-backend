package com.econage.extend.modular.bms.project.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.entity.BmsProjectBriefEntity;
import com.econage.extend.modular.bms.project.mapper.BmsProjectBriefMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BmsProjectBriefService extends ServiceImpl<BmsProjectBriefMapper, BmsProjectBriefEntity> {
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");

    @Override
    protected void doFillFkRelationship(Collection<BmsProjectBriefEntity> entities) {
        for (BmsProjectBriefEntity entity : entities) {
            if(!StringUtils.isEmpty(entity.getSalesManager())){
                String mi = userUnionQuery.selectUserMi(entity.getSalesManager());
                if(StringUtils.isEmpty(mi)){
                    mi = entity.getSalesManager();
                }
                entity.setSalesManagerName(mi);
            }
            if(entity.getProductFocusFlag()){ //对应Alpha审批中“是/否”基础数据的编码
                entity.setProductFocusIdForAf("6cb8790a");
            }else{
                entity.setProductFocusIdForAf("bdaa3acf");
            }
        }
    }
}
