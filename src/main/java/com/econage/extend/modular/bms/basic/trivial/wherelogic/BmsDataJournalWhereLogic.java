package com.econage.extend.modular.bms.basic.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;

import java.util.Collection;

@WhereLogic
public class BmsDataJournalWhereLogic extends BasicWhereLogic {
    private String modular;
    private String modular_inner_id;

    private Integer funcFlag;

    private String finalRelatedProjectId;

    @WhereLogicField(wherePart = "(create_date_ >= STR_TO_DATE(#{searchCreateDate_from}, '%Y-%m-%d')  )")
    private String searchCreateDate_from;

    @WhereLogicField(wherePart = "(create_date_ <= date_add(STR_TO_DATE(#{searchCreateDate_to}, '%Y-%m-%d'),interval 1 day ) )")
    private String searchCreateDate_to;

    public String getSearchCreateDate_from() {
        return searchCreateDate_from;
    }

    public void setSearchCreateDate_from(String searchCreateDate_from) {
        this.searchCreateDate_from = searchCreateDate_from;
    }

    public String getSearchCreateDate_to() {
        return searchCreateDate_to;
    }

    public void setSearchCreateDate_to(String searchCreateDate_to) {
        this.searchCreateDate_to = searchCreateDate_to;
    }

    public String getFinalRelatedProjectId() {
        return finalRelatedProjectId;
    }

    public void setFinalRelatedProjectId(String finalRelatedProjectId) {
        this.finalRelatedProjectId = finalRelatedProjectId;
    }

    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    @WhereLogicField(column = "func_flag_")
    private Collection<Integer> funcFlagList;

    @WhereLogicField(wherePart = "( id_ like '%af_%' and ext_content_ is null )" )
    private Boolean isExtContentError;

    @WhereLogicField(wherePart = "( func_flag_ in (1, 2, 3, 4, 33, 36, 37, 51, 32, 34, 35, 45, 48 ,53 , 55)  )" )
    private Boolean isAboutFin;

    @WhereLogicField(wherePart = "( final_related_project_id_ is null and modular_inner_id_ is not null and create_date_ > STR_TO_DATE('2019-01-01', '%Y-%m-%d') )" )
    private Boolean isNoFinalRelatedProject;

    @WhereLogicField(wherePart = "( final_related_project_id_ like '%#%' )" )
    private Boolean isMultiFinalRelatedProject;

    @WhereLogicField(wherePart = "( final_related_project_id_ is null and modular_ = 'ba' and modular_inner_id_ is not null )" )
    private Boolean isNoFinalRelatedProjectJustBa;

    @WhereLogicField(enable = false)
    private Integer isNoFinalRelatedProjectJustBaFlag;

    public Integer getIsNoFinalRelatedProjectJustBaFlag() {
        return isNoFinalRelatedProjectJustBaFlag;
    }

    public void setIsNoFinalRelatedProjectJustBaFlag(Integer isNoFinalRelatedProjectJustBaFlag) {
        this.isNoFinalRelatedProjectJustBaFlag = isNoFinalRelatedProjectJustBaFlag;
    }

    public Boolean getNoFinalRelatedProjectJustBa() {
        return isNoFinalRelatedProjectJustBa;
    }

    public void setNoFinalRelatedProjectJustBa(Boolean noFinalRelatedProjectJustBa) {
        isNoFinalRelatedProjectJustBa = noFinalRelatedProjectJustBa;
    }

    public Integer getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(Integer funcFlag) {
        this.funcFlag = funcFlag;
    }

    public Boolean getMultiFinalRelatedProject() {
        return isMultiFinalRelatedProject;
    }

    public void setMultiFinalRelatedProject(Boolean multiFinalRelatedProject) {
        isMultiFinalRelatedProject = multiFinalRelatedProject;
    }

    public Boolean getNoFinalRelatedProject() {
        return isNoFinalRelatedProject;
    }

    public void setNoFinalRelatedProject(Boolean noFinalRelatedProject) {
        isNoFinalRelatedProject = noFinalRelatedProject;
    }

    public Boolean getAboutFin() {
        return isAboutFin;
    }

    public void setAboutFin(Boolean aboutFin) {
        isAboutFin = aboutFin;
    }

    public Boolean getExtContentError() {
        return isExtContentError;
    }

    public void setExtContentError(Boolean extContentError) {
        isExtContentError = extContentError;
    }

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }

    public String getModular_inner_id() {
        return modular_inner_id;
    }

    public void setModular_inner_id(String modular_inner_id) {
        this.modular_inner_id = modular_inner_id;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public Collection<Integer> getFuncFlagList() {
        return funcFlagList;
    }

    public void setFuncFlagList(Collection<Integer> funcFlagList) {
        this.funcFlagList = funcFlagList;
    }
}
