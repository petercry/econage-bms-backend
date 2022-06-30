package com.econage.extend.modular.bms.afhwc.master;

import com.econage.base.plat.tokenstore.BasicTokenStoreService;
import com.econage.extend.modular.bms.afhwc.config.AfhwcRemotingEnabled;
import com.econage.extend.modular.bms.afhwc.config.AfhwcRemotingSetting;
import com.econage.extend.modular.bms.afhwc.remoting.AfhwcAuthRemoting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AfhwcRemotingEnabled
public class AfhwcAuthMaster {
    private AfhwcAuthRemoting afhwcAuthRemoting;
    private BasicTokenStoreService basicTokenStoreService;
    private AfhwcRemotingSetting setting;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    void setWired(
            AfhwcAuthRemoting afhwcAuthRemoting,
            BasicTokenStoreService basicTokenStoreService,
            AfhwcRemotingSetting setting
    ) {
        this.afhwcAuthRemoting = afhwcAuthRemoting;
        this.basicTokenStoreService = basicTokenStoreService;
        this.setting = setting;
    }

    public String fetchAccessToken(){
        /*return basicTokenStoreService.selectValidTokenWithRefreshLogic(
                "af-access-token",
                refreshLogic
        );*/
        return afhwcAuthRemoting.getAfhwcAccessToken(
                setting.getClientId(),
                setting.getClientSecret()
        ).getAccessToken();
    }
}

