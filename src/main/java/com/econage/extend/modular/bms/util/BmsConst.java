package com.econage.extend.modular.bms.util;

public interface BmsConst {
    String AFHWC_REMOTING_SETTING_BEAN_NAME = "extend_modular_bms_afhwc_remoting";
    String AFHWC_ACCESS_TOKEN_URI_VAR_NAME = "access_token";
    String AFHWC_CLIENT_ID_URI_VAR_NAME = "client_id";
    String AFHWC_CLIENT_SECRET_URI_VAR_NAME = "client_secret";


    String MODULAR_CONFIG_NAME_SPACE = "econage.extend.inner.bms";

    String BMS_MODULAR_GROUP_NAME="bms";

    String BMS_SQLSESSION_TEMPLATE = "bmsSqlSessionTemplate";

    String BMS_PUB_URL_PREFIX = "/bms/open";
    String BMS_PUB_URL_ANT = BMS_PUB_URL_PREFIX +"/**";
    String SELF_SYS_FLAG = "self";

    String BMS_TASK_MODULAR_NAME = "bms.task.bmsDevTask";
    String BMS_KANBAN_VIEW_PAGE_MODULAR_NAME = "bms.kanban.viewPage";
    String BMS_KANBAN_TEAMVIEW_MODULAR_NAME = "bms.kanban.teamView";
    String BMS_PROJECT_MODULAR_NAME = "bms.project";
    String BMS_CUSTOMER_MODULAR_NAME = "bms.customer";

    String PERMISSION_ADDTASK = "bms.task.addTask";
    String PERMISSION_EDITTASK = "bms.task.editTask";
    String PERMISSION_DELETETASK = "bms.task.deleteTask";
    String PERMISSION_ASSIGNTASK = "bms.task.assignTask";//转派任务
    String PERMISSION_ADDCALENDAR = "bms.task.addCalendar";
    String PERMISSION_ADDFEEDBACK = "bms.task.addFeedback";
    String PERMISSION_DELETECALENDAR = "bms.task.deleteCalendar";
    String PERMISSION_TASKLEADERCONFIRM = "bms.task.leaderConfirm";
    String PERMISSION_MMMFORPRODUCTTAB = "bms.task.mmmForProductTab";
    String PERMISSION_MMMFORPROJECTTAB = "bms.task.mmmForProjectTab";
    String PERMISSION_MMMFORTEAMTAB = "bms.task.mmmForTeamTab";
    String PERMISSION_MMMFORCALENDARTAB = "bms.task.mmmForCalendarTab";
    String PERMISSION_MMMFORPERSONALTAB = "bms.task.mmmForPersonalTab";
    String PERMISSION_AFTEAMVIEWTAB = "bms.task.afTeamViewTab";
    String PERMISSION_IBPMTEAMVIEWTAB = "bms.task.iBpmTeamViewTab";

    String PERMISSION_VIEWALLPROJECT = "bms.project.viewAllProject";
    String PERMISSION_ADDPROJECT = "bms.project.addProject";
    String PERMISSION_DELETEPROJECT = "bms.project.deleteProject";
    String PERMISSION_PROJECT_FIN_ADMIN = "bms.project.projectFinancialAdmin";
    String PERMISSION_PROJECTADMIN = "bms.project.projectAdmin";

    String PERMISSION_VIEWALLCUSTOMER = "bms.customer.viewAllCustomer";
    String PERMISSION_ADDCUSTOMER = "bms.customer.addCustomer";
    String PERMISSION_DELETECUSTOMER = "bms.customer.deleteCustomer";
    String PERMISSION_CUSTOMER_FIN_ADMIN = "bms.customer.customerFinancialAdmin";
    String PERMISSION_CUSTOMERADMIN = "bms.customer.customerAdmin";



    BriefKvEntity[] PROJECT_PHASE_FOR_COMMON = {
            new BriefKvEntity(10,"项目启动"),
            new BriefKvEntity(20,"需求调研"),
            new BriefKvEntity(30,"系统搭建"),
            new BriefKvEntity(40,"二次开发"),
            new BriefKvEntity(50,"培训试运行"),
            new BriefKvEntity(60,"免费维保"),
            new BriefKvEntity(70,"有偿维保"),
    };


    String BMS_TALENT_POOL_MODULAR_NAME = "bms.talent.pool";
    String BMS_TALENT_POOL_MODULAR_I18N = "bms.talent.pool";
    String BMS_TALENT_POOL_MODULAR_HREF = "";
    String BMS_TALENT_POOL_FOLLOW_RESULT_ROOT_ID ="FOLLOWRESULT";

    String BMS_TALENT_POOL_LABEL_GROUP_ID ="BMS.TALENT.LABEL";

    String BMS_BA_CONTACT_VALUECODE_GROUPID = "714";//客户联系人价值
    String BMS_BA_EVENT_TYPE_GROUPID = "713";//客户事件--联系方式
    String BMS_BA_SCALECODE_GROUPID = "724";//规模
    String BMS_BA_VALUECODE_GROUPID = "700";//价值
    String BMS_BA_OWNERSHIPCODE_GROUPID = "710";//所有制
    String BMS_BA_CONTACTS_STATUS_GROUPID = "730";//联系人状态

    String BMS_DATA_JOURNAL_FOR_FIN = "1,2,3,4,32,33,34,35,36,37,45,48,51,53,55";
    Integer[]  BMS_DATA_JOURNAL_FOR_FIN_ARRAY = {1,2,3,4,32,33,34,35,36,37,45,48,51,53,55};

    String BMS_DATA_JOURNAL_FOR_FIN_ABOUT_REIMBURSEFEE = "1,2,4,32,33,34,35,36,55"; //报销流程支出
    String BMS_DATA_JOURNAL_FOR_FIN_ABOUT_PAYFEE = "3,37,45";//付款申请流程支出
    String BMS_DATA_JOURNAL_FOR_FIN_ABOUT_ADVANCEFEE = "53";//费用预支流程支出
    String BMS_DATA_JOURNAL_FOR_FIN_ABOUT_BIZFEE = "48,51";//商务费用申请流程支出

    String BMS_BA_PRODUCT_DIRECTION_MODULAR = "product_direction";

    Integer BMS_EXPENSE_MODULAR_COST = 1; //财务结算bms_expsense_sign的modual,费用开支，jounal里取
    Integer BMS_EXPENSE_MODULAR_TAX = 2; //财务结算bms_expsense_sign的modual,税费，payment event里取

    String EMPLOYEE_GRADE_MODEL_FOR_AF_DEV = "EMPLOYEE_GRADE_MODEL_FOR_AF_DEV";
    String EMPLOYEE_GRADE_MODEL_FOR_IBPM_DEV = "EMPLOYEE_GRADE_MODEL_FOR_IBPM_DEV";
    String EMPLOYEE_GRADE_MODEL_FOR_IT_SUPPORT = "EMPLOYEE_GRADE_MODEL_FOR_IT_SUPPORT";
    String EMPLOYEE_GRADE_MODEL_FOR_ROOKIE = "EMPLOYEE_GRADE_MODEL_FOR_ROOKIE";
}
