package com.econage.extend.modular.bms.finCompute.util;


import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellDataTypeEnum;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsHeaderEntity;
import com.econage.extend.modular.bms.finCompute.entity.ProjectFinComputeEntity;
import com.econage.extend.modular.bms.finCompute.service.FinComputeService;
import com.econage.extend.modular.bms.util.BmsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class ProjectFinComputeDownloadRender implements View {
    private static final String DEFAULT_CHARSET= "UTF-8";
    private static final String CONTENT_TYPE="Content-type:application/octet-stream";
    private static final String CONTENT_DISPOSITION = "attachment;filename=";
    private static final Logger logger = LoggerFactory.getLogger(ProjectFinComputeDownloadRender.class);
    private final String fromDateStr;
    private final String toDateStr;
    private final FinComputeService finComputeService;
    public ProjectFinComputeDownloadRender(String fromDateStr , String toDateStr ,FinComputeService finComputeService ) {
        this.fromDateStr = fromDateStr;
        this.toDateStr = toDateStr;
        this.finComputeService = finComputeService;
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
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("项目名称");bexhe.setHeaderColWidth(70);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("合同金额");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("已收款金额");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("已收款比例");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("待回款金额");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("已耗费成本(含税)");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);

        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("报销流程支出");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("付款流程支出");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("费用预支流程支出");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("商务费用流程支出");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("税费支出");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);

        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("此时间段前是否已有到款");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);

        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("本次到款时间");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("到款类型");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("到款金额");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("到款备注");bexhe.setHeaderColWidth(30);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("税费类别");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("增值税");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("附加税");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("印花税");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);

        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("项目ID");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);
        bexhe = new BmsExpXlsHeaderEntity();bexhe.setHeaderColName("费用ID");bexhe.setHeaderColWidth(15);headerEntities.add(bexhe);

        bmsExpXlsEntity.setHeaderEntities(headerEntities);

        ArrayList<ProjectFinComputeEntity> pfceList = finComputeService.getFinComputeInfoList(this.fromDateStr ,this.toDateStr);

        ArrayList<ArrayList<BmsExpXlsCellEntity>> bodyEntity = new ArrayList<>();
        for(ProjectFinComputeEntity pfce : pfceList){
            ArrayList<BmsExpXlsCellEntity> rowEntity = new ArrayList<>();
            BmsExpXlsCellEntity bexce = null;
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getProjectName());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getContractAmt());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getReceivedPaymtAmt());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getReceivedPaymtPct());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getRestPaymtAmt());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getExpenseSum()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getReimburseFeeExpense()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getPayFeeExpense()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getAdvanceFeeExpense()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getBizFeeExpense()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getTaxExpense()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            String hasPaymentBefore = pfce.getExistPaymentBefore() ? "是": "否";
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(hasPaymentBefore);bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getCurrPaymentDate()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getCurrPaymentTypeDesc());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getCurrPaymentAmt()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getPaymentComments());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getCurrTaxTypeDesc());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getValueAddedTax()==null?0:pfce.getValueAddedTax()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getSuperTax()==null?0:pfce.getSuperTax()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(String.valueOf(pfce.getStampTax()==null?0:pfce.getStampTax()));bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getProjectId());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);
            bexce = new BmsExpXlsCellEntity();bexce.setCellValue(pfce.getPaymentEventId());bexce.setDataType(BmsExpXlsCellDataTypeEnum.STRING);rowEntity.add(bexce);

            bodyEntity.add(rowEntity);
        }
        bmsExpXlsEntity.setDataEntities(bodyEntity);
        BmsHelper.setReportXls(bmsExpXlsEntity ,os);
    }
}
