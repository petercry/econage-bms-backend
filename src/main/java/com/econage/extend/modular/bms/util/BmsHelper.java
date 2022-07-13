package com.econage.extend.modular.bms.util;

import com.aspose.cells.Cell;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.econage.base.organization.org.entity.UserEntity;
import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.organization.org.trivial.meta.UserStatusEnum;
import com.econage.base.organization.org.trivial.wherelogic.UserWhereLogic;
import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.base.support.filemanager.entity.FileHeaderEntity;
import com.econage.base.support.filemanager.manage.FileManager;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellDataTypeEnum;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsCellEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsEntity;
import com.econage.extend.modular.bms.basic.entity.xls.BmsExpXlsHeaderEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BmsHelper {
    public static String getProjectPhaseName(int projectPhaseId) {
        switch(projectPhaseId) {
            case 10: return "项目启动";
            case 20: return "需求调研";
            case 30: return "系统搭建";
            case 40: return "二次开发";
            case 50: return "培训试运行";
            case 60: return "免费维保";
            case 70: return "有偿维保";

            case 100: return "培育";
            case 110: return "立项";
            case 120: return "竞标";
            case 130: return "签定合同";
            case 140: return "首付收到";
            case 150: return "实施";
            case 160: return "项目暂缓";
            case 170: return "主动放弃";
            case 180: return "竞争失利";

            default: return "" + projectPhaseId;
        }
    }
    public static String afTenantId = "dingc1874ad3e752d85f35c2f4657eb6378f";
    public static String afPeterAccountId = "030050590320346524dingc1874ad3e752d85f35c2f4657eb6378f";
    public static String afhwcTenantId = "1433257550215774210";
    public static String afhwcYayaAccountId = "13999999999";
    public static String getAfAccessToken( RestTemplate restTemplate) throws Exception {
        String grant_type = "client_credentials";
        String client_id = "f2db5a2f-ca07-41f9-82b1-8649d77c3e92";
        String client_secret = "";
        String tokenUrl = "https://disvback.flowyun.com/security/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grant_type);
        params.add("client_id", client_id);
        params.add("client_secret", client_secret);

        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity re = restTemplate.exchange(tokenUrl , method , requestEntity , String.class);
        String reBody = (String)re.getBody();
        System.out.println("##reBody:" + reBody);
        JSONObject reObj = new JSONObject(reBody);
        String access_token = reObj.getString("access_token");
        return access_token;
    }
    public static String driverTaskForAf(RestTemplate restTemplate , String accessToken , String account , String reqDataId , String taskDataId , String taskId , FileManager fileManager) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);
        params.add("account", account);
        params.add("tenantId", BmsHelper.afTenantId);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode reqJsonObj = objectMapper.createObjectNode();
        reqJsonObj.put("flowTypeId", 0);
        reqJsonObj.put("reqDataId", reqDataId);
        reqJsonObj.put("taskDataId", taskDataId);
        reqJsonObj.put("taskId", taskId);
        reqJsonObj.put("decisionId", "0");
        reqJsonObj.put("decision", "1");
        reqJsonObj.put("wakeUp", true);

        ObjectNode reqItemJsonObj = objectMapper.createObjectNode();
        reqItemJsonObj.put("sjwcsj" , "2020-06-30");
        reqItemJsonObj.put("jbgcsfk" , "太难了！");

        reqJsonObj.set("itemParams" , reqItemJsonObj);

        FileHeaderEntity fileHeaderEntity = fileManager.selectFileHeaderById("1300643879676608514");
        Assert.notNull(fileHeaderEntity,"no file header,id:1300643879676608514");
        String filePath = fileManager.prepareAndGetFilePath(fileHeaderEntity.getFileBody());
        System.out.println("##filePath:" + filePath);

        //jbgcsfkwd

        HttpEntity<String> reqEntity = new HttpEntity<String>(reqJsonObj.toString(), headers);

        String driveTaskUrl = "https://disvback.flowyun.com/openapi/workflow/driverTask?access_token=" + accessToken + "&account=" + account + "&tenantId=" + BmsHelper.afTenantId;
        String reStr = restTemplate.postForObject(driveTaskUrl, reqEntity, String.class);
        System.out.println("##driveTask reStr:" + reStr);
        return reStr;
    }
    public static String getUserIdByAfUserStr(UserUnionQuery userService , String afUserStr) throws Exception {
        if(StringUtils.isEmpty(afUserStr)) return "";
        //afUserStr形如P|1063505,1063568^产品技术中心--俞哲峰
        String focusUserStr = afUserStr;
        if(focusUserStr.indexOf("^")>0){
            focusUserStr = focusUserStr.substring(focusUserStr.indexOf("^")+1);
            if(focusUserStr.indexOf("-")>0){
                focusUserStr = focusUserStr.substring(focusUserStr.lastIndexOf("-")+1);
            }
        }
        //顺利的话此时focusUserStr已存了姓名
        UserWhereLogic userWhereLogic = new UserWhereLogic();
        userWhereLogic.setStatus(UserStatusEnum.ACTIVE);
        userWhereLogic.setNameFuzzySearch(focusUserStr);
        List<UserEntity> userV = userService.selectListByWhereLogic(userWhereLogic);
        String focusUserId = afUserStr;
        if (userV != null && userV.size() > 0) {
            UserEntity ue = userV.get(0);
            focusUserId = ue.getId();
        }
        return focusUserId;
    }
    public static String getUserNameByAfUserStr(String afUserStr) throws Exception {
        if(StringUtils.isEmpty(afUserStr)) return "";
        //afUserStr形如P|1063505,1063568^产品技术中心--俞哲峰
        String focusUserStr = afUserStr;
        if(focusUserStr.indexOf("^")>0){
            focusUserStr = focusUserStr.substring(focusUserStr.indexOf("^")+1);
            if(focusUserStr.indexOf("-")>0){
                focusUserStr = focusUserStr.substring(focusUserStr.lastIndexOf("-")+1);
            }
        }
        return focusUserStr;
    }
    public static String sendMsgToDingGroupInMd(RestTemplate restTemplate , String chatAccessToken , String groupSecret , String mdTitle , String mdText) throws Exception{
        Long timestamp = System.currentTimeMillis();

        String stringToSign = timestamp + "\n" + groupSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(groupSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
        String url = "https://oapi.dingtalk.com/robot/send?access_token=" + chatAccessToken + "&timestamp=" + timestamp + "&sign=" + sign;

        JSONObject msgObj = new JSONObject();

        msgObj.put("msgtype","markdown");
        JSONObject mdObj = new JSONObject();
        mdObj.put("keyword","ptest1");
        mdObj.put( "title",mdTitle);
        mdObj.put("text",mdText);
        msgObj.put("markdown",mdObj);
        JSONObject atObj = new JSONObject();
        atObj.put("isAtAll",false);
        msgObj.put("at",atObj);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(msgObj.toString() , headers);
        return restTemplate.postForObject(url , entity , String.class);
    }
    public static int getBizOppoSourceFlagByDesc(String desc){
        int sourceFlag = -1;
        if("DISV_SAAS".equals(desc)){
            sourceFlag = 1;
        }else if("BPM".equals(desc)){
            sourceFlag = 2;
        }else if("OPEN_SAAS".equals(desc)){
            sourceFlag = 3;
        }
        return sourceFlag;
    }
    public static void sendMsgToAf(RocketMQTemplate rocketMQTemplate , MsgToAfEntity mtae , String mqTopic , UserUnionQuery userUnionQuery){
        RuntimeUserDetails runtimeUserDetails = SecurityHelper.getRuntimeUserAccount();
        if(runtimeUserDetails!=null && userUnionQuery!=null) {
            String mi = userUnionQuery.selectUserMi(runtimeUserDetails.getUsername());
            if(org.apache.commons.lang3.StringUtils.isEmpty(mi)){
                mi = runtimeUserDetails.getUsername();
            }
            mtae.setSenderName(mi);
        }else{
            mtae.setSenderName("ADMIN");
        }
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        mtae.setSendTime(dtf2.format(LocalDateTime.now()));
        rocketMQTemplate.send(mqTopic, MessageBuilder.withPayload(mtae).build());
    }
    public static ArrayList<String> StringToArrayList(String str, String separator) {
        ArrayList<String> arr = new ArrayList<String>();
        if ((str == null) || (separator == null)) {
            return arr;
        }
        StringTokenizer st = new StringTokenizer(str, separator);
        while (st.hasMoreTokens()) {
            arr.add(st.nextToken());
        }
        return arr;
    }
    public static boolean isDataJournalAboutFin(int checkFuncFlag){
        boolean re = false;
        for(int i=0;i<BmsConst.BMS_DATA_JOURNAL_FOR_FIN_ARRAY.length;i++){
            if(BmsConst.BMS_DATA_JOURNAL_FOR_FIN_ARRAY[i] == checkFuncFlag){
                re = true;break;
            }
        }
        return re;
    }
    public static void setReportXls(BmsExpXlsEntity bmsExpXlsEntity , ServletOutputStream os) throws Exception {
        Workbook workbook = new Workbook();
        Worksheet sheet = workbook.getWorksheets().get(0);
        ArrayList<BmsExpXlsHeaderEntity> headerEntities = bmsExpXlsEntity.getHeaderEntities();
        for(int i=0;i<headerEntities.size();i++){
            Cell h = sheet.getCells().get(0,i);
            h.setValue(headerEntities.get(i).getHeaderColName());
            sheet.getCells().setColumnWidth(i , headerEntities.get(i).getHeaderColWidth());
        }
        if(bmsExpXlsEntity.getDataEntities()!=null && bmsExpXlsEntity.getDataEntities().size() > 0){
            for(int i=0;i<bmsExpXlsEntity.getDataEntities().size();i++){
                ArrayList<BmsExpXlsCellEntity> rowEntity = bmsExpXlsEntity.getDataEntities().get(i);
                for(int j=0;j<rowEntity.size();j++){
                    BmsExpXlsCellEntity bexce = rowEntity.get(j);
                    Cell h = sheet.getCells().get(i+1,j);
                    if(bexce.getDataType() == BmsExpXlsCellDataTypeEnum.STRING) {
                        h.putValue(bexce.getCellValue());
                    }
                }
            }
        }
        sheet.autoFitRows();
        workbook.save(os, SaveFormat.XLSX);
    }
    public static String getBaValueCodeKeyIdForAf(String checkValueCode){
        String re = checkValueCode;
        if(checkValueCode.equals("A")) {
            re = "2715";
        }else if(checkValueCode.equals("B")){
            re = "2716" ;
        }else if(checkValueCode.equals("C")){
            re = "2717" ;
        }else if(checkValueCode.equals("D")){
            re = "2718" ;
        }else if(checkValueCode.equals("S")){
            re = "2719" ;
        }else if(checkValueCode.equals("F")){
            re = "2720" ;
        }else if(checkValueCode.equals("Q")){
            re = "2721" ;
        }else if(checkValueCode.equals("X")){
            re = "2722" ;
        }else if(checkValueCode.equals("已上OA")){
            re = "2788" ;
        }else if(checkValueCode.equals("P")){
            re = "3348" ;
        }else if(checkValueCode.equals("S-红")){
            re = "4963" ;
        }else if(checkValueCode.equals("S-黄")){
            re = "4964" ;
        }else if(checkValueCode.equals("S-黑")){
            re = "5063" ;
        }else if(checkValueCode.equals("T")){
            re = "5223" ;
        }
        return re;
    }
    public static String getBaProductDirectionKeyIdForAf(String checkProductDirection) {
        String re = checkProductDirection;
        if (checkProductDirection.equals("BPM")) {
            re = "1479407550824636417";
        } else if (checkProductDirection.equals("BPA")) {
            re = "1479407581656965122";
        } else if (checkProductDirection.equals("PM")) {
            re = "1479407605811961857";
        } else if (checkProductDirection.equals("ECM")) {
            re = "1479407630344445953";
        } else if (checkProductDirection.equals("BPE")) {
            re = "1505813549328269314";
        } else if (checkProductDirection.equals("BPI")) {
            re = "1505813725723918338";
        } else if (checkProductDirection.equals("其它")) {
            re = "1479407651479543809";
        }
        return re;
    }
    private static final String ENCODING = "UTF-8";
    public static String percentEncode(String value) throws UnsupportedEncodingException{
        return value != null ? URLEncoder.encode(value, ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }
    public static final Charset UTF8 = StandardCharsets.UTF_8;
    public static String sha256Hex(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(s.getBytes(UTF8));
        return Hex.encodeHexString(d).toLowerCase();
    }
    public static byte[] hmac256(byte[] key, String msg) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        mac.init(secretKeySpec);
        return mac.doFinal(msg.getBytes(UTF8));
    }
    public static String encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return java.util.Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
