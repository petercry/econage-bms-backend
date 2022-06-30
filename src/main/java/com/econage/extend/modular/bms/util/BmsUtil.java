package com.econage.extend.modular.bms.util;

public class BmsUtil {
    public static String getTaskStatusDesc(Integer status) {
        switch(status) {
            case -1: return "未安排";
            case 10: return "待办(已安排)";
            case 20: return "进行中";
            case 30: return "已完成未确认";
            case 40: return "已完成已确认";
            case 50: return "挂起";
            case 60: return "取消";
            default: return "" + status;
        }
    }

    public static String getRequireStatusDesc(Integer status) {
        switch(status) {
            case -1: return "未开始";
            case 10: return "已立项";
            case 20: return "研发中";
            case 30: return "研发完毕";
            case 40: return "测试中";
            case 50: return "测试完毕";
            case 60: return "已发布";
            case 70: return "已关闭";
            default: return "" + status;
        }
    }

    public static String getTaskTypeDesc(Integer typeId) {
        switch(typeId) {
            case 1: return "常规任务";
            case 2: return "Bug";
            case 3: return "测试";
            case 4: return "运维";
            case 5: return "UI设计";
            case 0: return "其他";
            default: return "未知任务";
        }
    }
}
