package com.econage.extend.modular.bms.ba.trivial.wherelogic;

import com.econage.core.service.BasicWhereLogic;
import com.econage.extend.modular.bms.ba.trivial.parser.BaWhereLogicParser;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsTagInfoWhereLogicParser;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogic;
import com.flowyun.cornerstone.db.mybatis.annotations.WhereLogicField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@WhereLogic
@Data
public class BaWhereLogic extends BasicWhereLogic {
    @WhereLogicField(enable = false)
    private List<String> baTags;

    @WhereLogicField(enable = false)
    private Integer sl_baViewType;

    @WhereLogicField(enable = false)
    private Boolean doWarningViewFlag;

    public Integer getSl_baViewType() {
        return sl_baViewType;
    }

    public void setSl_baViewType(Integer sl_baViewType) {
        this.sl_baViewType = sl_baViewType;
    }

    public Boolean getDoWarningViewFlag() {
        return doWarningViewFlag;
    }

    public void setDoWarningViewFlag(Boolean doWarningViewFlag) {
        this.doWarningViewFlag = doWarningViewFlag;
    }

    public List<String> getBaTags() {
        return baTags;
    }

    public void setBaTags(List<String> baTags) {
        this.baTags = baTags;
    }

    @WhereLogicField(parser = BmsTagInfoWhereLogicParser.TagKeyCollecParser.class)
    private Collection<String> tagKeyCollecExpress;

    public Collection<String> getTagKeyCollecExpress() {
        return tagKeyCollecExpress;
    }

    public void setTagKeyCollecExpress(Collection<String> tagKeyCollecExpress) {
        this.tagKeyCollecExpress = tagKeyCollecExpress;
    }

    @WhereLogicField(column = "valid_")
    private Boolean statusTarget;

    @WhereLogicField(wherePart = "(ba_name_ like concat('%',#{name},'%'))")
    private String name;

    @WhereLogicField(wherePart = "(city_ like concat('%',#{city},'%'))")
    private String city;

    @WhereLogicField(wherePart = "(datediff(now(),last_contact_time_) > #{expireContactDay} )")
    private Integer expireContactDay;

    @WhereLogicField(wherePart = "(value_code_ in ('2715','2716','2717','2718','2722') )")    //A、B、C、D、X
    private Boolean checkNewCustomer;

    @WhereLogicField(wherePart = "(value_code_ in ('2719','4963','4964','5063') )")  //S-红、S-黄、S-黑、S
    private Boolean checkOldCustomer;

    @WhereLogicField(wherePart = "(\n" +
            "           ( value_code_ = '2715' and datediff(now(),last_contact_time_) > 7 ) or\n" +  //A类超过7天
            "           ( value_code_ = '2716' and datediff(now(), last_contact_time_) > 30) or\n" + //B类超过30天
            "           ( value_code_ = '2717' and datediff(now(), last_contact_time_) > 180) or\n" + //C类超过半年
            "           datediff(now(), next_contact_time_) >= 0\n" +  //到达“下次联系时间”
            "        )")
    private Boolean isWarnignView;

    @WhereLogicField(wherePart = "not exists(select 1 from bms_ba_auth where bms_ba_auth.ba_id_ = bms_ba_master.id_ and bms_ba_auth.key_ = 'owner' and bms_ba_auth.valid_ = true )")
    private Boolean searchOwnerEmptyFlag;

    @WhereLogicField(wherePart = "not exists(select 1 from bms_ba_auth where bms_ba_auth.ba_id_ = bms_ba_master.id_ and bms_ba_auth.key_ = 'collabrator' and bms_ba_auth.valid_ = true)")
    private Boolean searchCollaboratorEmptyFlag;

    @WhereLogicField(wherePart = "not exists(select 1 from bms_ba_auth where bms_ba_auth.ba_id_ = bms_ba_master.id_ and bms_ba_auth.key_ = 'guest' and bms_ba_auth.valid_ = true)")
    private Boolean searchGuestEmptyFlag;


    @WhereLogicField(wherePart = "exists(select 1 from bms_ba_auth where bms_ba_auth.ba_id_ = bms_ba_master.id_ and bms_ba_auth.key_ = 'owner' and bms_ba_auth.valid_ = true and express_= #{searchOwnerUserStr})")
    private String searchOwnerUserStr;

    @WhereLogicField(wherePart = "exists(select 1 from bms_ba_auth where bms_ba_auth.ba_id_ = bms_ba_master.id_ and bms_ba_auth.key_ = 'collabrator' and bms_ba_auth.valid_ = true and express_= #{searchCollaboratorUserStr})")
    private String searchCollaboratorUserStr;

    @WhereLogicField(wherePart = "exists(select 1 from bms_ba_auth where bms_ba_auth.ba_id_ = bms_ba_master.id_ and bms_ba_auth.key_ = 'guest' and bms_ba_auth.valid_ = true and express_= #{searchGuestUserStr})")
    private String searchGuestUserStr;

    @WhereLogicField(enable = false)
    private String fromDateForReportSearch;

    @WhereLogicField(enable = false)
    private String toDateForReportSearch;

    @WhereLogicField(wherePart = " exists(select 1 from bms_data_journal j where j.valid_ = true and j.modular_='ba' and j.modular_inner_id_ = bms_ba_master.id_ and j.func_flag_ = 46 and j.create_date_ >= STR_TO_DATE(#{fromDateForReportSearch}, '%Y-%m-%d') and j.create_date_ <= date_add(STR_TO_DATE(#{toDateForReportSearch}, '%Y-%m-%d'),interval 1 day )) ")
    private Boolean isForVisitReportSearch;

    public String getFromDateForReportSearch() {
        return fromDateForReportSearch;
    }

    public void setFromDateForReportSearch(String fromDateForReportSearch) {
        this.fromDateForReportSearch = fromDateForReportSearch;
    }

    public String getToDateForReportSearch() {
        return toDateForReportSearch;
    }

    public void setToDateForReportSearch(String toDateForReportSearch) {
        this.toDateForReportSearch = toDateForReportSearch;
    }

    public Boolean getForVisitReportSearch() {
        return isForVisitReportSearch;
    }

    public void setForVisitReportSearch(Boolean forVisitReportSearch) {
        isForVisitReportSearch = forVisitReportSearch;
    }

    public Boolean getSearchOwnerEmptyFlag() {
        return searchOwnerEmptyFlag;
    }

    public void setSearchOwnerEmptyFlag(Boolean searchOwnerEmptyFlag) {
        this.searchOwnerEmptyFlag = searchOwnerEmptyFlag;
    }

    public Boolean getSearchCollaboratorEmptyFlag() {
        return searchCollaboratorEmptyFlag;
    }

    public void setSearchCollaboratorEmptyFlag(Boolean searchCollaboratorEmptyFlag) {
        this.searchCollaboratorEmptyFlag = searchCollaboratorEmptyFlag;
    }

    public Boolean getSearchGuestEmptyFlag() {
        return searchGuestEmptyFlag;
    }

    public void setSearchGuestEmptyFlag(Boolean searchGuestEmptyFlag) {
        this.searchGuestEmptyFlag = searchGuestEmptyFlag;
    }

    public String getAuthPart() {
        return authPart;
    }

    public void setAuthPart(String authPart) {
        this.authPart = authPart;
    }

    public String getNextContactDate_from() {
        return nextContactDate_from;
    }

    public void setNextContactDate_from(String nextContactDate_from) {
        this.nextContactDate_from = nextContactDate_from;
    }

    public String getNextContactDate_to() {
        return nextContactDate_to;
    }

    public void setNextContactDate_to(String nextContactDate_to) {
        this.nextContactDate_to = nextContactDate_to;
    }

    public LocalDate getAlreadyContactDate_from() {
        return alreadyContactDate_from;
    }

    public void setAlreadyContactDate_from(LocalDate alreadyContactDate_from) {
        this.alreadyContactDate_from = alreadyContactDate_from;
    }

    public LocalDate getAlreadyContactDate_to() {
        return alreadyContactDate_to;
    }

    public void setAlreadyContactDate_to(LocalDate alreadyContactDate_to) {
        this.alreadyContactDate_to = alreadyContactDate_to;
    }

    public String getLocationAreaDesc() {
        return locationAreaDesc;
    }

    public void setLocationAreaDesc(String locationAreaDesc) {
        this.locationAreaDesc = locationAreaDesc;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(String firstStatus) {
        this.firstStatus = firstStatus;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public Boolean getWarnignView() {
        return isWarnignView;
    }

    public void setWarnignView(Boolean warnignView) {
        isWarnignView = warnignView;
    }

    public Integer getExpireContactDay() {
        return expireContactDay;
    }

    public void setExpireContactDay(Integer expireContactDay) {
        this.expireContactDay = expireContactDay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @WhereLogicField(column = "id_")
    private Collection<String> ids;

    public Boolean getStatusTarget() {
        return statusTarget;
    }

    public void setStatusTarget(Boolean statusTarget) {
        this.statusTarget = statusTarget;
    }

    public Collection<String> getIds() {
        return ids;
    }

    public void setIds(Collection<String> ids) {
        this.ids = ids;
    }

    @WhereLogicField(enable = false)
    private String authPart;

    @WhereLogicField(parser = BaWhereLogicParser.BaSelectAuthParser.class)
    private Collection<String> baSelectAuthExpress;

    public Collection<String> getBaSelectAuthExpress() {
        return baSelectAuthExpress;
    }

    public void setBaSelectAuthExpress(Collection<String> baSelectAuthExpress) {
        this.baSelectAuthExpress = baSelectAuthExpress;
    }

    @WhereLogicField(wherePart = "(biz_oppo_time_ >= #{bizOppoTime_from} )")
    private String bizOppoTime_from;

    @WhereLogicField(wherePart = "(biz_oppo_time_ <= date_add(STR_TO_DATE(#{bizOppoTime_to}, '%Y-%m-%d'),interval 1 day ) )")
    private String bizOppoTime_to;

    public String getBizOppoTime_from() {
        return bizOppoTime_from;
    }

    public void setBizOppoTime_from(String bizOppoTime_from) {
        this.bizOppoTime_from = bizOppoTime_from;
    }

    public String getBizOppoTime_to() {
        return bizOppoTime_to;
    }

    public void setBizOppoTime_to(String bizOppoTime_to) {
        this.bizOppoTime_to = bizOppoTime_to;
    }

    @WhereLogicField(wherePart = "(next_contact_time_ >= #{nextContactDate_from} )")
    private String nextContactDate_from;

    @WhereLogicField(wherePart = "(next_contact_time_ <= #{nextContactDate_to} )")
    private String nextContactDate_to;

    @WhereLogicField(wherePart = "(exists(select 1 from bms_ba_event where bms_ba_event.ba_id_ = bms_ba_master.id_ and bms_ba_event.valid_=1 and datediff(bms_ba_event.action_date_,#{alreadyContactDate_from})>=0 and datediff(bms_ba_event.action_date_,#{alreadyContactDate_to})<=0))")
    private LocalDate alreadyContactDate_from;

    @WhereLogicField(enable = false)
    private LocalDate alreadyContactDate_to;

    @WhereLogicField(wherePart = " (city_ like concat('%',#{locationAreaDesc},'%') or state_ like concat('%',#{locationAreaDesc},'%') or state_area_desc_ like concat('%',#{locationAreaDesc},'%') )")
    private String locationAreaDesc;

    @WhereLogicField(enable = false)
    private Integer pageNum;

    public String getSearchOwnerUserStr() {
        return searchOwnerUserStr;
    }

    public void setSearchOwnerUserStr(String searchOwnerUserStr) {
        this.searchOwnerUserStr = searchOwnerUserStr;
    }

    public String getSearchCollaboratorUserStr() {
        return searchCollaboratorUserStr;
    }

    public void setSearchCollaboratorUserStr(String searchCollaboratorUserStr) {
        this.searchCollaboratorUserStr = searchCollaboratorUserStr;
    }

    public String getSearchGuestUserStr() {
        return searchGuestUserStr;
    }

    public void setSearchGuestUserStr(String searchGuestUserStr) {
        this.searchGuestUserStr = searchGuestUserStr;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    private String industryCode;
    private String sourceCode;
    private String firstStatus;
    private String valueCode;

    private String scaleCode;
    private String ownershipCode;
    private String posCode;

    private String contactsStatus;

    public String getContactsStatus() {
        return contactsStatus;
    }

    public void setContactsStatus(String contactsStatus) {
        this.contactsStatus = contactsStatus;
    }

    public String getScaleCode() {
        return scaleCode;
    }

    public void setScaleCode(String scaleCode) {
        this.scaleCode = scaleCode;
    }

    public String getOwnershipCode() {
        return ownershipCode;
    }

    public void setOwnershipCode(String ownershipCode) {
        this.ownershipCode = ownershipCode;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public Boolean getCheckNewCustomer() {
        return checkNewCustomer;
    }

    public void setCheckNewCustomer(Boolean checkNewCustomer) {
        this.checkNewCustomer = checkNewCustomer;
    }

    public Boolean getCheckOldCustomer() {
        return checkOldCustomer;
    }

    public void setCheckOldCustomer(Boolean checkOldCustomer) {
        this.checkOldCustomer = checkOldCustomer;
    }
}

