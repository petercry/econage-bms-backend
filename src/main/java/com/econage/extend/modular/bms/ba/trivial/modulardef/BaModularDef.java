package com.econage.extend.modular.bms.ba.trivial.modulardef;

import com.econage.base.plat.modular.entity.ModularDef;
import com.econage.base.plat.modular.entity.ModularPermission;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class BaModularDef implements ModularDef {

    @Override
    public String getId() {
        return BmsConst.BMS_CUSTOMER_MODULAR_NAME;
    }

    @Override
    public String getI18nKey() {
        return "bms.customer";
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
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_VIEWALLCUSTOMER,BmsConst.PERMISSION_VIEWALLCUSTOMER),                   //查看全部客户
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_ADDCUSTOMER,BmsConst.PERMISSION_ADDCUSTOMER),                     //新建客户
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_DELETECUSTOMER,BmsConst.PERMISSION_DELETECUSTOMER),                   //删除客户
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_CUSTOMER_FIN_ADMIN,BmsConst.PERMISSION_CUSTOMER_FIN_ADMIN),                   //客户模块财务管理权限
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_CUSTOMERADMIN,BmsConst.PERMISSION_CUSTOMERADMIN)                   //客户模块全管理权限
        );
    }
}

