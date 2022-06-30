package com.econage.extend.modular.bms.kanban.trivial.secure;

import com.econage.base.security.SecurityHelper;
import com.econage.core.web.security.SecurityUtils;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

/*
* ROLE_bms.task.bmsDevTask_bms.task.addTask
* */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured({
        SecurityHelper.ROLE_ADMIN,
        SecurityHelper.ROLE_SUPER_ADMIN,

        SecurityUtils.ROLE_PREFIX+
        BmsConst.BMS_TASK_MODULAR_NAME+ SecurityHelper.AUTH_SEPARATOR+BmsConst.PERMISSION_ADDTASK
})
public @interface SecuredBmsTaskAdd {
}
