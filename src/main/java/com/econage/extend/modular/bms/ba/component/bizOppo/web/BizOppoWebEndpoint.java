package com.econage.extend.modular.bms.ba.component.bizOppo.web;

import com.econage.base.organization.org.entity.DeptEntity;
import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.core.web.extension.controller.BasicControllerImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.component.auth.entity.BaAuthEntity;
import com.econage.extend.modular.bms.ba.component.auth.service.BaAuthService;
import com.econage.extend.modular.bms.ba.component.auth.trival.wherelogic.BaAuthWhereLogic;
import com.econage.extend.modular.bms.ba.component.auth.web.BaAuthWebEndpoint;
import com.econage.extend.modular.bms.ba.component.bizOppo.entity.BizOppoEntity;
import com.econage.extend.modular.bms.ba.component.bizOppo.service.BizOppoService;
import com.econage.extend.modular.bms.ba.component.bizOppo.trival.wherelogic.BizOppoWhereLogic;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.elasticsearch.common.util.ObjectArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/bms/ba/bizOppo")
public class BizOppoWebEndpoint  extends BasicControllerImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizOppoWebEndpoint.class);
    private BizOppoService bizOppoService;
    private RestTemplate restTemplate;
    @Autowired
    protected void setService(BizOppoService bizOppoService , RestTemplate restTemplate) {
        this.bizOppoService = bizOppoService;
        this.restTemplate = restTemplate;
    }

    /*
     * 添加-post
     * */
    @PostMapping("")
    protected BizOppoEntity newEntity(
            @RequestBody BizOppoEntity bizOppoEntity
    ){
        bizOppoService.insert(bizOppoEntity);
        return bizOppoService.selectById(bizOppoEntity.getId());
    }
    /*
     * 几乎全部字段-put
     * */
    @PutMapping("/{bizOppoId}")
    protected BizOppoEntity updateEntity(
            @PathVariable("bizOppoId") String bizOppoId,
            @RequestBody BizOppoEntity bizOppoEntity
    ){
        bizOppoEntity.setId(bizOppoId);
        bizOppoService.updateAllColumnById(bizOppoEntity);
        return bizOppoService.selectById(bizOppoId);
    }
    /*
     * 少量字段-patch
     * */
    @PatchMapping("/{bizOppoId}/status")
    protected void patchEntityStatus(
            @PathVariable("bizOppoId") String bizOppoId,
            @RequestParam("action") String action
    ){
        bizOppoService.changeBizOppoStatus(bizOppoId , BooleanUtils.toBooleanObject(action));
    }
    private final String[] defaultSort = {"order_seq_"};
    private final String[] defaultOrder = {"desc"};
    //查询商机列表
    @GetMapping("/search")
    protected BasicDataGridRows search(BizOppoWhereLogic whereLogic){
        if(ArrayUtils.isEmpty(whereLogic.getSort())){
            whereLogic.setSort(defaultSort);
        }
        if(ArrayUtils.isEmpty(whereLogic.getOrder())){
            whereLogic.setOrder(defaultOrder);
        }
        whereLogic.setStatusTarget(true);
        return BasicDataGridRows.create()
                .withRows(bizOppoService.selectListByWhereLogic(whereLogic,whereLogic.parsePagination())).withTotal(bizOppoService.selectCountByWhereLogic(whereLogic));
    }
    //查询商机统计数字
    @GetMapping("/statisticsCount")
    protected ObjectNode statisticsCount(){
        ObjectNode obj = new ObjectMapper().createObjectNode();
        for(int i = 1;i<4;i++) {
            ObjectNode nodeObj = new ObjectMapper().createObjectNode();
            BizOppoWhereLogic w1 = new BizOppoWhereLogic();
            w1.setStatusTarget(true);
            w1.setSourceFlag(i);
            BizOppoWhereLogic w2 = new BizOppoWhereLogic();
            w2.setStatusTarget(true);
            w2.setSourceFlag(i);
            w2.setStatisticsDay(7);
            BizOppoWhereLogic w3 = new BizOppoWhereLogic();
            w3.setStatusTarget(true);
            w3.setSourceFlag(i);
            w3.setStatisticsDay(1);
            nodeObj.put("totalCount", "" + bizOppoService.selectCountByWhereLogic(w1));
            nodeObj.put("day7Count", "" + bizOppoService.selectCountByWhereLogic(w2));
            nodeObj.put("todayCount", "" + bizOppoService.selectCountByWhereLogic(w3));
            obj.putPOJO("func"+i , nodeObj);
        }
        return obj;
    }
    /*
     * 批量调接口添加商机-post
     * */
    @PostMapping("/addBizOppoBatch")
    protected String addBizOppoBatch(
            @RequestParam String strTime
    ) throws IOException, ParseException {
        return bizOppoService.addBizOppoBatch(strTime);
    }
}
