package com.econage.extend.modular.projectwh88.web;

import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.projectwh88.service.ProjectEventWh88Service;
import com.econage.extend.modular.projectwh88.service.ProjectRoleUsersWh88Service;
import com.econage.extend.modular.projectwh88.service.ProjectWh88Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/projectWh88")
public class ProjectWh88WebEndpoint extends BasicControllerImpl {
    private ProjectWh88Service projectWh88Service;
    private ProjectEventWh88Service projectEventWh88Service;
    private ProjectRoleUsersWh88Service projectRoleUsersWh88Service;
    @Autowired
    protected void setProjectWh88Service(ProjectWh88Service projectWh88Service) {
        this.projectWh88Service = projectWh88Service;
    }

    @Autowired
    protected void setProjectEventWh88Service(ProjectEventWh88Service projectEventWh88Service) {
        this.projectEventWh88Service = projectEventWh88Service;
    }
    @Autowired
    protected void setProjectRoleUsersWh88Service(ProjectRoleUsersWh88Service projectRoleUsersWh88Service) {
        this.projectRoleUsersWh88Service = projectRoleUsersWh88Service;
    }
    /*
     * 查询-get
     * */
    @GetMapping("/search")
    protected BasicDataGridRows defaultPaginationRows(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        return getPaginationWithoutWhereLogic(
                projectWh88Service,
                sort,order,
                page,rows
        );
    }

    @GetMapping("/importBmsProject")
    protected BasicDataGridRows importBmsProject(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                projectWh88Service,
                sort,order,
                page,rows
        );
        projectWh88Service.importBmsData(data);
        return data;
    }

    @GetMapping("/importBmsProjectEvent")
    protected BasicDataGridRows importBmsProjectEvent(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                projectEventWh88Service,
                sort,order,
                page,rows
        );
        projectEventWh88Service.importBmsData(data);
        return data;
    }

    @GetMapping("/importBmsProjectAuth")
    protected BasicDataGridRows importBmsProjectAuth(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                projectRoleUsersWh88Service,
                sort,order,
                page,rows
        );
        projectRoleUsersWh88Service.importBmsData(data);
        return null;
    }
}
