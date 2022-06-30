package com.econage.extend.modular.bms.talentpool.trival.modulardef;

import com.econage.base.plat.modular.entity.ModularDef;
import org.springframework.stereotype.Component;

import static com.econage.extend.modular.bms.util.BmsConst.*;

/**
 * @author econage
 */
@Component
public class TalentPoolModularDef implements ModularDef {

    @Override
    public String getId() {
        return BMS_TALENT_POOL_MODULAR_NAME;
    }

    @Override
    public String getI18nKey() {
        return BMS_TALENT_POOL_MODULAR_I18N;
    }

    @Override
    public String getAuthor() {
        return "chris";
    }

    @Override
    public String getGroup() {
        return BMS_MODULAR_GROUP_NAME;
    }


    @Override
    public String getMenuHref() { return BMS_TALENT_POOL_MODULAR_HREF; }


}

