package com.econage.extend.modular.bms.project.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.econage.extend.modular.bms.project.trivial.parser.ProjectWhereLogicParser;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

import java.util.Collection;

@WhereLogic
public class ProjectWhereLogic extends BasicWhereLogic {
    @WhereLogicField(enable = false)
    private Integer activeParam;

    private Boolean activeFlag;


    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    @WhereLogicField(wherePart = "(project_name_ like concat('%',#{projectName},'%'))")
    private String projectName;

    @WhereLogicField(wherePart = "( upper(project_name_) like upper(concat('%',TRIM(#{fuzzyKeywords}),'%')) or upper(contract_no_) like upper(concat('%',TRIM(#{fuzzyKeywords}),'%'))  or upper(customer_desc_) like upper(concat('%',TRIM(#{fuzzyKeywords}),'%')))")
    private String fuzzyKeywords;

    private String projectType;

    @WhereLogicField(parser = ProjectWhereLogicParser.ProjectSelectAuthParser.class)
    private Collection<String> projectSelectAuthExpress;

    @WhereLogicField(enable = false)
    private Integer hasBidHitDoc;

    @WhereLogicField(wherePart = "(bid_hit_doc_file_id_ is null)")
    private Boolean bidHitDocFileIdIsNull;

    @WhereLogicField(wherePart = "(bid_hit_doc_file_id_ is not null)")
    private Boolean bidHitDocFileIdIsNotNull;

    @WhereLogicField(enable = false)
    private Integer hasContractDoc;

    @WhereLogicField(wherePart = "(contract_doc_file_id_ is null)")
    private Boolean contractDocFileIdIsNull;

    @WhereLogicField(wherePart = "(contract_doc_file_id_ is not null)")
    private Boolean contractDocFileIdIsNotNull;

    @WhereLogicField(enable = false)
    private Integer hasCheckAcceptDoc;

    @WhereLogicField(wherePart = "(check_accept_doc_file_id_ is null)")
    private Boolean checkAcceptDocFileIdIsNull;

    @WhereLogicField(wherePart = "(check_accept_doc_file_id_ is not null)")
    private Boolean checkAcceptDocFileIdIsNotNull;

    private String totalStatus;

    public String getTotalStatus() {
        return totalStatus;
    }

    public void setTotalStatus(String totalStatus) {
        this.totalStatus = totalStatus;
    }

    public Integer getHasBidHitDoc() {
        return hasBidHitDoc;
    }

    public void setHasBidHitDoc(Integer hasBidHitDoc) {
        this.hasBidHitDoc = hasBidHitDoc;
    }

    public Boolean getBidHitDocFileIdIsNull() {
        return bidHitDocFileIdIsNull;
    }

    public void setBidHitDocFileIdIsNull(Boolean bidHitDocFileIdIsNull) {
        this.bidHitDocFileIdIsNull = bidHitDocFileIdIsNull;
    }

    public Boolean getBidHitDocFileIdIsNotNull() {
        return bidHitDocFileIdIsNotNull;
    }

    public void setBidHitDocFileIdIsNotNull(Boolean bidHitDocFileIdIsNotNull) {
        this.bidHitDocFileIdIsNotNull = bidHitDocFileIdIsNotNull;
    }

    public Integer getHasCheckAcceptDoc() {
        return hasCheckAcceptDoc;
    }

    public void setHasCheckAcceptDoc(Integer hasCheckAcceptDoc) {
        this.hasCheckAcceptDoc = hasCheckAcceptDoc;
    }

    public Boolean getCheckAcceptDocFileIdIsNull() {
        return checkAcceptDocFileIdIsNull;
    }

    public void setCheckAcceptDocFileIdIsNull(Boolean checkAcceptDocFileIdIsNull) {
        this.checkAcceptDocFileIdIsNull = checkAcceptDocFileIdIsNull;
    }

    public Boolean getCheckAcceptDocFileIdIsNotNull() {
        return checkAcceptDocFileIdIsNotNull;
    }

    public void setCheckAcceptDocFileIdIsNotNull(Boolean checkAcceptDocFileIdIsNotNull) {
        this.checkAcceptDocFileIdIsNotNull = checkAcceptDocFileIdIsNotNull;
    }

    public Boolean getContractDocFileIdIsNull() {
        return contractDocFileIdIsNull;
    }

    public Integer getHasContractDoc() {
        return hasContractDoc;
    }

    public void setHasContractDoc(Integer hasContractDoc) {
        this.hasContractDoc = hasContractDoc;
    }

    public void setContractDocFileIdIsNull(Boolean contractDocFileIdIsNull) {
        this.contractDocFileIdIsNull = contractDocFileIdIsNull;
    }

    public Boolean getContractDocFileIdIsNotNull() {
        return contractDocFileIdIsNotNull;
    }

    public void setContractDocFileIdIsNotNull(Boolean contractDocFileIdIsNotNull) {
        this.contractDocFileIdIsNotNull = contractDocFileIdIsNotNull;
    }

    public Collection<String> getProjectSelectAuthExpress() {
        return projectSelectAuthExpress;
    }

    public void setProjectSelectAuthExpress(Collection<String> projectSelectAuthExpress) {
        this.projectSelectAuthExpress = projectSelectAuthExpress;
    }

    public String getFuzzyKeywords() {
        return fuzzyKeywords;
    }

    public void setFuzzyKeywords(String fuzzyKeywords) {
        this.fuzzyKeywords = fuzzyKeywords;
    }

    public Integer getActiveParam() {
        return activeParam;
    }

    public void setActiveParam(Integer activeParam) {
        this.activeParam = activeParam;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @WhereLogicField(enable = false)
    private Integer pageNum;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @WhereLogicField(wherePart = "( exists(\n" +
            "        select 1 from bms_project_event e\n" +
            "        where e.valid_ = true\n" +
            "            and e.project_id_ = bms_project_master.id_\n" +
            "            and e.category_id_ = 5 and e.paymt_type_ = 0\n" +
            "            and paymt_date_ >= STR_TO_DATE(#{finComputeFromDateStr}, '%Y-%m-%d %H:%i:%s')\n" +
            "            and paymt_date_ <= STR_TO_DATE(#{finComputeToDateStr}, '%Y-%m-%d %H:%i:%s')\n" +
            "    ) )" )
    private Boolean isSearchFinCompute;

    @WhereLogicField(enable = false)
    private String finComputeFromDateStr;

    @WhereLogicField(enable = false)
    private String finComputeToDateStr;

    public Boolean getSearchFinCompute() {
        return isSearchFinCompute;
    }

    public void setSearchFinCompute(Boolean searchFinCompute) {
        isSearchFinCompute = searchFinCompute;
    }

    public String getFinComputeFromDateStr() {
        return finComputeFromDateStr;
    }

    public void setFinComputeFromDateStr(String finComputeFromDateStr) {
        this.finComputeFromDateStr = finComputeFromDateStr;
    }

    public String getFinComputeToDateStr() {
        return finComputeToDateStr;
    }

    public void setFinComputeToDateStr(String finComputeToDateStr) {
        this.finComputeToDateStr = finComputeToDateStr;
    }
}
