package com.econage.extend.modular.bms.project.component.auth.service;

import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthForImportEntity;
import com.econage.extend.modular.bms.project.component.auth.mapper.ProjectAuthForImportMapper;
import com.econage.extend.modular.bms.project.component.auth.trival.tempBean.OrgByMiBean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class ProjectAuthForImportService extends ServiceImpl<ProjectAuthForImportMapper, ProjectAuthForImportEntity> {
    @Override
    protected void doRefreshSingleEntityBeforeInsert(ProjectAuthForImportEntity entity){
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

    public Boolean isAuthExist(String projectId , String roleKey , String roleUser){
        int count_re = getMapper().countExistAuth(projectId , roleKey , roleUser);
        return count_re != 0;
    }
}

