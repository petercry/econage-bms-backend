package com.econage.extend.modular.bms.employeeManage.util;

import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellDataTypeEnum;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsHeaderEntity;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeInfoEntity;
import com.econage.extend.modular.bms.employeeManage.service.EmployeeInfoService;
import com.econage.extend.modular.bms.employeeManage.trivial.wherelogic.EmployeeInfoWherelogic;
import com.econage.extend.modular.bms.util.BmsConst;
import com.econage.extend.modular.bms.util.BmsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeExamineListDownloadRender implements View {
    private static final String DEFAULT_CHARSET= "UTF-8";
    private static final String CONTENT_TYPE="Content-type:application/octet-stream";
    private static final String CONTENT_DISPOSITION = "attachment;filename=";
    private static final Logger logger = LoggerFactory.getLogger(EmployeeExamineListDownloadRender.class);
    private final EmployeeInfoWherelogic employeeInfoWherelogic;
    private final EmployeeInfoService employeeInfoService;
    private final DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public EmployeeExamineListDownloadRender(EmployeeInfoWherelogic employeeInfoWherelogic , EmployeeInfoService employeeInfoService ) {
        this.employeeInfoWherelogic = employeeInfoWherelogic;
        this.employeeInfoService = employeeInfoService;
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
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("姓名");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("当前职级");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("转正时间");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_AF_DEV)){
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("平均薪资基数");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("上级评分");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("产品Leader评分");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("产品助理评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("CTO评分");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("职等权重评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终考核评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终奖金系数");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终考核奖金");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        }else if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IBPM_DEV)){
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("平均薪资基数");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("上级评分");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("伙伴评分");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("PMO经理评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("工时计分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("CTO评分");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("职等权重评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终考核评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终奖金系数");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终考核奖金");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        }else if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_ROOKIE)){
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("当前薪资");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("奖金系数");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("奖金");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        }else if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IT_SUPPORT)){
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("平均薪资基数");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("上级评分");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("CTO评分");bexhe.setHeaderColWidth(10);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("职等权重评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终考核评分");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终奖金系数");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
            bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("最终考核奖金");bexhe.setHeaderColWidth(12);headerEntities.add(bexhe);
        }
        bmsExpXlsEntity.setHeaderEntities(headerEntities);
        List<EmployeeInfoEntity> employeeInfoEntities = employeeInfoService.setExamineDataIntoEntities(employeeInfoWherelogic);
        ArrayList<ArrayList<BmsExpXlsCellEntity>> bodyEntity = new ArrayList<>();

        for(EmployeeInfoEntity employeeInfoEntity : employeeInfoEntities) {
            ArrayList<BmsExpXlsCellEntity> rowEntity = new ArrayList<>();
            BmsExpXlsCellEntity bexce = null;

            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(employeeInfoEntity.getUserName());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(employeeInfoEntity.getPositionGradeTotalDesc());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(datef.format(employeeInfoEntity.getRegularTime()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_AF_DEV)){
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getWageBase()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getLeaderGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getProductLeaderGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getProductAssistantGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getCtoGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getPosLevelGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalGrade = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalGrade()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalGrade.doubleValue()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalRewardScore = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalRewardScore()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalRewardScore));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgExamineReward = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getExamineReward()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgExamineReward));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            }else if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IBPM_DEV)){
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getWageBase()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getLeaderGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getCoworkerGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getPmoGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getManhourGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getCtoGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getPosLevelGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalGrade = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalGrade()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalGrade.doubleValue()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalRewardScore = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalRewardScore()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalRewardScore));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgExamineReward = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getExamineReward()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgExamineReward));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            }else if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_ROOKIE)){
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getWageBase()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalRewardScore = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalRewardScore()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalRewardScore));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgExamineReward = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getExamineReward()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgExamineReward));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            }else if(employeeInfoWherelogic.getSearchExamineModel().equals(BmsConst.EMPLOYEE_GRADE_MODEL_FOR_IT_SUPPORT)){
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getWageBase()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getLeaderGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getCtoGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(employeeInfoEntity.getEmployeeExamineDetailEntity().getPosLevelGrade()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalGrade = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalGrade()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalGrade.doubleValue()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgFinalRewardScore = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getFinalRewardScore()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgFinalRewardScore));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
                BigDecimal bgExamineReward = new BigDecimal(employeeInfoEntity.getEmployeeExamineDetailEntity().getExamineReward()).setScale(2, RoundingMode.HALF_UP);
                bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(bgExamineReward));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            }
            bodyEntity.add(rowEntity);
        }
        bmsExpXlsEntity.setDataEntities(bodyEntity);
        BmsHelper.setReportXls(bmsExpXlsEntity ,os);
    }
}
