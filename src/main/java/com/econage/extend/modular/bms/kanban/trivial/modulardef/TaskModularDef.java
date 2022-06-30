package com.econage.extend.modular.bms.kanban.trivial.modulardef;

import com.econage.base.plat.modular.entity.ModularDef;
import com.econage.base.plat.modular.entity.ModularPermission;
import com.econage.extend.modular.bms.util.BmsConst;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class TaskModularDef implements ModularDef {

    @Override
    public String getId() {
        return BmsConst.BMS_TASK_MODULAR_NAME;
    }

    @Override
    public String getI18nKey() {
        return "bms.task.bmsDevTask";
    }

    @Override
    public String getAuthor() {
        return "stone";
    }

    @Override
    public String getGroup() {
        return "bms";
    }

    @Override
    public Collection<ModularPermission> getPermissionItems(){
        return Arrays.asList(
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_ADDTASK,BmsConst.PERMISSION_ADDTASK),                     //添加任务
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_EDITTASK,BmsConst.PERMISSION_EDITTASK),                   //编辑任务
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_DELETETASK,BmsConst.PERMISSION_DELETETASK),               //删除任务
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_ASSIGNTASK,BmsConst.PERMISSION_ASSIGNTASK),               //转派任务
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_ADDCALENDAR,BmsConst.PERMISSION_ADDCALENDAR),             //添加计划
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_ADDFEEDBACK,BmsConst.PERMISSION_ADDFEEDBACK),             //添加反馈
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_DELETECALENDAR,BmsConst.PERMISSION_DELETECALENDAR),       //删除日程
                ModularPermission.newPermissionItem(BmsConst.PERMISSION_TASKLEADERCONFIRM,BmsConst.PERMISSION_TASKLEADERCONFIRM)  //leader确认评价
        );
    }
}

