package com.econage.extend.modular.bms.project.trivial.modulardef;

import com.econage.base.plat.modular.entity.ModularDef;
import com.econage.base.plat.modular.entity.ModularPermission;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class ProjectModularDef  implements ModularDef {

    @Override
    public String getId() {
        return BmsConst.BMS_PROJECT_MODULAR_NAME;
    }

    @Override
    public String getI18nKey() {
        return "bms.project";
    }

    @Override
    public String getAuthor() {
        return "peter";
    }

    @Override
    public String getGroup() {
        return "bms";
    }

    @Override
    public Collection<ModularPermission> getPermissionItems(){
        return Arrays.asList(
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_VIEWALLPROJECT,BmsConst.PERMISSION_VIEWALLPROJECT),                   //查看全部项目
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_ADDPROJECT,BmsConst.PERMISSION_ADDPROJECT),                     //新建项目
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_DELETEPROJECT,BmsConst.PERMISSION_DELETEPROJECT),                   //删除项目
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_PROJECT_FIN_ADMIN,BmsConst.PERMISSION_PROJECT_FIN_ADMIN),                   //项目模块财务管理权限
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_PROJECTADMIN,BmsConst.PERMISSION_PROJECTADMIN)                   //项目模块全管理权限
        );
    }
}

