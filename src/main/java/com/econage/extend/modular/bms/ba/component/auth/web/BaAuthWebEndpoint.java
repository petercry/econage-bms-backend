package com.econage.extend.modular.bms.ba.component.auth.web;

import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.econage.extend.modular.bms.ba.component.auth.service.BaAuthService;
import com.econage.extend.modular.bms.ba.component.auth.trival.wherelogic.BaAuthWhereLogic;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bms/ba/auth")
public class BaAuthWebEndpoint extends BasicControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaAuthWebEndpoint.class);
    private BaAuthService baAuthService;
    @Autowired
    protected void setBaAuthService(BaAuthService baAuthService) {
        this.baAuthService = baAuthService;
    }
    /*
     * 添加-post
     * */
    @PostMapping("")
    protected void newEntity( @RequestParam("roleKey") String roleKey , @RequestParam("roleUser") String roleUser , @RequestParam("baId") String baId
    ){
        String[] roleUserList = roleUser.split(",");//以逗号分割
        for (String roleUserNode : roleUserList) {
            //System.out.println("roleUserNode-->>>" + roleUserNode);
            if(!baAuthService.isAuthExist(baId , roleKey , roleUserNode)) { //确认相同记录不存在
                BaAuthEntity baAuthEntity = new BaAuthEntity();
                baAuthEntity.setBaId(baId);
                baAuthEntity.setKey(roleKey);
                baAuthEntity.setOrgId(roleUserNode);
                baAuthEntity.setLinkId(roleUserNode.substring(roleUserNode.indexOf(".") + 1));
                baAuthEntity.setExpress(roleUserNode.substring(roleUserNode.indexOf(".") + 1));
                baAuthEntity.setType(1);
                baAuthService.insert(baAuthEntity);
            }
        }
    }
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    //查询当前用户是否在该客户团队中
    @GetMapping("/check/{baId}")
    protected Integer checkAuth(BaAuthWhereLogic whereLogic){
        Integer re = 0 ;
        RuntimeUserDetails userAccount = SecurityHelper.getRuntimeUserAccount();
        if(userAccount.isAdmin()) return 1;
        String userName = userAccount.getUsername();
        String userId = userAccount.getUserId();
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        whereLogic.setStatusTarget(true);
        List<BaAuthEntity> authEntities = baAuthService.selectListByWhereLogic(whereLogic);
        for(BaAuthEntity bae : authEntities){
            if(bae.getLinkId().equals(userAccount.getUserId())){
                re = 1;break;
            }
        }
        return re;
    }
    //查询团队成员列表
    @GetMapping("/search/{baId}")
    protected BasicDataGridRows searchTeamMember(BaAuthWhereLogic whereLogic){
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(baAuthService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination())).withTotal(baAuthService.selectCountByWhereLogic(whereLogic));
    }
    /*
     * 删除团队成员-patch
     * */
    @PatchMapping("/{memberId}/status")
    protected void changeMemberValid(
            @PathVariable("memberId") String memberId,
            @RequestParam("action") String action
    ){
        baAuthService.changeBaTeamMemberValid(memberId , BooleanUtils.toBooleanObject(action));
    }
    /*
     * 添加-post
     * */
    @PostMapping("/setBatch")
    protected void setRoleBatch( @RequestParam("op") String op , @RequestParam("oldRole") String oldRole ,
                                 @RequestParam("oldRoleUser") String oldRoleUser , @RequestParam("newRole") String newRole , @RequestParam("newRoleUser") String newRoleUser ,
                                 @RequestParam("baIdStr") String baIdStr
    ){
        String[] baIdV = baIdStr.split(",");
        String[] newRoleUserOrgIdV = newRoleUser.split(",");
        if(op.equals("1")) { //添加权限
            for (String checkBaId : baIdV) {
                for(String checkRoleUserOrgId : newRoleUserOrgIdV) {
                    if (!baAuthService.isAuthExist(checkBaId, newRole, checkRoleUserOrgId)) { //确认相同记录不存在
                        BaAuthEntity baAuthEntity = new BaAuthEntity();
                        baAuthEntity.setBaId(checkBaId);
                        baAuthEntity.setKey(newRole);
                        baAuthEntity.setOrgId(checkRoleUserOrgId);
                        baAuthEntity.setLinkId(checkRoleUserOrgId.substring(checkRoleUserOrgId.indexOf(".") + 1));
                        baAuthEntity.setExpress(checkRoleUserOrgId.substring(checkRoleUserOrgId.indexOf(".") + 1));
                        baAuthEntity.setType(1);
                        baAuthService.insert(baAuthEntity);
                    }
                }
            }
        }else if(op.equals("2")) { //删除权限
            for (String checkBaId : baIdV) {
                BaAuthWhereLogic baAuthWhereLogic = new BaAuthWhereLogic();
                baAuthWhereLogic.setBaId(checkBaId);
                baAuthWhereLogic.setStatusTarget(true);
                baAuthWhereLogic.setSort(defaultSort);
                baAuthWhereLogic.setOrder(defaultOrder);
                List<BaAuthEntity> baAuthEntityList = baAuthService.selectListByWhereLogic(baAuthWhereLogic);
                for (String checkRoleUserOrgId : newRoleUserOrgIdV) {
                    String checkLinkId = checkRoleUserOrgId;
                    if(checkLinkId.indexOf(".")>0) checkLinkId = checkLinkId.substring(checkLinkId.lastIndexOf(".")+1);
                    for(BaAuthEntity checkBaAuthEntity : baAuthEntityList){
                        //if(checkBaAuthEntity.getOrgId().equals(checkRoleUserOrgId) && checkBaAuthEntity.getKey().equals(newRole)){
                        if(checkBaAuthEntity.getLinkId().equals(checkLinkId) && checkBaAuthEntity.getKey().equals(newRole)){
                            baAuthService.changeBaTeamMemberValid(checkBaAuthEntity.getId() , false);
                        }
                    }
                }
            }
        }else if(op.equals("3")) { //更新权限
            for (String checkBaId : baIdV) {
                if (baAuthService.isAuthExist(checkBaId, oldRole, oldRoleUser)) {  //更新权限，先确定某个客户下有相应的旧权限。如果有，则将旧权限删除，并加上新权限
                    BaAuthWhereLogic baAuthWhereLogic = new BaAuthWhereLogic();
                    baAuthWhereLogic.setBaId(checkBaId);
                    baAuthWhereLogic.setStatusTarget(true);
                    baAuthWhereLogic.setSort(defaultSort);
                    baAuthWhereLogic.setOrder(defaultOrder);
                    List<BaAuthEntity> baAuthEntityList = baAuthService.selectListByWhereLogic(baAuthWhereLogic);
                    for(BaAuthEntity checkBaAuthEntity : baAuthEntityList){
                        if(checkBaAuthEntity.getOrgId().equals(oldRoleUser) && checkBaAuthEntity.getKey().equals(oldRole)){
                            baAuthService.changeBaTeamMemberValid(checkBaAuthEntity.getId() , false);
                        }
                    }
                    for(String checkRoleUserOrgId : newRoleUserOrgIdV) {
                        if (!baAuthService.isAuthExist(checkBaId, newRole, checkRoleUserOrgId)) { //确认相同记录不存在
                            BaAuthEntity baAuthEntity = new BaAuthEntity();
                            baAuthEntity.setBaId(checkBaId);
                            baAuthEntity.setKey(newRole);
                            baAuthEntity.setOrgId(checkRoleUserOrgId);
                            baAuthEntity.setLinkId(checkRoleUserOrgId.substring(checkRoleUserOrgId.indexOf(".") + 1));
                            baAuthEntity.setExpress(checkRoleUserOrgId.substring(checkRoleUserOrgId.indexOf(".") + 1));
                            baAuthEntity.setType(1);
                            baAuthService.insert(baAuthEntity);
                        }
                    }
                }
            }
        }
    }
}
