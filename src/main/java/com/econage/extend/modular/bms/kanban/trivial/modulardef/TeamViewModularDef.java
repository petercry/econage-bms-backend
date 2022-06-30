package com.econage.extend.modular.bms.kanban.trivial.modulardef;

import com.econage.base.plat.modular.entity.ModularDef;
import com.econage.base.plat.modular.entity.ModularPermission;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
@Component
public class TeamViewModularDef implements ModularDef {

    @Override
    public String getId() {
        return BmsConst.BMS_KANBAN_TEAMVIEW_MODULAR_NAME;
    }

    @Override
    public String getI18nKey() {
        return "bms.kanban.teamView";
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
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_AFTEAMVIEWTAB,BmsConst.PERMISSION_AFTEAMVIEWTAB),
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_IBPMTEAMVIEWTAB,BmsConst.PERMISSION_IBPMTEAMVIEWTAB)
        );
    }
}


