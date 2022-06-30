package com.econage.extend.modular.projectcrm88.trivial;

import com.econage.extend.modular.projectcrm88.trivial.meta.Crm88Const;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Transactional(Crm88Const.CRM88_TRANSACTION_MANAGER)
public @interface Crm88ByDbTransactional {
}
