package com.econage.extend.modular.projectwh88;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(name = "econage.wh-88.enabled", havingValue = "true")
public @interface Wh88EWhEnabled {
}
