package com.econage.extend.modular.bms.util;

import com.econage.core.web.security.config.SecurityPermitAllRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class BmsOpenAccessUrlMatcher implements SecurityPermitAllRequestMatcher {
    @Override
    public RequestMatcher getRequestMatcher() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher(BmsConst.BMS_PUB_URL_ANT)
        );
    }
}
