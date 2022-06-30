package com.econage.extend.modular.bms.ba.component.bizOppo.service;

import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.bizOppo.entity.BizOppoEntity;
import com.econage.extend.modular.bms.ba.component.bizOppo.entity.TmpLibBizOppoEntity;
import com.econage.extend.modular.bms.ba.component.bizOppo.mapper.BizOppoMapper;
import com.econage.extend.modular.bms.util.BmsHelper;
import com.econage.extend.modular.bms.util.TmpLibGeneralEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BizOppoService extends ServiceImpl<BizOppoMapper, BizOppoEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BizOppoService.class);
    private RestTemplate restTemplate;
    @Autowired
    protected void setService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");

    @Transactional(rollbackFor = Throwable.class)
    public boolean changeBizOppoStatus(String baId , boolean status){
        if(StringUtils.isEmpty(baId)){
            return false;
        }
        BizOppoEntity bizOppoEntity = new BizOppoEntity();
        bizOppoEntity.setId(baId);
        bizOppoEntity.setValid(status);
        return updatePartialColumnById(bizOppoEntity,validUpdateCols);
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BizOppoEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
    }
    public String addBizOppoBatch(String strTime) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String r = "";
        String url = "http://192.168.4.22:30003/zentao/openapi/trial/list?dateTime=";
        url += strTime;
        Resource resp = restTemplate.getForObject(url, Resource.class);
        String respStr = IOUtils.toString(resp.getInputStream() , StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        TmpLibGeneralEntity tmpLibGeneralEntity = objectMapper.readValue(respStr, TmpLibGeneralEntity.class);
        if(tmpLibGeneralEntity.getStatus()!=null && tmpLibGeneralEntity.getStatus() == 200){
            if(tmpLibGeneralEntity.getData().getClass() == ArrayList.class){
                ArrayList<TmpLibBizOppoEntity> boList = objectMapper.convertValue(tmpLibGeneralEntity.getData(), new TypeReference< ArrayList<TmpLibBizOppoEntity>>() {});
                for(TmpLibBizOppoEntity tlboe : boList){
                    BizOppoEntity boe = new BizOppoEntity();
                    boe.setId(tlboe.getSourcePt() + "_" + tlboe.getId());
                    boe.setSourceId(tlboe.getSourceId());
                    boe.setSourceFlag(BmsHelper.getBizOppoSourceFlagByDesc(tlboe.getSourcePt()));
                    boe.setApplyPhone(tlboe.getPhone());
                    boe.setApplyContactorName(tlboe.getContractName());
                    boe.setApplyBaName(tlboe.getTenantName());
                    String dateStr = tlboe.getSourceCreateDate();
                    if(dateStr.length() == 16) dateStr += ":00";
                    boe.setSourceApplyDate(Instant.ofEpochMilli( sdf.parse(dateStr).getTime() ).atZone( ZoneId.systemDefault() ).toLocalDateTime());
                    try {
                        insert(boe);
                    }catch (Exception ex){
                        LOGGER.info(ex.getMessage() + "#" +boe.getId() + "#" +boe.getApplyBaName());
                    }
                }
            }
        }
        r = "url:" + url + "$$$" + respStr;
        LOGGER.info( "check end#" +r);
        return r;
    }
}
