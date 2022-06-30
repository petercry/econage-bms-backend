package com.econage.extend.modular.bms.basic.web;

import com.econage.base.organization.member.ComponentMemberType;
import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.entity.UserGroupEntity;
import com.econage.base.organization.org.entity.helper.ComponentMemberEntity;
import com.econage.base.organization.org.entity.vo.PersonalRoleExistsOneByOneVO;
import com.econage.base.organization.org.service.UserGroupService;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.organization.org.trivial.meta.UserStatusEnum;
import com.econage.base.organization.org.trivial.wherelogic.UserGroupWhereLogic;
import com.econage.base.organization.org.trivial.wherelogic.UserWhereLogic;
import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.base.support.kv.entity.BasicKvEntity;
import com.econage.base.support.kv.service.BasicKVService;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.core.web.security.SecurityUtils;
import com.econage.extend.modular.bms.basic.entity.BmsOpActionDetailEntity;
import com.econage.extend.modular.bms.basic.entity.BmsOpActionEntity;
import com.econage.extend.modular.bms.basic.entity.BmsTagInfoEntity;
import com.econage.extend.modular.bms.basic.service.BmsDataJournalService;
import com.econage.extend.modular.bms.basic.service.BmsOpActionDetailService;
import com.econage.extend.modular.bms.basic.service.BmsOpActionService;
import com.econage.extend.modular.bms.basic.service.BmsTagInfoService;
import com.econage.extend.modular.bms.basic.trivial.wherelogic.BmsDataJournalWhereLogic;
import com.econage.extend.modular.bms.util.BmsConst;
import com.econage.extend.modular.bms.util.BriefUserEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bms/public")
public class BmsPublicWebEndpoint extends BasicControllerImpl {
    private UserUnionQuery userService;
    private BmsOpActionService actionService;
    private BmsOpActionDetailService actionDetService;
    private UserGroupService userGroupService;
    private BmsDataJournalService dataJournalService;
    private BasicKVService basicKVService;
    private BmsTagInfoService bmsTagInfoService;

    @Autowired
    protected void setBmsTagInfoService(BmsTagInfoService bmsTagInfoService) {
        this.bmsTagInfoService = bmsTagInfoService;
    }
    @Autowired
    protected void setBasicKVService(BasicKVService basicKVService) {
        this.basicKVService = basicKVService;
    }

    @Autowired
    protected void setDataJournalService(BmsDataJournalService dataJournalService) {
        this.dataJournalService = dataJournalService;
    }

    @Autowired
    protected void setUserService(UserUnionQuery userService) {
        this.userService = userService;
    }

    @Autowired
    protected void setActionService(BmsOpActionService actionService) {
        this.actionService = actionService;
    }

    @Autowired
    protected void setActionDetService(BmsOpActionDetailService actionDetService) {
        this.actionDetService = actionDetService;
    }

    @Autowired
    protected void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    /*
     * 根据姓名关键字获取人员列表-get
     * */
    @GetMapping("/user")
    protected List<BriefUserEntity> getUserListByNameQuery(
            @RequestParam("nameQuery") String nameQuery
    ) {
        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        userWhereLogic.setNameFuzzySearch(nameQuery);
        List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
        ArrayList<BriefUserEntity> briefUserV = new ArrayList<BriefUserEntity>();
        for (UserEntity ue : userV) {
            BriefUserEntity bue = new BriefUserEntity();
            bue.setId(ue.getId());
            bue.setMi(ue.getMi());
            briefUserV.add(bue);
        }
        return briefUserV;
    }

    /*
     * 添加操作记录-post
     * */
    @PostMapping("/action")
    protected BmsOpActionEntity newOpActionEntity(
            @RequestBody BmsOpActionEntity actionEntity
    ) {
        actionService.insert(actionEntity);
        if (actionEntity.getDetail() != null) {
            for (BmsOpActionDetailEntity detEntity : actionEntity.getDetail()) {
                detEntity.setActionId(actionEntity.getId());
                actionDetService.insert(detEntity);
            }
        }
        return actionService.selectById(actionEntity.getId());
    }

    /*
     * 获取延展的操作记录列表（模块及子模块）-get
     * */
    @GetMapping("/action/extSearch")
    protected Collection<BmsOpActionEntity> extSearchActionList(@RequestParam("objType") String objType, @RequestParam("objId") String objId, @RequestParam("extObjType") String extObjType
    ) {
        return actionService.extSearchOpAction(objType, objId, extObjType);
    }

    /*
     * 获取有效用户组列表（用于看板，过滤用不到的用户组）-get
     * */
    @GetMapping("/userGroupListForKanban")
    protected List<UserGroupEntity> getUserGroupListForKanban() {
        UserGroupWhereLogic userGroupWhereLogic = new UserGroupWhereLogic();
        userGroupWhereLogic.setValid(true);
        List<UserGroupEntity> oriUserGroupList = userGroupService.selectListByWhereLogic(userGroupWhereLogic);

        PersonalRoleExistsOneByOneVO roleOneByOneVO = new PersonalRoleExistsOneByOneVO();
        RuntimeUserDetails userAccount = SecurityHelper.getRuntimeUserAccount();
        roleOneByOneVO.setAdmin(userAccount.isAdmin());
        Map<String, Boolean> roleMap = new HashMap();
        roleOneByOneVO.setAuthenticationMap(roleMap);
        String[] roleStrArray = {BmsConst.BMS_KANBAN_TEAMVIEW_MODULAR_NAME+"_"+BmsConst.PERMISSION_AFTEAMVIEWTAB , BmsConst.BMS_KANBAN_TEAMVIEW_MODULAR_NAME+"_"+BmsConst.PERMISSION_IBPMTEAMVIEWTAB};
        for(String singleRoleStr : roleStrArray){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(SecurityUtils.joinRoleAuthorizeStr4SpringSecurity(new String[]{singleRoleStr}));
            roleMap.put(singleRoleStr, userAccount.getAuthorities().contains(authority));
        }

        String[] expectCodeArray = {"1456491016263516162"};   //用户组列表中除去"知识模块试用小组"
        List<UserGroupEntity> userGroupList = new ArrayList<>();
        for(UserGroupEntity userGroupEntity : oriUserGroupList){
            if(Arrays.asList(expectCodeArray).contains(userGroupEntity.getCode())){
               continue;
            }
            if(!roleOneByOneVO.isAdmin()){  //如果是管理员，就不用判断了
                String checkGroupName = userGroupEntity.getName();
                if(!roleMap.get(BmsConst.BMS_KANBAN_TEAMVIEW_MODULAR_NAME+"_"+BmsConst.PERMISSION_AFTEAMVIEWTAB) && checkGroupName.indexOf("iBpm")<0){ //不具备af团队权限，且当前团队是af的，则略过
                    continue;
                }else if(!roleMap.get(BmsConst.BMS_KANBAN_TEAMVIEW_MODULAR_NAME+"_"+BmsConst.PERMISSION_IBPMTEAMVIEWTAB) && checkGroupName.indexOf("iBpm")>=0){ //不具备ibpm团队权限，且当前团队是ibpm的，则略过
                    continue;
                }
            }
            userGroupList.add(userGroupEntity);
        }
        return userGroupList;
    }

    /*
     * 根据用户组code获取人员列表-get
     * */
    @GetMapping("/userListByUserGroupCode")
    protected List<BriefUserEntity> getUserListByUserGroupCode(
            @RequestParam("userGroupCode") String userGroupCode
    ) {
        Collection<ComponentMemberEntity> userV = userGroupService.selectMemberListByGroupCode(userGroupCode);
        ArrayList<BriefUserEntity> briefUserV = new ArrayList<BriefUserEntity>();
        for (ComponentMemberEntity cme : userV) {
            if (cme.getType() == ComponentMemberType.PERSONNEL) {
                BriefUserEntity bue = new BriefUserEntity();
                bue.setId(cme.getLinkId());
                bue.setMi(userService.selectUserMi(cme.getLinkId()));
                briefUserV.add(bue);
            }
        }
        return briefUserV;
    }

    /*
     * 获取本人信息-get
     * */
    @GetMapping("/myUserInfo")
    protected UserEntity getMyUserInfo() {
        String id = SecurityUtils.getRuntimeUserAccount().getUsername();
        UserEntity ue = userService.selectSingle(id);
        return ue;
    }

    /*
     * 获取ID-get
     * */
    @GetMapping("/getId")
    protected String getIdStr() {
        return IdWorker.getIdStr();
    }

//    /*
//     * 获取相关数据总和-get
//     * */
//    @GetMapping("/dataJournal/sumByModular")
//    protected Double sumWithDataJournalByModular(
//            @RequestParam("modular") String modular, @RequestParam("modular_inner_id") String modular_inner_id, @RequestParam("func_flag_str") String func_flag_str
//    ) {
//        return dataJournalService.sumWithDataJournalByModular(modular, modular_inner_id, func_flag_str);
//    }

    /*
     * 根据最终绑定项目，获取相关数据总和-get
     * */
    @GetMapping("/dataJournal/sumByFinalRelatedProject")
    protected Double sumWithDataJournalByFinalRelatedProject(
            @RequestParam("final_related_project_id") String final_related_project_id, @RequestParam("func_flag_str") String func_flag_str
    ) {
        return dataJournalService.sumWithDataJournalByFinalRelatedProjectId(final_related_project_id, func_flag_str);
    }

    /*
     * 根据绑定客户，获取只绑定了客户，未绑定最终项目的相关数据总和-get
     * */
    @GetMapping("/dataJournal/sumByNoFinalRelatedProjectJustBa")
    protected Double sumWithDataJournalByNoFinalRelatedProjectIdJustBa(
            @RequestParam("ba_id") String ba_id, @RequestParam("func_flag_str") String func_flag_str
    ) {
        return dataJournalService.sumWithDataJournalByNoFinalRelatedProjectIdJustBa(ba_id, func_flag_str);
    }

    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};

    @GetMapping("/dataJournal/search")
    protected BasicDataGridRows search(BmsDataJournalWhereLogic whereLogic, @RequestParam(value = "func_flag_str", required = false) String func_flag_str) {
        if (ArrayUtils.isEmpty(whereLogic.getSort())) {
            whereLogic.setSort(defaultSort);
        }
        if (ArrayUtils.isEmpty(whereLogic.getOrder())) {
            whereLogic.setOrder(defaultOrder);
        }
        if(whereLogic.getIsNoFinalRelatedProjectJustBaFlag()!=null && whereLogic.getIsNoFinalRelatedProjectJustBaFlag() == 1){
            whereLogic.setNoFinalRelatedProjectJustBa(true);
        }
        if (whereLogic.getPage() == 0) whereLogic.setPage(1);
        if (whereLogic.getRows() == 0) whereLogic.setRows(1000);
        whereLogic.setStatusTarget(true);
        if (!StringUtils.isEmpty(func_flag_str)) {
            String[] priV = func_flag_str.split(",");
            ArrayList<Integer> v = new ArrayList<>();
            for (String node : priV) {
                v.add(Integer.parseInt(node));
            }
            whereLogic.setFuncFlagList(v);
        }
        return BasicDataGridRows.create()
                .withRows(dataJournalService.selectListByWhereLogic(whereLogic, whereLogic.parsePagination()))
                .withTotal(dataJournalService.selectCountByWhereLogic(whereLogic));
    }

    @PostMapping("/addTag")
    protected BasicKvEntity addTag(
            @RequestBody BasicKvEntity basicKvEntity
    ) {
        basicKvEntity.setEnableInCreate(true);
        basicKvEntity.setEnableInSelect(true);
        basicKvEntity.setEnableInUpdate(true);
        basicKVService.insert(basicKvEntity);
        return basicKVService.selectById(basicKvEntity.getId());
    }
    /*
     * 获取已选标签
     * */
    @GetMapping("/getSelectedTags")
    protected List<BmsTagInfoEntity> getSelectedTags(
            @RequestParam("modular") String modular , @RequestParam("modularInnerId") String modularInnerId
    ) {
        return bmsTagInfoService.getSelectedTag(modular , modularInnerId);
    }
}
