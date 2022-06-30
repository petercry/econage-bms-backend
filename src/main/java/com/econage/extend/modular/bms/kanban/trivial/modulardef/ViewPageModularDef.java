package com.econage.extend.modular.bms.kanban.trivial.modulardef;

import com.econage.base.plat.modular.entity.ModularDef;
import com.econage.base.plat.modular.entity.ModularPermission;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
@Component
public class ViewPageModularDef implements ModularDef {

    @Override
    public String getId() {
        return BmsConst.BMS_KANBAN_VIEW_PAGE_MODULAR_NAME;
    }

    @Override
    public String getI18nKey() {
        return "bms.kanban.viewPage";
    }

    @Override
    public String getAuthor() {
        return "stone";
    }

    @Override
    public String getGroup() {
        return "bms";
    }

    @Override
    public Collection<ModularPermission> getPermissionItems(){
        return Arrays.asList(
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_MMMFORPRODUCTTAB,BmsConst.PERMISSION_MMMFORPRODUCTTAB),
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_MMMFORPROJECTTAB,BmsConst.PERMISSION_MMMFORPROJECTTAB),
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_MMMFORTEAMTAB,BmsConst.PERMISSION_MMMFORTEAMTAB),
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_MMMFORCALENDARTAB,BmsConst.PERMISSION_MMMFORCALENDARTAB),
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_MMMFORPERSONALTAB,BmsConst.PERMISSION_MMMFORPERSONALTAB)
        );
    }
}


