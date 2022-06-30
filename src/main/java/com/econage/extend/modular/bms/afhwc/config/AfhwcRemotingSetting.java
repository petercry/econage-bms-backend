package com.econage.extend.modular.bms.afhwc.config;

import com.econage.extend.modular.bms.util.BmsConst;
import com.flowyun.cornerstone.web.client.rest.interfaces.RestProxyTarget;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(BmsConst.AFHWC_REMOTING_SETTING_BEAN_NAME)
@ConfigurationProperties("econage.bms.afhwc.remoting")
public class AfhwcRemotingSetting implements RestProxyTarget {
    private boolean enabled;
    private String clientId;
    private String clientSecret;
    private String anonymousAccount;
    private String serverTarget;
    /*
     * 部分页面展示场景下，需要用到不同的移动端基础地址
     * */
    private String mobileTarget;

    public boolean isEnabled() {
        return enabled;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getServerTarget() {
        return serverTarget;
    }

    public String getAnonymousAccount() {
        return anonymousAccount;
    }

    public String getMobileTarget() {
        return mobileTarget;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    void setClientId(String clientId) {
        this.clientId = clientId;
    }

    void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    void setServerTarget(String serverTarget) {
        this.serverTarget = serverTarget;
    }

    void setAnonymousAccount(String anonymousAccount) {
        this.anonymousAccount = anonymousAccount;
    }

    void setMobileTarget(String mobileTarget) {
        this.mobileTarget = mobileTarget;
    }

    @Override
    public String serviceTarget() {
        return serverTarget;
    }
}
