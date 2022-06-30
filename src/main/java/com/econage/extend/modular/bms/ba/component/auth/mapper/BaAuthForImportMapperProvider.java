package com.econage.extend.modular.bms.ba.component.auth.mapper;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

public class BaAuthForImportMapperProvider  implements ProviderMethodResolver {
    public static String selectOrgByMi(){
        StringBuilder strBuf = new StringBuilder()
                .append("select u.id_ , u.mi_ ,\n" +
                        "       (select max(o.ID_) from ecl_organization_ o where o.RESOURCE_ID_ = u.ID_) as org_\n" +
                        "       from ecl_user_ u where STATUS_=1");
        return strBuf.toString();
    }
}
