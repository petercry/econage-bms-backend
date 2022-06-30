package com.econage.extend.modular.bms.employeeManage.web;

import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.extend.modular.bms.employeeManage.entity.EmployeeInfoEntity;
import com.econage.extend.modular.bms.employeeManage.service.EmployeeExamineDetailService;
import com.econage.extend.modular.bms.employeeManage.service.EmployeeExamineSummaryService;
import com.econage.extend.modular.bms.employeeManage.service.EmployeeInfoService;
import com.econage.extend.modular.bms.employeeManage.service.EmployeePromoteDetailService;
import com.econage.extend.modular.bms.employeeManage.trivial.wherelogic.EmployeeInfoWherelogic;
import com.econage.extend.modular.bms.employeeManage.util.EmployeeExamineListDownloadRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import java.util.List;

@RestController
@RequestMapping("/bms/employeeManage")
public class EmployeeManageWebEndpoint extends BasicControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeManageWebEndpoint.class);
    private EmployeeInfoService employeeInfoService;
    private EmployeeExamineDetailService employeeExamineDetailService;
    private EmployeePromoteDetailService employeePromoteDetailService;
    private EmployeeExamineSummaryService employeeExamineSummaryService;
    @Autowired
    protected void setService(EmployeeInfoService employeeInfoService , EmployeeExamineDetailService employeeExamineDetailService , EmployeePromoteDetailService employeePromoteDetailService , EmployeeExamineSummaryService employeeExamineSummaryService) {
        this.employeeInfoService = employeeInfoService;
        this.employeeExamineDetailService = employeeExamineDetailService;
        this.employeePromoteDetailService = employeePromoteDetailService;
        this.employeeExamineSummaryService = employeeExamineSummaryService;
    }
    /*
     * 查询-get
     * */
    @PostMapping("/searchEmployee")
    protected List<EmployeeInfoEntity> search( @RequestBody EmployeeInfoWherelogic employeeInfoWherelogic){
        return employeeInfoService.setExamineDataIntoEntities(employeeInfoWherelogic);
    }
    @PostMapping("/employeeExamineListXlsExp")
    public View downloadEmployeeExamineListXlsExp( @RequestBody EmployeeInfoWherelogic employeeInfoWherelogic){
        return new EmployeeExamineListDownloadRender(employeeInfoWherelogic , employeeInfoService);
    }
}
