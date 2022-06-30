package com.econage.extend.modular.bms.afhwc.remoting;

import com.econage.extend.modular.bms.afhwc.config.AfhwcRemoting;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcAccessTokenDTO;
import com.econage.extend.modular.bms.util.BmsConst;
import com.flowyun.cornerstone.web.client.rest.annotations.RestMethod;
import com.flowyun.cornerstone.web.client.rest.annotations.RestPath;
import com.flowyun.cornerstone.web.client.rest.annotations.RestUriVariable;
import org.springframework.http.HttpMethod;

/*
 * 认证相关的方法
 * */
@AfhwcRemoting
public interface AfhwcAuthRemoting {


    /*
     * 获取可用的令牌
     * 返回原始数据格式
     * */
    @RestPath("/security/oauth/token" +
            "?grant_type=client_credentials" +
            "&"+ BmsConst.AFHWC_CLIENT_ID_URI_VAR_NAME+"={"+BmsConst.AFHWC_CLIENT_ID_URI_VAR_NAME+"}" +
            "&"+BmsConst.AFHWC_CLIENT_SECRET_URI_VAR_NAME+"={"+BmsConst.AFHWC_CLIENT_SECRET_URI_VAR_NAME+"}"
    )
    @RestMethod(HttpMethod.GET)
    AfhwcAccessTokenDTO getAfhwcAccessToken(
            @RestUriVariable(BmsConst.AFHWC_CLIENT_ID_URI_VAR_NAME) String clientId,
            @RestUriVariable(BmsConst.AFHWC_CLIENT_SECRET_URI_VAR_NAME) String clientSecret
    );

}
