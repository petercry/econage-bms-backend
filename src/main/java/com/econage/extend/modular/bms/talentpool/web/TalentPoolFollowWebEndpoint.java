package com.econage.extend.modular.bms.talentpool.web;

import com.econage.core.basic.i18n.I18nUtils4Web;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.extend.modular.bms.talentpool.entity.BmsTalentFollowEntity;
import com.econage.extend.modular.bms.talentpool.service.TalentPoolFollowService;
import com.econage.extend.modular.bms.talentpool.trival.meta.FollowTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author econage
 * 跟进情况
 */
@RestController
@RequestMapping("/bms/talent-pool/follow")
public class TalentPoolFollowWebEndpoint extends BasicControllerImpl {
     private TalentPoolFollowService talentPoolFollowService;

    @Autowired
    void setAutowired(TalentPoolFollowService talentPoolFollowService) {
        this.talentPoolFollowService = talentPoolFollowService;
    }

    @GetMapping("")
    protected Object querySingle(
            @RequestParam("id") String id
    ){
        return talentPoolFollowService.querySingle(id);
    }


    @PostMapping("")
    protected BmsTalentFollowEntity newEntity(
            @RequestBody BmsTalentFollowEntity talentFollowEntity
    ){
        Assert.hasText(talentFollowEntity.getInfoId()," no infoId");
        talentPoolFollowService.insert(talentFollowEntity);
        return talentPoolFollowService.selectById(talentFollowEntity.getId());
    }

    @DeleteMapping("")
    protected Object deleteEntity(
            @RequestParam("id") String commonDemoId
    ){
        return talentPoolFollowService.deleteById(commonDemoId);
    }


    @PutMapping("")
    protected BmsTalentFollowEntity updateEntity(
            @RequestBody BmsTalentFollowEntity followEntity
    ){
        Assert.hasLength(followEntity.getId(),"id is null");
        talentPoolFollowService.updateById(followEntity);
        return talentPoolFollowService.selectById(followEntity.getId());
    }


    @GetMapping("/list-by-info")
    protected Object listByInfo(@RequestParam("infoId") String infoId
    ){
         return talentPoolFollowService.selectListByFk(infoId).stream()
                 .sorted(Comparator.comparing(BmsTalentFollowEntity::getDate).reversed())
                 .collect(Collectors.toList());
    }

    @GetMapping("/type-enum")
    protected Map<String, String> getFollowType(){
        return I18nUtils4Web.getI18nEnumMap(FollowTypeEnum.class);
    }



}
