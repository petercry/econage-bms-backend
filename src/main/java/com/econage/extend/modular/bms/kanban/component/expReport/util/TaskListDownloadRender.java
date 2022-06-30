package com.econage.extend.modular.bms.kanban.component.expReport.util;

import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.extend.modular.bms.ba.component.event.service.BmsBaEventService;
import com.econage.extend.modular.bms.ba.component.expReport.util.SalesProjectReportDownloadRender;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellDataTypeEnum;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsHeaderEntity;
import com.econage.extend.modular.bms.kanban.entity.TaskEntity;
import com.econage.extend.modular.bms.kanban.service.RequireService;
import com.econage.extend.modular.bms.kanban.service.TaskService;
import com.econage.extend.modular.bms.kanban.trivial.wherelogic.TaskWhereLogic;
import com.econage.extend.modular.bms.util.BmsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskListDownloadRender implements View {
    private static final String DEFAULT_CHARSET= "UTF-8";
    private static final String CONTENT_TYPE="Content-type:application/octet-stream";
    private static final String CONTENT_DISPOSITION = "attachment;filename=";
    private static final Logger logger = LoggerFactory.getLogger(TaskListDownloadRender.class);
    private final DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter minutef = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter secondf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final TaskService taskService;
    private final TaskWhereLogic whereLogic;
    public TaskListDownloadRender(TaskService taskService , TaskWhereLogic whereLogic) {
        this.taskService = taskService;
        this.whereLogic = whereLogic;
    }
    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }
    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse response ) throws Exception {
        ServletOutputStream os = response.getOutputStream();

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Accept-Ranges", "bytes");

//        response.setHeader(
//                "Content-Disposition",
//                CONTENT_DISPOSITION+ URLEncoder.encode(fromDateStr + "至" + toDateStr + "项目财务结算表"+ StringMore.DOT + "xlsx", DEFAULT_CHARSET)
//        );
        response.setHeader(
                "Content-Disposition",
                CONTENT_DISPOSITION + URLEncoder.encode("BMS任务列表"+secondf.format(LocalDateTime.now()) +".xlsx", DEFAULT_CHARSET)
        );
        BmsExpXlsEntity bmsExpXlsEntity = new BmsExpXlsEntity();
        ArrayList<BmsExpXlsHeaderEntity> headerEntities = new ArrayList<>();
        BmsExpXlsHeaderEntity bexhe = null;
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("任务名称");bexhe.setHeaderColWidth(70);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("关联项目");bexhe.setHeaderColWidth(60);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("经办人");bexhe.setHeaderColWidth(7);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("状态");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("要求完成时间");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("实际完成时间");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("预估工时");bexhe.setHeaderColWidth(8);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("实际工时");bexhe.setHeaderColWidth(8);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("绩效工时");bexhe.setHeaderColWidth(8);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("关联日程数");bexhe.setHeaderColWidth(8);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("对接人");bexhe.setHeaderColWidth(8);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("录入时间");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bmsExpXlsEntity.setHeaderEntities(headerEntities);
        List<TaskEntity> taskList = taskService.selectListByWhereLogic(whereLogic);
        ArrayList<ArrayList<BmsExpXlsCellEntity>> bodyEntity = new ArrayList<>();
        for(TaskEntity taskEntity : taskList){
            ArrayList<BmsExpXlsCellEntity> rowEntity = new ArrayList<>();
            BmsExpXlsCellEntity bexce = null;
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(taskEntity.getTitle());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(taskEntity.getProjectDesc());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(taskEntity.getDealerName());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(taskEntity.getStatusDesc());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            String expectFinishDate = "";
            if(taskEntity.getExpectFinishDate()!=null){
                expectFinishDate = datef.format(taskEntity.getExpectFinishDate());
            }
            String actualFinishDate = "";
            if(taskEntity.getActualFinishDate()!=null){
                actualFinishDate = datef.format(taskEntity.getActualFinishDate());
            }
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(expectFinishDate);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(actualFinishDate);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(taskEntity.getEstimateManHour()==null?"":taskEntity.getEstimateManHour()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(taskEntity.getActualManHour()==null?"":taskEntity.getActualManHour()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(taskEntity.getPerformManHour()==null?"":taskEntity.getPerformManHour()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(taskEntity.getCalendarCount()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(taskEntity.getFollowuperName());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(minutef.format(taskEntity.getCreateDate()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bodyEntity.add(rowEntity);
        }
        bmsExpXlsEntity.setDataEntities(bodyEntity);
        BmsHelper.setReportXls(bmsExpXlsEntity ,os);
    }
}
