package com.econage.extend.modular.bms.talentpool.web;

import com.econage.base.support.kv.entity.BasicKvEntity;
import com.econage.base.support.kv.service.BasicKVService;
import com.econage.core.basic.exception.EconageBusinessException;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentInfoEntity;
import com.econage.extend.modular.bms.talentpool.service.TalentPoolInfoService;
import com.econage.extend.modular.bms.talentpool.trival.wherelogic.TalentPoolInfoWhereLogic;
import com.econage.extend.modular.bms.util.BmsConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author econage
 */
@RestController
@RequestMapping("/bms/talent-pool/info")
public class TalentPoolInfoWebEndpoint extends BasicControllerImpl {
    private TalentPoolInfoService talentPoolInfoService;

    private BasicKVService basicKVService;

    @Autowired
    void setAutowired(TalentPoolInfoService talentPoolInfoService, BasicKVService basicKVService) {
        this.talentPoolInfoService = talentPoolInfoService;
        this.basicKVService = basicKVService;
    }



    @PostMapping("")
    protected BmsTalentInfoEntity newEntity(
            @RequestBody BmsTalentInfoEntity bmsTalentInfoEntity
    ) {
        talentPoolInfoService.insert(bmsTalentInfoEntity);
        return talentPoolInfoService.selectById(bmsTalentInfoEntity.getId());
    }

    @PostMapping("/label")
    protected BasicKvEntity newLabel(
            @RequestBody BasicKvEntity basicKvEntity
    ) {
        basicKvEntity.setGroup(BmsConst.BMS_TALENT_POOL_LABEL_GROUP_ID);
        basicKvEntity.setEnableInCreate(true);
        basicKvEntity.setEnableInSelect(true);
        basicKvEntity.setEnableInUpdate(true);
        basicKVService.insert(basicKvEntity);
        return basicKVService.selectById(basicKvEntity.getId());
    }


    @DeleteMapping("")
    protected Object deleteEntity(
            @RequestParam("id") String commonDemoId
    ) {
        return talentPoolInfoService.deleteById(commonDemoId);
    }

    @DeleteMapping("/label")
    protected Object deleteLabel(
            @RequestParam("label") String label
    ) {
        return basicKVService.deleteById(label);
    }


    @PutMapping("")
    protected BmsTalentInfoEntity updateEntity(
            @RequestBody BmsTalentInfoEntity bmsTalentInfoEntity
    ) {
        Assert.hasLength(bmsTalentInfoEntity.getId(), "id is null");
        talentPoolInfoService.updateById(bmsTalentInfoEntity);
        return talentPoolInfoService.selectById(bmsTalentInfoEntity.getId());
    }
    /*
     * 少量字段-patch
     * */
    @PatchMapping("/{taId}/folder/{folder}")

    protected void changeTaFolder(
            @PathVariable("taId") String taId,
            @PathVariable("folder") String folder
    ){
        talentPoolInfoService.changeTaFolder(taId , Integer.parseInt(folder));
    }

    @GetMapping("/single")
    protected BmsTalentInfoEntity singleEntity(
            @RequestParam("id") String commonDemoId
    ) {
        BmsTalentInfoEntity infoEntity = talentPoolInfoService.selectById(commonDemoId);
      // talentPoolInfoService.fillFollowResultList(infoEntity);
        return infoEntity;
    }

    @GetMapping("/list")
    protected BasicDataGridRows defaultPaginationRows(
            TalentPoolInfoWhereLogic whereLogic
    ) {
        if(whereLogic.getFolderId() == -1) whereLogic.setFolderId(null);
        return BasicDataGridRows.create()
                .withRows(talentPoolInfoService.selectListByWhereLogic(whereLogic))
                .withTotal(talentPoolInfoService.selectCountByWhereLogic(whereLogic));
    }

    @GetMapping("/query-by-name-phone")
    protected Object queryByNamePhone(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone
    ) {

        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(phone)) {
            throw new EconageBusinessException(" no name or phone ");
        }

        return talentPoolInfoService.queryByNameOrPhone(phone, name);
    }

}
