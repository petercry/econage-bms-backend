package com.econage.extend.modular.projectcrm88.web;

import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.projectcrm88.service.Ba88CrmService;
import com.econage.extend.modular.projectcrm88.service.BaContact88CrmService;
import com.econage.extend.modular.projectcrm88.service.BaEvent88CrmService;
import com.econage.extend.modular.projectcrm88.service.BaRoleUsers88CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ba88Crm")
public class Ba88CrmWebEndpoint extends BasicControllerImpl {
    private Ba88CrmService ba88CrmService;
    private BaEvent88CrmService baEvent88CrmService;
    private BaContact88CrmService baContact88CrmService;
    private BaRoleUsers88CrmService baRoleUsers88CrmService;
    @Autowired
    protected void setBa88CrmService(Ba88CrmService ba88CrmService) {
        this.ba88CrmService = ba88CrmService;
    }
    @Autowired
    protected void setBaEvent88CrmService(BaEvent88CrmService baEvent88CrmService) {
        this.baEvent88CrmService = baEvent88CrmService;
    }
    @Autowired
    protected void setBaContact88CrmService(BaContact88CrmService baContact88CrmService) {
        this.baContact88CrmService = baContact88CrmService;
    }
    @Autowired
    protected void setBaRoleUsers88CrmService(BaRoleUsers88CrmService baRoleUsers88CrmService) {
        this.baRoleUsers88CrmService = baRoleUsers88CrmService;
    }
    @GetMapping("/importBmsBa")
    protected BasicDataGridRows importBmsBa(
            @RequestParam("sort") String[] sort,
            @RequestParam("order") String[] order,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ){
        BasicDataGridRows data = getPaginationWithoutWhereLogic(
                ba88CrmService,
                sort,order,
                page,rows
        );
        ba88CrmService.importBmsData(data);
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
                baEvent88CrmService,
                sort,order,
                page,rows
        );
        baEvent88CrmService.importBmsData(data);
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
                baContact88CrmService,
                sort,order,
                page,rows
        );
        baContact88CrmService.importBmsData(data);
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
                baRoleUsers88CrmService,
                sort,order,
                page,rows
        );
        baRoleUsers88CrmService.importBmsData(data);
        return null;
    }
}
