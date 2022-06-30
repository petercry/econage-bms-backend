package com.econage.extend.modular.bms.afhwc.remoting;

import com.econage.extend.modular.bms.afhwc.config.AfhwcRemoting;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcDriveTaskRequestDTO;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcDriveTaskResponseDTO;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcWorkflowInstResponseDTO;
import com.econage.extend.modular.bms.util.BmsConst;
import com.flowyun.cornerstone.web.client.rest.annotations.RestMethod;
import com.flowyun.cornerstone.web.client.rest.annotations.RestPath;
import com.flowyun.cornerstone.web.client.rest.annotations.RestUriVariable;
import org.springframework.http.HttpMethod;

/*
 * 认证相关的方法
 * */
@AfhwcRemoting
public interface AfhwcWfRemoting {
    /*
     * 获取流程实例信息
     * */
    @RestPath("/openapi/workflow/getInstance?access_token={" + BmsConst.AFHWC_ACCESS_TOKEN_URI_VAR_NAME + "}&account={account}&workflowId={workflowId}")
    @RestMethod(HttpMethod.GET)
    AfhwcWorkflowInstResponseDTO getWorkflowInstData(
            @RestUriVariable(BmsConst.AFHWC_ACCESS_TOKEN_URI_VAR_NAME) String token,
            @RestUriVariable("account") String account ,
            @RestUriVariable("workflowId") Long workflowId
    );
    /**
     * 驱动流程
     */
    @RestPath("/openapi/workflow/driverTask?access_token={" + BmsConst.AFHWC_ACCESS_TOKEN_URI_VAR_NAME + "}&account={account}&tenantId={tenantId}")
    @RestMethod(HttpMethod.POST)
    AfhwcDriveTaskResponseDTO driveTask(
            @RestUriVariable(BmsConst.AFHWC_ACCESS_TOKEN_URI_VAR_NAME) String token,
            @RestUriVariable("account") String account,
            @RestUriVariable("tenantId") String tenantId,
            AfhwcDriveTaskRequestDTO reqDTO
    );

}
