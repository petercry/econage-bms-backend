package com.econage.extend.modular.bms.project.component.auth.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthEntity;
import com.econage.extend.modular.bms.project.component.auth.mapper.ProjectAuthMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ProjectAuthService extends ServiceImpl<ProjectAuthMapper, ProjectAuthEntity> {
    private UserUnionQuery userUnionQuery;
    @Autowired
    protected void setUserServiceUnionQuery(UserUnionQuery userUnionQuery) {
        this.userUnionQuery = userUnionQuery;
    }
    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeProjectTeamMemberValid(String memberId , boolean status){
        if(StringUtils.isEmpty(memberId)){
            return false;
        }
        ProjectAuthEntity projectAuthEntity = new ProjectAuthEntity();
        projectAuthEntity.setId(memberId);
        projectAuthEntity.setValid(status);
        return updatePartialColumnById(projectAuthEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(ProjectAuthEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    @Override
    protected void doFillFkRelationship(Collection<ProjectAuthEntity> entities){
        for(ProjectAuthEntity entity : entities){
            if(entity.getType() == 1){
                entity.setMemberName(userUnionQuery.selectUserMi(entity.getLinkId()));
            }else{
                entity.setMemberName(entity.getLinkId());
            }
            entity.setCreateUserName(userUnionQuery.selectUserMi(entity.getCreateUser()));
            if(StringUtils.isEmpty(entity.getCreateUserName())) entity.setCreateUserName(entity.getCreateUser());
        }
    }
    public Boolean isAuthExist(String projectId , String roleKey , String roleUser){
        int count_re = getMapper().countExistAuth(projectId , roleKey , roleUser);
        return count_re != 0;
    }
}
