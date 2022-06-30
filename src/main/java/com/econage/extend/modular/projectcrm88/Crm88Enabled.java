package com.econage.extend.modular.projectcrm88;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})

@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(name = "econage.88-crm.enabled", havingValue = "true")
public @interface Crm88Enabled {
}
