package com.econage.extend.modular.bms.afhwc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(value="econage.bms.afhwc.remoting.enabled",havingValue = "true")
public @interface AfhwcRemotingEnabled {
}
