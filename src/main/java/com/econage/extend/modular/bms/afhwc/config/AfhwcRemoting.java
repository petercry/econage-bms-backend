package com.econage.extend.modular.bms.afhwc.config;

import com.econage.extend.modular.bms.util.BmsConst;
import com.flowyun.cornerstone.web.client.rest.annotations.RestProxy;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
@AfhwcRemotingEnabled
@RestProxy(BmsConst.AFHWC_REMOTING_SETTING_BEAN_NAME)
public @interface AfhwcRemoting {
}
