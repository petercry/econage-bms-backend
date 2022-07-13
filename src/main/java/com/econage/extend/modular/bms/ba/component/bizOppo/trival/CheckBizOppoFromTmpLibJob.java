package com.econage.extend.modular.bms.ba.component.bizOppo.trival;

import com.econage.core.cron.CronJob;
import com.econage.extend.modular.bms.ba.component.bizOppo.service.BizOppoService;
import com.econage.extend.modular.bms.config.BmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CheckBizOppoFromTmpLibJob implements CronJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBizOppoFromTmpLibJob.class);
    private BizOppoService bizOppoService;
    private RestTemplate restTemplate;
    private BmsProperties bmsProperties;

    @Autowired
    void setWired(
            RestTemplate restTemplate ,
            BizOppoService bizOppoService,
            BmsProperties bmsProperties
    ) {
        this.restTemplate = restTemplate;
        this.bizOppoService = bizOppoService;
        this.bmsProperties = bmsProperties;
    }

    @Override
    public String cronExpress() {
        return bmsProperties.getCheckBizOppoFromTmpLibJobCron();
    }

    @Override
    public void execute() throws Throwable {
            LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%start check biz oppo from tmp lib###" + LocalDateTime.now());
            String r = "";
//            String url = "http://192.168.4.22:30003/zentao/openapi/trial/list?dateTime=";
            //获取6分钟前的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strTime = sdf.format((new Date()).getTime() - 12 * 60 * 1000);

            r = bizOppoService.addBizOppoBatch(strTime);
//            //strTime = "2019-09-09 09:00:00";
//            url += strTime;
//            Resource resp = restTemplate.getForObject(url, Resource.class);
//            String respStr = IOUtils.toString(resp.getInputStream() , StandardCharsets.UTF_8);
//            ObjectMapper objectMapper = new ObjectMapper();
//            TmpLibGeneralEntity tmpLibGeneralEntity = objectMapper.readValue(respStr, TmpLibGeneralEntity.class);
//            if(tmpLibGeneralEntity.getStatus()!=null && tmpLibGeneralEntity.getStatus() == 200){
//                if(tmpLibGeneralEntity.getData().getClass() == ArrayList.class){
//                    ArrayList<TmpLibBizOppoEntity> boList = objectMapper.convertValue(tmpLibGeneralEntity.getData(), new TypeReference< ArrayList<TmpLibBizOppoEntity>>() {});
//                    for(TmpLibBizOppoEntity tlboe : boList){
//                        BizOppoEntity boe = new BizOppoEntity();
//                        boe.setId(tlboe.getSourcePt() + "_" + tlboe.getId());
//                        boe.setSourceId(tlboe.getSourceId());
//                        boe.setSourceFlag(BmsHelper.getBizOppoSourceFlagByDesc(tlboe.getSourcePt()));
//                        boe.setApplyPhone(tlboe.getPhone());
//                        boe.setApplyContactorName(tlboe.getContractName());
//                        boe.setApplyBaName(tlboe.getTenantName());
//                        String dateStr = tlboe.getSourceCreateDate();
//                        if(dateStr.length() == 16) dateStr += ":00";
//                        boe.setSourceApplyDate(Instant.ofEpochMilli( sdf.parse(dateStr).getTime() ).atZone( ZoneId.systemDefault() ).toLocalDateTime());
//                        try {
//                            bizOppoService.insert(boe);
//                        }catch (Exception ex){
//                            LOGGER.info(ex.getMessage() + "#" +boe.getId() + "#" +boe.getApplyBaName());
//                        }
//                    }
//                }
//            }
//            r = "url:" + url + "$$$" + respStr;
            LOGGER.info( "check end#" +r);
    }
}