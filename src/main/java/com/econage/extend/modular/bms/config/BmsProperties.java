package com.econage.extend.modular.bms.config;


import com.econage.extend.modular.bms.util.BmsConst;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Setter(AccessLevel.PACKAGE)
@Component
@ConfigurationProperties(BmsConst.MODULAR_CONFIG_NAME_SPACE)
public class BmsProperties {

    private boolean enabled;

    //private String checkBizOppoFromTmpLibJobCron = "0/5 * * * * ?"; //每5秒钟一次
    private String checkBizOppoFromTmpLibJobCron = "0 0/10 * * * ?"; //每10分钟一次
}
