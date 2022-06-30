package com.econage.extend.modular.projectwh88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthForImportEntity;
import com.econage.extend.modular.bms.project.component.auth.service.ProjectAuthForImportService;
import com.econage.extend.modular.bms.project.component.auth.trival.tempBean.OrgByMiBean;
import com.econage.extend.modular.projectwh88.entity.ProjectRoleUserWh88Entity;
import com.econage.extend.modular.projectwh88.mapper.ProjectRoleUsersWh88Mapper;
import com.econage.extend.modular.projectwh88.trivial.Wh88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@Service
@Wh88ByDbTransactional
public class ProjectRoleUsersWh88Service extends ServiceImpl<ProjectRoleUsersWh88Mapper, ProjectRoleUserWh88Entity> {
    private ProjectAuthForImportService projectAuthForImportService;
    @Autowired
    protected void setProjectAuthForImportService(ProjectAuthForImportService projectAuthForImportService) {
        this.projectAuthForImportService = projectAuthForImportService;
    }
    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<ProjectRoleUserWh88Entity> list = (Collection<ProjectRoleUserWh88Entity>)data.getRows();
        //System.out.println("####list:" + list.size());
        HashMap<String , OrgByMiBean> org_hash = projectAuthForImportService.selectOrgByMi();
        Iterator it = list.iterator();
        int idx=0;
        while(it.hasNext()){
            idx ++ ;
            ProjectRoleUserWh88Entity projectRoleUserWh88Entity = (ProjectRoleUserWh88Entity)it.next();
            ProjectAuthForImportEntity projectAuthForImportEntity = new ProjectAuthForImportEntity();
            if(org_hash.containsKey(projectRoleUserWh88Entity.getMi())){
                OrgByMiBean bean = org_hash.get(projectRoleUserWh88Entity.getMi());
                projectAuthForImportEntity.setOrgId(bean.getOrg());
                projectAuthForImportEntity.setLinkId(bean.getId());
                projectAuthForImportEntity.setExpress(bean.getId());
                projectAuthForImportEntity.setType(1);
            }else{
                continue;
            }
            projectAuthForImportEntity.setOrderSeq(projectRoleUserWh88Entity.getOrderSeq());
            projectAuthForImportEntity.setId(projectRoleUserWh88Entity.getId());
            projectAuthForImportEntity.setProjectId(projectRoleUserWh88Entity.getProjectId());
            String key = "guest";
            if(projectRoleUserWh88Entity.getRoleId()==1) key = "owner";
            else if(projectRoleUserWh88Entity.getRoleId()==2) key = "collabrator";
            else if(projectRoleUserWh88Entity.getRoleId()==5) key = "flowup";
            projectAuthForImportEntity.setKey(key);
            projectAuthForImportEntity.setCreateUser("econage");
            projectAuthForImportEntity.setCreateDate(projectRoleUserWh88Entity.getCreateDate());
            projectAuthForImportEntity.setModDate(projectRoleUserWh88Entity.getModDate());
            projectAuthForImportEntity.setModUser("econage");

            System.out.println("$$$$$"+idx + "###" +projectAuthForImportEntity.getId());
            if(!projectAuthForImportService.isAuthExist(projectAuthForImportEntity.getProjectId() , projectAuthForImportEntity.getKey() , projectAuthForImportEntity.getOrgId())) { //确认相同记录不存在
                projectAuthForImportService.insert(projectAuthForImportEntity);
            }
        }
        return true;
    }
}
