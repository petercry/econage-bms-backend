package com.econage.extend.modular.bms.ba.component.expReport.util;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventEntity;
import com.econage.extend.modular.bms.ba.component.event.service.BmsBaEventService;
import com.econage.extend.modular.bms.ba.component.event.trivial.wherelogic.BaEventWherelogic;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.entity.BmsDataJournalEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellDataTypeEnum;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsHeaderEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsDataJournalWhereLogic;
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

public class BaVisitReportDownloadRender implements View {
    private static final String DEFAULT_CHARSET= "UTF-8";
    private static final String CONTENT_TYPE="Content-type:application/octet-stream";
    private static final String CONTENT_DISPOSITION = "attachment;filename=";
    private static final Logger logger = LoggerFactory.getLogger(BaVisitReportDownloadRender.class);
    private final String fromDateStr;
    private final String toDateStr;
    private final String sourceCode;
    private final String valueCode;
    private final List<String> baTags;
    private final String searchOwnerUserStr;
    private final Boolean searchOwnerEmptyFlag;
    private final BmsBaService bmsBaService;
    private final BasicKvUnionQuery basicKVGroupCacheService;
    private final BmsDataJournalService dataJournalService;
    private final UserUnionQuery userUnionQuery;
    private final BmsBaEventService bmsBaEventService;
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    private final String[] defaultSortForBa = {"last_contact_time_","order_seq_"};
    private final String[] defaultOrderForBa = {"desc","desc"};
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BaVisitReportDownloadRender(
            String fromDateStr ,
            String toDateStr ,
            String sourceCode,
            String valueCode,
            List<String> baTags,
            String searchOwnerUserStr,
            Boolean searchOwnerEmptyFlag,
            BmsBaService bmsBaService ,BasicKvUnionQuery basicKVGroupCacheService , BmsDataJournalService dataJournalService , UserUnionQuery userUnionQuery , BmsBaEventService bmsBaEventService) {
        this.fromDateStr = fromDateStr;
        this.toDateStr = toDateStr;
        this.sourceCode = sourceCode;
        this.valueCode = valueCode;
        this.baTags = baTags;
        this.searchOwnerUserStr = searchOwnerUserStr;
        this.searchOwnerEmptyFlag = searchOwnerEmptyFlag;
        this.bmsBaService = bmsBaService;
        this.basicKVGroupCacheService = basicKVGroupCacheService;
        this.dataJournalService = dataJournalService;
        this.userUnionQuery = userUnionQuery;
        this.bmsBaEventService = bmsBaEventService;
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
                CONTENT_DISPOSITION+ URLEncoder.encode("exp.xlsx", DEFAULT_CHARSET)
        );
        BmsExpXlsEntity bmsExpXlsEntity = new BmsExpXlsEntity();
        ArrayList<BmsExpXlsHeaderEntity> headerEntities = new ArrayList<>();
        BmsExpXlsHeaderEntity bexhe = null;
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("客户名称");bexhe.setHeaderColWidth(50);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("来源");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("标签");bexhe.setHeaderColWidth(25);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("价值");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("行业");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("区域");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("拜访时间");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("拜访责任人");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("联系情况");bexhe.setHeaderColWidth(100);headerEntities.add(bexhe);
        bmsExpXlsEntity.setHeaderEntities(headerEntities);

        BaWhereLogic baWhereLogic = new BaWhereLogic();
        baWhereLogic.setStatusTarget(true);
        baWhereLogic.setFromDateForReportSearch(this.fromDateStr);
        baWhereLogic.setToDateForReportSearch(this.toDateStr);
        if(!StringUtils.isEmpty(this.sourceCode)){
            baWhereLogic.setSourceCode(this.sourceCode);
        }
        if(!StringUtils.isEmpty(this.valueCode)){
            baWhereLogic.setValueCode(this.valueCode);
        }
        if(!StringUtils.isEmpty(this.searchOwnerUserStr)){
            baWhereLogic.setSearchOwnerUserStr(this.searchOwnerUserStr);
        }
        if(this.searchOwnerEmptyFlag != null && this.searchOwnerEmptyFlag){
            baWhereLogic.setSearchOwnerEmptyFlag(this.searchOwnerEmptyFlag);
        }
        if(this.baTags!=null&& this.baTags.size() > 0){
            baWhereLogic.setBaTags(this.baTags);
            baWhereLogic.setTagKeyCollecExpress(baWhereLogic.getBaTags());
        }
        baWhereLogic.setIsForVisitReportSearch(true);
        baWhereLogic.setSort(defaultSortForBa);
        baWhereLogic.setOrder(defaultOrderForBa);
        baWhereLogic.setPage(1);
        baWhereLogic.setRows(1000);


        List<BmsBaEntity> baList = bmsBaService.selectListByWhereLogic(baWhereLogic);
        ArrayList<ArrayList<BmsExpXlsCellEntity>> bodyEntity = new ArrayList<>();
        for(BmsBaEntity bbe : baList){
            ArrayList<BmsExpXlsCellEntity> rowEntity = new ArrayList<>();
            BmsExpXlsCellEntity bexce = null;
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bbe.getBaName());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(basicKVGroupCacheService.getKvTextById(bbe.getSourceCode()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bbe.getBaTagStr());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(basicKVGroupCacheService.getKvTextById(bbe.getValueCode()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(basicKVGroupCacheService.getKvTextById(bbe.getIndustryCode()) );bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(bbe.getStateAreaDesc());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            BmsDataJournalWhereLogic dataJournalWhereLogic = new BmsDataJournalWhereLogic();
            dataJournalWhereLogic.setStatusTarget(true);
            dataJournalWhereLogic.setModular("ba");
            dataJournalWhereLogic.setModular_inner_id(bbe.getId());
            dataJournalWhereLogic.setFuncFlag(46);
            dataJournalWhereLogic.setSearchCreateDate_from(fromDateStr);
            dataJournalWhereLogic.setSearchCreateDate_to(toDateStr);
            dataJournalWhereLogic.setSort(defaultSort);
            dataJournalWhereLogic.setOrder(defaultOrder);
            dataJournalWhereLogic.setPage(1);
            dataJournalWhereLogic.setRows(1000);

            List<BmsDataJournalEntity> edjeList = dataJournalService.selectListByWhereLogic(dataJournalWhereLogic);
            String baVisitTime = "";
            String baVisitOwner = "";
            if(edjeList!=null&&edjeList.size()>0){
                BmsDataJournalEntity edje = edjeList.get(0);
                baVisitTime = df.format(edje.getCreateDate());
                baVisitOwner = userUnionQuery.selectUserMi(edje.getCreateUser());
            }
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(baVisitTime);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(baVisitOwner);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            BaEventWherelogic baEventWherelogic = new BaEventWherelogic();
            baEventWherelogic.setStatusTarget(true);
            baEventWherelogic.setBaId(bbe.getId());
            baEventWherelogic.setSearchActionDate_from(fromDateStr);
            baEventWherelogic.setSearchActionDate_to(toDateStr);
            baEventWherelogic.setSort(defaultSort);
            baEventWherelogic.setOrder(defaultOrder);
            baEventWherelogic.setPage(1);
            baEventWherelogic.setRows(1000);
            List<BmsBaEventEntity> baEventEntities = bmsBaEventService.selectListByWhereLogic(baEventWherelogic);
            String eventInfo = "";
            for(BmsBaEventEntity bbee : baEventEntities){
                String eventNodeStr = "";
                eventNodeStr = "时间：" +  df.format(bbee.getActionDate()) + ";联系方式：" + basicKVGroupCacheService.getKvTextById(bbee.getTypeId()) + ";客户方联系人：" + bbee.getContactPerson() + ";经办人：" + bbee.getActionUser() + ";内容："+bbee.getSubject();
                if(eventInfo.equals("")){
                    eventInfo = eventNodeStr;
                }else{
                    eventInfo += "\r\n" + eventNodeStr;
                }
            }
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(eventInfo);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bodyEntity.add(rowEntity);
        }
        bmsExpXlsEntity.setDataEntities(bodyEntity);
        BmsHelper.setReportXls(bmsExpXlsEntity ,os);
    }
}
