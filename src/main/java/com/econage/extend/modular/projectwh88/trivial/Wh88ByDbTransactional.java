package com.econage.extend.modular.projectwh88.trivial;

import com.econage.extend.modular.projectwh88.trivial.meta.Wh88Const;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Transactional(Wh88Const.WH88_TRANSACTION_MANAGER)
public @interface Wh88ByDbTransactional {
}
