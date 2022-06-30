package com.econage.extend.modular.bms.ba.web;

import com.econage.base.organization.org.entity.DeptEntity;
import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.base.support.kv.entity.BasicKvEntity;
import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.econage.extend.modular.bms.ba.component.auth.entity.BmsBaAuthForMobileEntity;
import com.econage.extend.modular.bms.ba.component.auth.service.BaAuthService;
import com.econage.extend.modular.bms.ba.component.auth.trival.wherelogic.BaAuthWhereLogic;
import com.econage.extend.modular.bms.ba.component.contact.entity.BmsBaContactEntity;
import com.econage.extend.modular.bms.ba.component.contact.entity.BmsBaContactForMobileEntity;
import com.econage.extend.modular.bms.ba.component.contact.service.BmsBaContactService;
import com.econage.extend.modular.bms.ba.component.contact.trivial.wherelogic.BaContactWherelogic;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventEntity;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventForMobileEntity;
import com.econage.extend.modular.bms.ba.component.event.service.BmsBaEventService;
import com.econage.extend.modular.bms.ba.component.event.trivial.wherelogic.BaEventWherelogic;
import com.econage.extend.modular.bms.ba.component.expReport.util.BaVisitReportDownloadRender;
import com.econage.extend.modular.bms.ba.component.expReport.util.SalesProjectReportDownloadRender;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.entity.BmsBaForMobileEntity;
import com.econage.extend.modular.bms.ba.entity.BmsBaKeyValueMapEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaService;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.entity.BmsTagInfoEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.basic.service.BmsTagInfoService;
import com.econage.extend.modular.bms.util.BmsConst;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bms/ba")
public class BmsBaWebEndpoint extends BasicControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BmsBaWebEndpoint.class);
    private BmsBaService bmsBaService;
    private BmsBaEventService bmsBaEventService;
    private BmsBaContactService bmsBaContactService;
    private BasicKvUnionQuery basicKVGroupCacheService;
    private BaAuthService baAuthService;
    private UserUnionQuery userUnionQuery;
    private BmsTagInfoService bmsTagInfoService;
    private BmsDataJournalService dataJournalService;
    @Autowired
    protected void setService(
            BmsBaService bmsBaService ,
            BmsBaEventService bmsBaEventService ,
            BmsBaContactService bmsBaContactService ,
            BasicKvUnionQuery basicKVGroupCacheService ,
            BaAuthService baAuthService ,
            UserUnionQuery userUnionQuery ,
            BmsTagInfoService bmsTagInfoService ,
            BmsDataJournalService dataJournalService
    ) {
        this.bmsBaService = bmsBaService;
        this.bmsBaEventService = bmsBaEventService;
        this.bmsBaContactService = bmsBaContactService;
        this.basicKVGroupCacheService = basicKVGroupCacheService;
        this.baAuthService = baAuthService;
        this.userUnionQuery = userUnionQuery;
        this.bmsTagInfoService = bmsTagInfoService;
        this.dataJournalService = dataJournalService;
    }

    //========================客户信息维护==========================================================
    /*
     * 添加-post
     * */
    @PostMapping("")
    protected BmsBaEntity newEntity(
            @RequestBody BmsBaEntity bmsBaEntity
    ){
        bmsBaService.insert(bmsBaEntity);
        RuntimeUserDetails runtimeUserDetails = SecurityHelper.getRuntimeUserAccount();
        if(runtimeUserDetails != null) {
            UserEntity userEntity = userUnionQuery.selectSingle(runtimeUserDetails.getUserId());
            if(userEntity != null) {
                for(DeptEntity deptEntity : userEntity.getDepartments()){
                    String orgId = deptEntity.getId() + "." + runtimeUserDetails.getUserId();
                    BaAuthEntity baAuthEntity = new BaAuthEntity();
                    baAuthEntity.setBaId(bmsBaEntity.getId());
                    baAuthEntity.setKey("owner");
                    baAuthEntity.setOrgId(orgId);
                    baAuthEntity.setLinkId(runtimeUserDetails.getUserId());
                    baAuthEntity.setExpress(runtimeUserDetails.getUserId());
                    baAuthEntity.setType(1);
                    baAuthService.insert(baAuthEntity);
                }
            }
        }
        return bmsBaService.selectById(bmsBaEntity.getId());
    }

    /*
     * 几乎全部字段-put
     * */
    @PutMapping("/{baId}")
    protected BmsBaEntity updateEntity(
            @PathVariable("baId") String baId,
            @RequestBody BmsBaEntity bmsBaEntity
    ){
        bmsBaEntity.setId(baId);
        bmsBaService.updateAllColumnById(bmsBaEntity);
        return bmsBaService.selectById(baId);
    }

    /*
     * 少量字段-patch
     * */
    @PatchMapping("/{baId}/status")

    protected void patchEntityStatus(
            @PathVariable("baId") String baId,
            @RequestParam("action") String action
    ){
        bmsBaService.changeBaStatus(baId , BooleanUtils.toBooleanObject(action));
    }
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    private final String[] defaultSortForBa = {"last_contact_time_","order_seq_"};
    private final String[] defaultOrderForBa = {"desc","desc"};
    /*
     * 查询-get
     * */
    @GetMapping("/search")
    protected BasicDataGridRows search(BaWhereLogic oriWhereLogic , @RequestParam("sl_baViewType") Integer sl_baViewType , @RequestParam("doWarningViewFlag") Boolean doWarningViewFlag){
        BaWhereLogic newWhereLogic = bmsBaService.setSearchWhereLogic(oriWhereLogic , sl_baViewType , doWarningViewFlag);
        return BasicDataGridRows.create()
                .withRows(bmsBaService.selectListByWhereLogic(newWhereLogic,newWhereLogic.parsePagination()))
                .withTotal(bmsBaService.selectCountByWhereLogic(newWhereLogic));
    }

    @GetMapping("/{baId}")
    protected BmsBaEntity getSingleEntity(
            @PathVariable("baId") String baId
    ){
        BmsBaEntity entity = bmsBaService.selectById(baId);
        List<BmsTagInfoEntity> selectedTags = bmsTagInfoService.getSelectedTag("ba" , baId);
        entity.setBaTag(selectedTags);
        if(StringUtils.isEmpty(entity.getStateAreaDesc())){
            entity.setStateAreaDesc(entity.getCity());
        }
        return entity;
    }

    //========================客户事件维护==========================================================
    /*
     * 添加事件-post
     * */
    @PostMapping("/event")
    protected BmsBaEventEntity newEventEntity(
            @RequestBody BmsBaEventEntity bmsBaEventEntity
    ){
        bmsBaEventService.insert(bmsBaEventEntity);
        return bmsBaEventService.selectById(bmsBaEventEntity.getId());
    }
    private final String[] defaultEventSort = {"action_date_","order_seq_"};
    private final String[] defaultEventOrder = {"desc","desc"};
    //查询客户事件列表
    @GetMapping("/event/search/{baId}")
    protected BasicDataGridRows searchEvent(BaEventWherelogic whereLogic){
       if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultEventSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultEventOrder);
        }
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(bmsBaEventService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination())).withTotal(bmsBaEventService.selectCountByWhereLogic(whereLogic));
    }
    /*
     * 删除事件-patch
     * */
    @PatchMapping("/event/{eventId}/status")

    protected void changeEventValid(
            @PathVariable("eventId") String eventId,
            @RequestParam("action") String action
    ){
        bmsBaEventService.changeBaEventValid(eventId , BooleanUtils.toBooleanObject(action));
    }
    //获取单个事件详情
    @GetMapping("/event/{eventId}")
    protected BmsBaEventEntity getSingleEventEntity(
            @PathVariable("eventId") String eventId
    ){
        return bmsBaEventService.selectById(eventId);
    }
    /*
     * 修改事件-put
     * */
    @PutMapping("/event/{eventId}")
    protected BmsBaEventEntity updateEventEntity(
            @PathVariable("eventId") String eventId,
            @RequestBody BmsBaEventEntity bmsBaEventEntity
    ){
        bmsBaEventEntity.setId(eventId);
        bmsBaEventService.updateById(bmsBaEventEntity);
        return bmsBaEventService.selectById(eventId);
    }

    //========================客户联系人维护==========================================================
    /*
     * 添加联系人-post
     * */
    @PostMapping("/contact")
    protected BmsBaContactEntity newContactEntity(
            @RequestBody BmsBaContactEntity bmsBaContactEntity
    ){
        bmsBaContactService.insert(bmsBaContactEntity);
        return bmsBaContactService.selectById(bmsBaContactEntity.getId());
    }
    //查询客户联系人列表
    @GetMapping("/contact/search/{baId}")
    protected BasicDataGridRows searchContact(BaContactWherelogic whereLogic){
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(bmsBaContactService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination())).withTotal(bmsBaContactService.selectCountByWhereLogic(whereLogic));
    }
    /*
     * 删除联系人-patch
     * */
    @PatchMapping("/contact/{contactId}/status")

    protected void changeContactValid(
            @PathVariable("contactId") String contactId,
            @RequestParam("action") String action
    ){
        bmsBaContactService.changeBaContactValid(contactId , BooleanUtils.toBooleanObject(action));
    }
    //获取单个联系人详情
    @GetMapping("/contact/{contactId}")
    protected BmsBaContactEntity getSingleContactEntity(
            @PathVariable("contactId") String contactId
    ){
        return bmsBaContactService.selectById(contactId);
    }
    /*
     * 修改联系人-put
     * */
    @PutMapping("/contact/{contactId}")
    protected BmsBaContactEntity updateContactEntity(
            @PathVariable("contactId") String contactId,
            @RequestBody BmsBaContactEntity bmsBaContactEntity
    ){
        bmsBaContactEntity.setId(contactId);
        bmsBaContactService.updateById(bmsBaContactEntity);
        return bmsBaContactService.selectById(contactId);
    }

    /*
     * 查询-get
     * 返回客户模块用到的基础数据，用于移动端
     * */
    @GetMapping("/customerKeyValues")
    protected BmsBaKeyValueMapEntity getCustomerKeyValues(){
        Collection<BasicKvEntity> contactValueCodeList = basicKVGroupCacheService.selectListEnabledSelectByGroup(BmsConst.BMS_BA_CONTACT_VALUECODE_GROUPID);//714客户联系人价值
        Map<String, String> contactValueCodeMap = contactValueCodeList.stream().collect(Collectors.toMap(BasicKvEntity::getId, BasicKvEntity::getText));

        Collection<BasicKvEntity> eventContactTypeList = basicKVGroupCacheService.selectListEnabledSelectByGroup(BmsConst.BMS_BA_EVENT_TYPE_GROUPID);//713客户事件--联系方式
        Map<String, String> eventContactTypeMap = eventContactTypeList.stream().collect(Collectors.toMap(BasicKvEntity::getId, BasicKvEntity::getText));

        Collection<BasicKvEntity> scaleCodeList = basicKVGroupCacheService.selectListEnabledSelectByGroup(BmsConst.BMS_BA_SCALECODE_GROUPID);//724规模
        Map<String, String> scaleCodeMap = scaleCodeList.stream().collect(Collectors.toMap(BasicKvEntity::getId, BasicKvEntity::getText));

        Collection<BasicKvEntity> valueCodeList = basicKVGroupCacheService.selectListEnabledSelectByGroup(BmsConst.BMS_BA_VALUECODE_GROUPID);//700价值
        Map<String, String> valueCodeMap = valueCodeList.stream().collect(Collectors.toMap(BasicKvEntity::getId, BasicKvEntity::getText));

        Collection<BasicKvEntity> ownerShipList = basicKVGroupCacheService.selectListEnabledSelectByGroup(BmsConst.BMS_BA_OWNERSHIPCODE_GROUPID);//710所有制
        Map<String, String> ownerShipMap = ownerShipList.stream().collect(Collectors.toMap(BasicKvEntity::getId, BasicKvEntity::getText));

        Collection<BasicKvEntity> contactPersonList = basicKVGroupCacheService.selectListEnabledSelectByGroup(BmsConst.BMS_BA_CONTACTS_STATUS_GROUPID);//730联系人状态
        Map<String, String> contactPersonMap = contactPersonList.stream().collect(Collectors.toMap(BasicKvEntity::getId, BasicKvEntity::getText));

        BmsBaKeyValueMapEntity bmsBaKeyValueMapEntity = new BmsBaKeyValueMapEntity();
        bmsBaKeyValueMapEntity.setContactValueCodeMap(contactValueCodeMap);
        bmsBaKeyValueMapEntity.setEventContactTypeMap(eventContactTypeMap);
        bmsBaKeyValueMapEntity.setScaleCodeMap(scaleCodeMap);
        bmsBaKeyValueMapEntity.setValueCodeMap(valueCodeMap);
        bmsBaKeyValueMapEntity.setOwnerShipMap(ownerShipMap);
        bmsBaKeyValueMapEntity.setContactPersonMap(contactPersonMap);

        return bmsBaKeyValueMapEntity;
    }
    /*
     * 查询-get
     * 获取客户列表，用于移动端
     * */
    @GetMapping("/searchForMobile")
    protected BasicDataGridRows searchForMobile(BaWhereLogic whereLogic , @RequestParam("roleId") Integer roleId , @RequestParam(value = "compAction" , required = false) String compAction ){
        if(roleId == 1 || roleId == 2 || roleId == 3) {
            RuntimeUserDetails runtimeUserDetails = SecurityHelper.getRuntimeUserAccount();
            Collection<String> s = new ArrayList<>();
            s.addAll(runtimeUserDetails.getUnionRefExpress());
            //s.add("1172011667186290690");//模拟沈艳
            // whereLogic.setBaSelectAuthExpress(userAccountDetails.getUnionRefExpress());
            whereLogic.setBaSelectAuthExpress(s);
            if (roleId == 1) {
                whereLogic.setAuthPart("owner");
            } else if (roleId == 2) {
                whereLogic.setAuthPart("collabrator");
            } else if (roleId == 3) {
                whereLogic.setAuthPart("guest");
            }
        }
        if("newCustomer".equals(compAction)){
            whereLogic.setCheckNewCustomer(true);
        }else if("oldCustomer".equals(compAction)){
            whereLogic.setCheckOldCustomer(true);
        }else if("warningCustomer".equals(compAction)){
            whereLogic.setIsWarnignView(true);
        }
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSortForBa);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrderForBa);
        }
        whereLogic.setStatusTarget(true);
        if(whereLogic.getSearchOwnerEmptyFlag()!=null&&!whereLogic.getSearchOwnerEmptyFlag()) whereLogic.setSearchOwnerEmptyFlag(null);
        if(whereLogic.getSearchCollaboratorEmptyFlag()!=null&&!whereLogic.getSearchCollaboratorEmptyFlag()) whereLogic.setSearchCollaboratorEmptyFlag(null);
        if(whereLogic.getSearchGuestEmptyFlag()!=null&&!whereLogic.getSearchGuestEmptyFlag()) whereLogic.setSearchGuestEmptyFlag(null);
        if(!StringUtils.isEmpty(whereLogic.getSearchOwnerUserStr()) && whereLogic.getSearchOwnerUserStr().indexOf(".")>0){
            String userStr = whereLogic.getSearchOwnerUserStr();
            userStr = userStr.substring(userStr.indexOf(".") + 1);
            whereLogic.setSearchOwnerUserStr(userStr);
        }
        if(!StringUtils.isEmpty(whereLogic.getSearchCollaboratorUserStr()) && whereLogic.getSearchCollaboratorUserStr().indexOf(".")>0){
            String userStr = whereLogic.getSearchCollaboratorUserStr();
            userStr = userStr.substring(userStr.indexOf(".") + 1);
            whereLogic.setSearchCollaboratorUserStr(userStr);
        }
        if(!StringUtils.isEmpty(whereLogic.getSearchGuestUserStr()) && whereLogic.getSearchGuestUserStr().indexOf(".")>0){
            String userStr = whereLogic.getSearchGuestUserStr();
            userStr = userStr.substring(userStr.indexOf(".") + 1);
            whereLogic.setSearchGuestUserStr(userStr);
        }
        BasicDataGridRows dbgr = BasicDataGridRows.create()
                .withRows(bmsBaService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination()))
                .withTotal(bmsBaService.selectCountByWhereLogic(whereLogic));
        Collection<BmsBaEntity> bbeColl = (Collection<BmsBaEntity>) dbgr.getRows();
        ArrayList<BmsBaForMobileEntity> bbfmeList = new ArrayList<>();
        for(BmsBaEntity bbe : bbeColl){
            BmsBaForMobileEntity bbfme = new BmsBaForMobileEntity();
            bbfme.setAddress(bbe.getAddress());
            bbfme.setCityCode(bbe.getCity());
            bbfme.setClientContactPerson(bbe.getClientContactPerson());
            bbfme.setCode(bbe.getCode());
            bbfme.setComments(bbe.getComments());
            bbfme.setContactsStatus(bbe.getContactsStatus());
            bbfme.setCreateDate(bbe.getCreateDate());
            bbfme.setCreateUser(bbe.getCreateUser());
            bbfme.setId(bbe.getId());
            bbfme.setInTime(!bmsBaService.checkWarningCustomer(bbe));
            bbfme.setModifiedDate(bbe.getModDate());
            bbfme.setModifiedUser(bbe.getModUser());
            bbfme.setName(bbe.getBaName());
            bbfme.setOwnerShipCode(bbe.getOwnershipCode());
            bbfme.setPhoneNo(bbe.getPhoneNo());
            bbfme.setPosCode(bbe.getPosCode());
            bbfme.setRelationCode(bbe.getRelationCode());
            bbfme.setScaleCode(bbe.getScaleCode());
            bbfme.setShortName(bbe.getShortName());
            bbfme.setSourceCode(bbe.getSourceCode());
            bbfme.setStateCode(bbe.getState());
            bbfme.setTypeCode(bbe.getCustomerType());
            bbfme.setValueCode(bbe.getValueCode());

            BaEventWherelogic baEventWherelogic = new BaEventWherelogic();
            baEventWherelogic.setBaId(bbfme.getId());
            baEventWherelogic.setSort(defaultEventSort);
            baEventWherelogic.setOrder(defaultEventOrder);
            baEventWherelogic.setPage(1);
            baEventWherelogic.setRows(1);
            baEventWherelogic.setStatusTarget(true);
            List<BmsBaEventEntity> bbeeList = bmsBaEventService.selectListByWhereLogic(baEventWherelogic,baEventWherelogic.parsePagination());
            if(bbeeList!=null && bbeeList.size() > 0){
                BmsBaEventEntity lastEvent = bbeeList.get(0);
                BmsBaEventForMobileEntity bbefme = new BmsBaEventForMobileEntity();
                bbefme.setAction_date(lastEvent.getActionDate());
                //bbefme.setAction_hour();
                bbefme.setAction_user_id(lastEvent.getActionUser());
                bbefme.setBa_id(lastEvent.getBaId());
                bbefme.setComments(lastEvent.getComments());
                bbefme.setContact_person(lastEvent.getContactPerson());
                bbefme.setCreateDate(lastEvent.getCreateDate());
                bbefme.setCreateUser(lastEvent.getCreateUser());
                bbefme.setId(lastEvent.getId());
                bbefme.setLmdate(lastEvent.getModDate());
                bbefme.setLmuser(lastEvent.getModUser());
                bbefme.setNext_contact_time(lastEvent.getNextContactDate());
                bbefme.setOrder_id(lastEvent.getOrderSeq());
                //bbefme.setStatus(lastEvent.getv);
                bbefme.setSubject(lastEvent.getSubject());
                bbefme.setType_id(lastEvent.getTypeId());
                ArrayList<BmsBaEventForMobileEntity> bbefmeList = new ArrayList<>();
                bbefmeList.add(bbefme);
                bbfme.setRequestBAEventBeans(bbefmeList);
            }
            bbfmeList.add(bbfme);
        }
        dbgr.setRows(bbfmeList);
        return dbgr;
    }
    @GetMapping("/searchSingleForMobile/{baId}")
    protected BmsBaForMobileEntity getSingleEntityForMobile(
            @PathVariable("baId") String baId
    ){
        BmsBaEntity bbe = bmsBaService.selectById(baId);
        if(StringUtils.isEmpty(bbe.getStateAreaDesc())){
            bbe.setStateAreaDesc(bbe.getCity());
        }
        BmsBaForMobileEntity bbfme = new BmsBaForMobileEntity();
        bbfme.setAddress(bbe.getAddress());
        bbfme.setCityCode(bbe.getCity());
        bbfme.setClientContactPerson(bbe.getClientContactPerson());
        bbfme.setCode(bbe.getCode());
        bbfme.setComments(bbe.getComments());
        bbfme.setContactsStatus(bbe.getContactsStatus());
        bbfme.setCreateDate(bbe.getCreateDate());
        bbfme.setCreateUser(bbe.getCreateUser());
        bbfme.setId(bbe.getId());
        bbfme.setInTime(!bmsBaService.checkWarningCustomer(bbe));
        bbfme.setModifiedDate(bbe.getModDate());
        bbfme.setModifiedUser(bbe.getModUser());
        bbfme.setName(bbe.getBaName());
        bbfme.setOwnerShipCode(bbe.getOwnershipCode());
        bbfme.setPhoneNo(bbe.getPhoneNo());
        bbfme.setPosCode(bbe.getPosCode());
        bbfme.setRelationCode(bbe.getRelationCode());
        bbfme.setScaleCode(bbe.getScaleCode());
        bbfme.setShortName(bbe.getShortName());
        bbfme.setSourceCode(bbe.getSourceCode());
        bbfme.setStateCode(bbe.getState());
        bbfme.setTypeCode(bbe.getCustomerType());
        bbfme.setValueCode(bbe.getValueCode());

        BaEventWherelogic baEventWherelogic = new BaEventWherelogic();
        baEventWherelogic.setBaId(bbfme.getId());
        baEventWherelogic.setSort(defaultEventSort);
        baEventWherelogic.setOrder(defaultEventOrder);
        baEventWherelogic.setPage(1);
        baEventWherelogic.setRows(200);
        baEventWherelogic.setStatusTarget(true);
        List<BmsBaEventEntity> bbeeList = bmsBaEventService.selectListByWhereLogic(baEventWherelogic,baEventWherelogic.parsePagination());
        if(bbeeList!=null && bbeeList.size() > 0){
            ArrayList<BmsBaEventForMobileEntity> bbefmeList = new ArrayList<>();
            for(BmsBaEventEntity eventNode : bbeeList) {
                BmsBaEventForMobileEntity bbefme = new BmsBaEventForMobileEntity();
                bbefme.setAction_date(eventNode.getActionDate());
                //bbefme.setAction_hour();
                bbefme.setAction_user_id(eventNode.getActionUser());
                bbefme.setBa_id(eventNode.getBaId());
                bbefme.setComments(eventNode.getComments());
                bbefme.setContact_person(eventNode.getContactPerson());
                bbefme.setCreateDate(eventNode.getCreateDate());
                bbefme.setCreateUser(eventNode.getCreateUser());
                bbefme.setId(eventNode.getId());
                bbefme.setLmdate(eventNode.getModDate());
                bbefme.setLmuser(eventNode.getModUser());
                bbefme.setNext_contact_time(eventNode.getNextContactDate());
                bbefme.setOrder_id(eventNode.getOrderSeq());
                //bbefme.setStatus(lastEvent.getv);
                bbefme.setSubject(eventNode.getSubject());
                bbefme.setType_id(eventNode.getTypeId());
                bbefmeList.add(bbefme);
            }
            bbfme.setRequestBAEventBeans(bbefmeList);
        }
        BaAuthWhereLogic baAuthWhereLogic = new BaAuthWhereLogic();
        baAuthWhereLogic.setBaId(bbfme.getId());
        baAuthWhereLogic.setSort(defaultSort);
        baAuthWhereLogic.setOrder(defaultOrder);
        baAuthWhereLogic.setStatusTarget(true);
        List<BaAuthEntity> baeList = baAuthService.selectListByWhereLogic(baAuthWhereLogic);
        if(baeList!=null && baeList.size() > 0){
            ArrayList<BmsBaAuthForMobileEntity> bbafmeList = new ArrayList<>();
            for(BaAuthEntity bae : baeList){
                BmsBaAuthForMobileEntity bbafme = new BmsBaAuthForMobileEntity();
                bbafme.setComponent_id(baId);
                bbafme.setId(bae.getId());
                bbafme.setCreateDate(bae.getCreateDate());
                bbafme.setCreateUser(bae.getCreateUser());
                bbafme.setLmdate(bae.getModDate());
                bbafme.setLmuser(bae.getModUser());
                bbafme.setOrder_id(bae.getOrderSeq());
                bbafme.setRole(bae.getKey());
                if("owner".equals(bae.getKey())){
                    bbafme.setRoleId(1);
                }else if("collabrator".equals(bae.getKey())){
                    bbafme.setRoleId(2);
                }else{
                    bbafme.setRoleId(3);
                }
                bbafme.setRoleName(bae.getKey());
                bbafme.setUser(bae.getMemberName());
                bbafme.setUserId(bae.getLinkId());
                bbafme.setUser_name(bae.getMemberName());
                bbafmeList.add(bbafme);
            }
            bbfme.setComponentRoleUserBeans(bbafmeList);
        }

        BaContactWherelogic baContactWherelogic = new BaContactWherelogic();
        baContactWherelogic.setBaId(baId);
        baContactWherelogic.setSort(defaultSort);
        baContactWherelogic.setOrder(defaultOrder);
        baContactWherelogic.setStatusTarget(true);
        List<BmsBaContactEntity> bbceList = bmsBaContactService.selectListByWhereLogic(baContactWherelogic);
        if(bbceList!=null && bbceList.size() > 0){
            ArrayList<BmsBaContactForMobileEntity> bbcfmeList = new ArrayList<>();
            for(BmsBaContactEntity bbce : bbceList){
                BmsBaContactForMobileEntity bbcfme = new BmsBaContactForMobileEntity();
                bbcfme.setBa_id(bbce.getBaId());
                bbcfme.setComments(bbce.getComments());
                bbcfme.setCreateDate(bbce.getCreateDate());
                bbcfme.setCreateUser(bbce.getCreateUser());
                bbcfme.setEmail_addr(bbce.getEmail());
                bbcfme.setFax_num(bbce.getFaxNo());
                bbcfme.setHome_addr(bbce.getHomeAddr());
                bbcfme.setHome_phone(bbce.getHomePhone());
                bbcfme.setId(bbce.getId());
                bbcfme.setLmdate(bbce.getModDate());
                bbcfme.setLmuser(bbce.getModUser());
                bbcfme.setMobile_phone(bbce.getMobilePhone());
                bbcfme.setName(bbce.getName());
                bbcfme.setOrder_id(bbce.getOrderSeq());
                //bbcfme.setPriority();
                bbcfme.setSex(bbce.getSex());
                bbcfme.setTitle(bbce.getTitle());
                bbcfme.setValue_code(bbce.getValueCode());
                bbcfme.setWork_addr(bbce.getWorkAddr());
                bbcfme.setWork_phone(bbce.getWorkPhone());
                bbcfmeList.add(bbcfme);
            }
            bbfme.setRequestBAContactBeans(bbcfmeList);
        }
        return bbfme;
    }
    /*
     * 添加事件-post
     * */
    @PostMapping("/addEventForMobile")
    protected Boolean newEventEntityForMobile(
            @RequestParam("ba_id") String ba_id ,
            @RequestParam("type_id") String type_id ,
            @RequestParam("action_user_id") String action_user_id ,
            @RequestParam("action_date") LocalDateTime action_date ,
            @RequestParam("subject") String subject ,
            @RequestParam("contact_person") String contact_person ,
            @RequestParam(value = "next_contact_time" , required = false) LocalDate next_contact_time
    ){
        BmsBaEventEntity bmsBaEventEntity = new BmsBaEventEntity();
        bmsBaEventEntity.setBaId(ba_id);
        bmsBaEventEntity.setTypeId(type_id);
        bmsBaEventEntity.setActionUser(action_user_id);
        bmsBaEventEntity.setActionDate(action_date);
        bmsBaEventEntity.setSubject(subject);
        bmsBaEventEntity.setContactPerson(contact_person);
        if(next_contact_time!=null) {
            bmsBaEventEntity.setNextContactDate(next_contact_time);
        }
        return bmsBaEventService.insert(bmsBaEventEntity);
    }

    /*
     * 添加联系人-post
     * */
    @PostMapping("/addContactForMobile")
    protected Boolean newContactEntityForMobile(
            @RequestParam("ba_id") String ba_id ,
            @RequestParam("name") String name ,
            @RequestParam("title") String title ,
            @RequestParam("sex") String sex ,
            @RequestParam("value_code") String value_code ,
            @RequestParam("work_addr") String work_addr ,
            @RequestParam("home_addr") String home_addr ,
            @RequestParam("work_phone") String work_phone ,
            @RequestParam("home_phone") String home_phone ,
            @RequestParam("mobile_phone") String mobile_phone ,
            @RequestParam("comments") String comments ,
            @RequestParam("email_addr") String email_addr
    ){
        BmsBaContactEntity bmsBaContactEntity = new BmsBaContactEntity();
        bmsBaContactEntity.setBaId(ba_id);
        bmsBaContactEntity.setName(name);
        bmsBaContactEntity.setTitle(title);
        bmsBaContactEntity.setSex(sex);
        bmsBaContactEntity.setValueCode(value_code);
        bmsBaContactEntity.setWorkAddr(work_addr);
        bmsBaContactEntity.setHomeAddr(home_addr);
        bmsBaContactEntity.setWorkPhone(work_phone);
        bmsBaContactEntity.setHomePhone(home_phone);
        bmsBaContactEntity.setMobilePhone(mobile_phone);
        bmsBaContactEntity.setComments(comments);
        bmsBaContactEntity.setEmail(email_addr);
        return bmsBaContactService.insert(bmsBaContactEntity);
    }

    private final static List<String> updateColsForMobile = ImmutableList.of("valueCode","scaleCode","ownershipCode","contactsStatus","clientContactPerson","address","comments","phoneNo","modDate","modUser");
    /*
     * 移动端修改
     * */
    @PostMapping("/editBaForMobile/{baId}")
    protected Boolean updateEntityForMobile(
            @PathVariable("baId") String baId,
            @RequestParam("valueCode") String valueCode ,
            @RequestParam("scaleCode") String scaleCode ,
            @RequestParam("ownerShipCode") String ownerShipCode ,
            @RequestParam("contactsStatus") String contactsStatus ,
            @RequestParam("clientContactPerson") String clientContactPerson ,
            @RequestParam("address") String address ,
            @RequestParam("comments") String comments ,
            @RequestParam("phoneNo") String phoneNo
    ){
        BmsBaEntity bbe = new BmsBaEntity();
        bbe.setId(baId);
        bbe.setValueCode(valueCode);
        bbe.setScaleCode(scaleCode);
        bbe.setOwnershipCode(ownerShipCode);
        bbe.setContactsStatus(contactsStatus);
        bbe.setClientContactPerson(clientContactPerson);
        bbe.setAddress(address);
        bbe.setComments(comments);
        bbe.setPhoneNo(phoneNo);
        return bmsBaService.updatePartialColumnById(bbe,updateColsForMobile);
    }
    @PostMapping("/baVisitReportXlsExp")
    public View downloadBaVisitReportXlsExp(
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate,
            @RequestParam(value = "sourceCode" , required = false) String sourceCode,
            @RequestParam(value = "valueCode" , required = false) String valueCode,
            @RequestParam(value = "baTags" , required = false) List<String> baTags,
            @RequestParam(value = "searchOwnerUserStr" , required = false) String searchOwnerUserStr,
            @RequestParam(value = "searchOwnerEmptyFlag" , required = false) Boolean searchOwnerEmptyFlag
                                            ){
        return new BaVisitReportDownloadRender(
                fromDate,
                toDate ,
                sourceCode ,
                valueCode ,
                baTags ,
                searchOwnerUserStr ,
                searchOwnerEmptyFlag ,
                bmsBaService , basicKVGroupCacheService , dataJournalService , userUnionQuery , bmsBaEventService);
    }
    /*
     * 导出销售项目穿透分析表-post
     * */
    @PostMapping("/salesProjectReportXlsExp")
    public View salesProjectReportXlsExp( @RequestBody BaWhereLogic oriWhereLogic ){
        BaWhereLogic newWhereLogic = bmsBaService.setSearchWhereLogic(oriWhereLogic , oriWhereLogic.getSl_baViewType() , oriWhereLogic.getDoWarningViewFlag());
        return new SalesProjectReportDownloadRender(bmsBaService , basicKVGroupCacheService , bmsBaEventService , newWhereLogic);
    }
}
