package com.econage.extend.modular.bms.afhwc.master;

import com.econage.extend.modular.bms.afhwc.config.AfhwcRemotingEnabled;
import com.econage.extend.modular.bms.afhwc.config.AfhwcRemotingSetting;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcDriveTaskRequestDTO;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcDriveTaskResponseDTO;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcInstanceFormRequestDTO;
import com.econage.extend.modular.bms.afhwc.entity.dto.AfhwcWorkflowInstResponseDTO;
import com.econage.extend.modular.bms.afhwc.entity.model.AfhwcDriveTaskModel;
import com.econage.extend.modular.bms.afhwc.entity.model.AfhwcWorkflowInstModel;
import com.econage.extend.modular.bms.afhwc.remoting.AfhwcWfRemoting;
import com.econage.extend.modular.bms.afhwc.web.AfhwcRemotingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
@Component
@AfhwcRemotingEnabled
public class AfhwcWfMaster {
    private AfhwcWfRemoting afhwcWfRemoting;
    private AfhwcAuthMaster afhwcAuthMaster;
    private AfhwcRemotingSetting setting;
    private ObjectMapper objectMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    void setWired(
            AfhwcWfRemoting afhwcAuthRemoting,
            AfhwcAuthMaster afhwcAuthMaster,
            AfhwcRemotingSetting setting,
            ObjectMapper objectMapper
    ) {
        this.afhwcWfRemoting = afhwcAuthRemoting;
        this.afhwcAuthMaster = afhwcAuthMaster;
        this.setting = setting;
        this.objectMapper = objectMapper;
    }

    public AfhwcWorkflowInstModel getWorkflowInstanceData(long workflowId){
        Assert.isTrue(workflowId!=0,"empty workflowId");

        AfhwcInstanceFormRequestDTO requestDTO = new AfhwcInstanceFormRequestDTO();
        requestDTO.setWorkflowId(workflowId);
        requestDTO.setAccount(setting.getAnonymousAccount());

        AfhwcWorkflowInstResponseDTO dto = afhwcWfRemoting.getWorkflowInstData(
                afhwcAuthMaster.fetchAccessToken(),
                setting.getAnonymousAccount(),
                workflowId
        );
        if(dto.getStatus()!=200){
            throw new AfhwcRemotingException(dto.getMsg());
        }
        return dto.getData();
    }


    public AfhwcDriveTaskResponseDTO driveTask(String tenantId , String account , String reqDataId , String taskDataId , String taskId , ObjectNode itemObj , ObjectNode fileParamObj){
        AfhwcDriveTaskRequestDTO reqDTO = new AfhwcDriveTaskRequestDTO();
        reqDTO.setFlowTypeId(0);
        reqDTO.setReqDataId(reqDataId);
        reqDTO.setTaskDataId(taskDataId);
        reqDTO.setTaskId(taskId);
        reqDTO.setDecisionId("0");
        reqDTO.setDecision("1");
        reqDTO.setWakeUp(true);
        reqDTO.setItemParams(itemObj);
        reqDTO.setFileParams(fileParamObj);

        AfhwcDriveTaskResponseDTO dto = afhwcWfRemoting.driveTask(afhwcAuthMaster.fetchAccessToken() , account , tenantId , reqDTO);

        if(dto.getStatus() == 200) {
            AfhwcDriveTaskModel model = objectMapper.convertValue(dto.getData(), AfhwcDriveTaskModel.class);
            dto.setModel(model);
        }
        return dto;
    }


}
