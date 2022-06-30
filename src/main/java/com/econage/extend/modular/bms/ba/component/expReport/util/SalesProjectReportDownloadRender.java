package com.econage.extend.modular.bms.ba.component.expReport.util;

import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventEntity;
import com.econage.extend.modular.bms.ba.component.event.service.BmsBaEventService;
import com.econage.extend.modular.bms.ba.component.event.trivial.wherelogic.BaEventWherelogic;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellDataTypeEnum;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsHeaderEntity;
import com.econage.extend.modular.bms.util.BmsHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesProjectReportDownloadRender implements View {
    private static final String DEFAULT_CHARSET= "UTF-8";
    private static final String CONTENT_TYPE="Content-type:application/octet-stream";
    private static final String CONTENT_DISPOSITION = "attachment;filename=";
    private static final Logger logger = LoggerFactory.getLogger(SalesProjectReportDownloadRender.class);
    private final DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter minutef = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    private final BmsBaService bmsBaService;
    private final BmsBaEventService bmsBaEventService;
    private final BasicKvUnionQuery kvService;
    private final BaWhereLogic baWhereLogic;
    public SalesProjectReportDownloadRender(
            BmsBaService bmsBaService , BasicKvUnionQuery kvService , BmsBaEventService bmsBaEventService , BaWhereLogic baWhereLogic) {
        this.bmsBaService = bmsBaService;
        this.kvService = kvService;
        this.bmsBaEventService = bmsBaEventService;
        this.baWhereLogic = baWhereLogic;
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
                CONTENT_DISPOSITION + URLEncoder.encode("exp.xlsx", DEFAULT_CHARSET)
        );
        BmsExpXlsEntity bmsExpXlsEntity = new BmsExpXlsEntity();
        ArrayList<BmsExpXlsHeaderEntity> headerEntities = new ArrayList<>();
        BmsExpXlsHeaderEntity bexhe = null;
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("客户名称");bexhe.setHeaderColWidth(50);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("来源");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("产品方向");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("价值");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("商机时间");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("预期定标时间");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("当前阶段");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最近联系时间及记录");bexhe.setHeaderColWidth(50);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("下一步计划");bexhe.setHeaderColWidth(50);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("竞争情况");bexhe.setHeaderColWidth(50);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("项目预算(万元)");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bmsExpXlsEntity.setHeaderEntities(headerEntities);
        List<BmsBaEntity> baList = bmsBaService.selectListByWhereLogic(baWhereLogic);
        ArrayList<ArrayList<BmsExpXlsCellEntity>> bodyEntity = new ArrayList<>();
        for(BmsBaEntity bbe : baList){
            ArrayList<BmsExpXlsCellEntity> rowEntity = new ArrayList<>();
            BmsExpXlsCellEntity bexce = null;
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bbe.getBaName());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(kvService.getKvTextById(bbe.getSourceCode()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bbe.getProductDirectionStr());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(kvService.getKvTextById(bbe.getValueCode()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            String bizOppoTimeStr = "";
            if(bbe.getBizOppoTime()!=null){
                bizOppoTimeStr = datef.format(bbe.getBizOppoTime());
            }
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bizOppoTimeStr);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            String expectTenderTimeStr = "";
            if(bbe.getExpectTenderTime()!=null) {
                expectTenderTimeStr = datef.format(bbe.getExpectTenderTime());
                if (!StringUtils.isEmpty(expectTenderTimeStr) && expectTenderTimeStr.length() == 10) {
                    expectTenderTimeStr = expectTenderTimeStr.substring(0, 7);
                }
            }
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(expectTenderTimeStr);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(kvService.getKvTextById(bbe.getCurrentPhase()) );bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            BaEventWherelogic baEventWherelogic = new BaEventWherelogic();
            baEventWherelogic.setStatusTarget(true);
            baEventWherelogic.setBaId(bbe.getId());
            baEventWherelogic.setSort(defaultSort);
            baEventWherelogic.setOrder(defaultOrder);
            baEventWherelogic.setPage(1);
            baEventWherelogic.setRows(1);
            List<BmsBaEventEntity> baEventEntities = bmsBaEventService.selectListByWhereLogic(baEventWherelogic);
            String eventInfo = "";
            String nextPlan = "";
            if(baEventEntities.size()>0){
                BmsBaEventEntity bbee = baEventEntities.get(0);
                eventInfo = "时间：" +  minutef.format(bbee.getActionDate()) + ";联系方式：" + kvService.getKvTextById(bbee.getTypeId()) + ";客户方联系人：" + bbee.getContactPerson() + ";经办人：" + bbee.getActionUser() + ";内容："+bbee.getSubject();
                if(!StringUtils.isEmpty(bbee.getNextPlan())){
                    nextPlan = bbee.getNextPlan();
                }
            }


            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(eventInfo);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(nextPlan);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bbe.getCompetitiveSituation());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            String projectBudgetStr = "";
            if(bbe.getProjectBudget()!=null){
                projectBudgetStr = String.valueOf(bbe.getProjectBudget());
            }
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(projectBudgetStr);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bodyEntity.add(rowEntity);
        }
        bmsExpXlsEntity.setDataEntities(bodyEntity);
        BmsHelper.setReportXls(bmsExpXlsEntity ,os);
    }
}
