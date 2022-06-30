package com.econage.extend.modular.projectwh88.web;

import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.projectwh88.service.BaContactWh88Service;
import com.econage.extend.modular.projectwh88.service.BaEventWh88Service;
import com.econage.extend.modular.projectwh88.service.BaRoleUsersWh88Service;
import com.econage.extend.modular.projectwh88.service.BaWh88Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baWh88")
public class BaWh88WebEndpoint extends BasicControllerImpl {
    private BaWh88Service baWh88Service;
    private BaEventWh88Service baEventWh88Service;
    private BaContactWh88Service baContactWh88Service;
    private BaRoleUsersWh88Service baRoleUsersWh88Service;
    @Autowired
    protected void setBaWh88Service(BaWh88Service baWh88Service) {
        this.baWh88Service = baWh88Service;
    }
    @Autowired
    protected void setBaEventWh88Service(BaEventWh88Service baEventWh88Service) {
        this.baEventWh88Service = baEventWh88Service;
    }
    @Autowired
    protected void setBaContactWh88Service(BaContactWh88Service baContactWh88Service) {
        this.baContactWh88Service = baContactWh88Service;
    }
    @Autowired
    protected void setBaRoleUsersWh88Service(BaRoleUsersWh88Service baRoleUsersWh88Service) {
        this.baRoleUsersWh88Service = baRoleUsersWh88Service;
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
                baWh88Service,
                sort,order,
                page,rows
        );
    }

    @GetMapping("/importBmsBa")
    protected BasicDataGridRows importBmsBa(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                baWh88Service,
                sort,order,
                page,rows
        );
        baWh88Service.importBmsData(data);
        return data;
    }
    @GetMapping("/importBmsBaEvent")
    protected BasicDataGridRows importBmsBaEvent(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                baEventWh88Service,
                sort,order,
                page,rows
        );
        baEventWh88Service.importBmsData(data);
        return null;
    }
    @GetMapping("/importBmsBaContact")
    protected BasicDataGridRows importBmsBaContact(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                baContactWh88Service,
                sort,order,
                page,rows
        );
        baContactWh88Service.importBmsData(data);
        return null;
    }
    @GetMapping("/importBmsBaAuth")
    protected BasicDataGridRows importBmsBaAuth(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                baRoleUsersWh88Service,
                sort,order,
                page,rows
        );
        baRoleUsersWh88Service.importBmsData(data);
        return null;
    }
}
