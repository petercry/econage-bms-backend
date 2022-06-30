package com.econage.extend.modular.bms.project.component.auth.web;

import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.project.component.auth.entity.ProjectAuthEntity;
import com.econage.extend.modular.bms.project.component.auth.service.ProjectAuthService;
import com.econage.extend.modular.bms.project.component.auth.trival.wherelogic.ProjectAuthWhereLogic;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bms/project/auth")
public class ProjectAuthWebEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectAuthWebEndpoint.class);
    private ProjectAuthService projectAuthService;
    @Autowired
    protected void setProjectAuthService(ProjectAuthService projectAuthService) {
        this.projectAuthService = projectAuthService;
    }
    /*
     * 添加-post
     * */
    @PostMapping("")
    protected void newEntity( @RequestParam("roleKey") String roleKey , @RequestParam("roleUser") String roleUser , @RequestParam("projectId") String projectId
    ){
        String[] roleUserList = roleUser.split(",");//以逗号分割
        for (String roleUserNode : roleUserList) {
            System.out.println("roleUserNode-->>>" + roleUserNode);
            if(!projectAuthService.isAuthExist(projectId , roleKey , roleUserNode)) { //确认相同记录不存在
                ProjectAuthEntity projectAuthEntity = new ProjectAuthEntity();
                projectAuthEntity.setProjectId(projectId);
                projectAuthEntity.setKey(roleKey);
                projectAuthEntity.setOrgId(roleUserNode);
                projectAuthEntity.setLinkId(roleUserNode.substring(roleUserNode.indexOf(".") + 1));
                projectAuthEntity.setExpress(roleUserNode.substring(roleUserNode.indexOf(".") + 1));
                projectAuthEntity.setType(1);
                projectAuthService.insert(projectAuthEntity);
            }
        }
    }
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    //查询团队成员列表
    @GetMapping("/search/{projectId}")
    protected BasicDataGridRows searchTeamMemberContact(ProjectAuthWhereLogic whereLogic){
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(projectAuthService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination())).withTotal(projectAuthService.selectCountByWhereLogic(whereLogic));
    }
    /*
     * 删除团队成员-patch
     * */
    @PatchMapping("/{memberId}/status")
    protected void changeMemberValid(
            @PathVariable("memberId") String memberId,
            @RequestParam("action") String action
    ){
        projectAuthService.changeProjectTeamMemberValid(memberId , BooleanUtils.toBooleanObject(action));
    }
}
