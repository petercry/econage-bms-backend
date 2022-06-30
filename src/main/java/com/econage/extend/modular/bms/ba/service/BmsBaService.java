package com.econage.extend.modular.bms.ba.service;

import com.econage.base.organization.org.service.helper.UserUnionQuery;
import com.econage.base.security.SecurityHelper;
import com.econage.base.security.userdetials.entity.RuntimeUserDetails;
import com.econage.base.support.kv.manage.BasicKvUnionQuery;
import com.econage.core.basic.util.IdWorker;
import com.econage.core.service.ServiceImpl;
import com.econage.extend.modular.bms.ba.component.event.entity.BmsBaEventEntity;
import com.econage.extend.modular.bms.ba.entity.BmsBaAssociationEntity;
import com.econage.extend.modular.bms.ba.entity.BmsBaEntity;
import com.econage.extend.modular.bms.ba.mapper.BmsBaMapper;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaAssociationWhereLogic;
import com.econage.extend.modular.bms.ba.trivial.wherelogic.BaWhereLogic;
import com.econage.extend.modular.bms.basic.entity.BmsTagInfoEntity;
import com.econage.extend.modular.bms.basic.service.BmsTagInfoService;
import com.econage.extend.modular.bms.util.BmsConst;
import com.econage.extend.modular.bms.util.BmsHelper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BmsBaService extends ServiceImpl<BmsBaMapper, BmsBaEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BmsBaService.class);
    private BasicKvUnionQuery kvService;
    private UserUnionQuery userUnionQuery;
    private BmsTagInfoService bmsTagInfoService;
    private BmsBaAssociationService baAssociationService;
    private final String[] defaultSortForBa = {"last_contact_time_","order_seq_"};
    private final String[] defaultOrderForBa = {"desc","desc"};
    @Autowired
    protected void setService(BmsTagInfoService bmsTagInfoService , UserUnionQuery userUnionQuery , BasicKvUnionQuery kvService , BmsBaAssociationService baAssociationService) {
        this.bmsTagInfoService = bmsTagInfoService;
        this.userUnionQuery = userUnionQuery;
        this.kvService = kvService;
        this.baAssociationService = baAssociationService;
    }

    private final static List<String> validUpdateCols = ImmutableList.of("valid","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeBaStatus(String baId , boolean status){
        if(StringUtils.isEmpty(baId)){
            return false;
        }
        BmsBaEntity bmsBaEntity = new BmsBaEntity();
        bmsBaEntity.setId(baId);
        bmsBaEntity.setValid(status);
        return updatePartialColumnById(bmsBaEntity,validUpdateCols);
    }

    private final static List<String> updateColsForAfCallin = ImmutableList.of("valueCode","modDate","modUser");
    @Transactional(rollbackFor = Throwable.class)
    public boolean changeBaInfoForAfCallin(String baId , String valueCode , String productDirection , String modUser){ //AF的Callin流程中回写若干客户属性
        if(StringUtils.isEmpty(baId)){
            return false;
        }
        valueCode = BmsHelper.getBaValueCodeKeyIdForAf(valueCode);
        productDirection = BmsHelper.getBaProductDirectionKeyIdForAf(productDirection);
        BmsBaEntity bmsBaEntity = new BmsBaEntity();
        bmsBaEntity.setId(baId);
        bmsBaEntity.setValueCode(valueCode);
        bmsBaEntity.setModUser(modUser);
        BmsBaAssociationEntity baAssociationEntity = new BmsBaAssociationEntity();
        baAssociationEntity.setBaId(baId);
        baAssociationEntity.setModular("product_direction");
        baAssociationEntity.setModularInnerId(productDirection);
        baAssociationEntity.setCreateUser(modUser);
        baAssociationEntity.setModUser(modUser);
        ArrayList<BmsBaAssociationEntity> baAssociationEntities = new ArrayList<>();
        baAssociationEntities.add(baAssociationEntity);
        bmsBaEntity.setProductDirectionOptions(baAssociationEntities);
        return updatePartialColumnById(bmsBaEntity,updateColsForAfCallin);
    }

    @Override
    protected void doRefreshSingleEntityBeforeUpdate(BmsBaEntity entity) {
        if(entity.getStateAreaArray()!=null&&entity.getStateAreaArray().length>0) entity.setStateArea(Arrays.toString(entity.getStateAreaArray()));
    }
    @Override
    protected void doRefreshSingleEntityBeforeInsert(BmsBaEntity entity){
        entity.setValid(true);
        if(entity.getOrderSeq() == null||entity.getOrderSeq()==0) entity.setOrderSeq(IdWorker.getId());
        if(entity.getStateAreaArray()!=null&&entity.getStateAreaArray().length>0) entity.setStateArea(Arrays.toString(entity.getStateAreaArray()));
        RuntimeUserDetails runtimeUserDetails = SecurityHelper.getRuntimeUserAccount();
        entity.setLastActionUser(userUnionQuery.selectUserMi(runtimeUserDetails.getUserId()));
        entity.setLastContactTime(LocalDate.now());
    }

    public void updateBaEntityWhenUpdateEvent(String baId , BmsBaEventEntity bmsBaEventEntity){
        BmsBaEntity oriBaEntity = selectById(baId);
        BmsBaEntity opBaEntity = new BmsBaEntity();
        opBaEntity.setId(baId);
        //如果事件时间晚于客户信息中的最近联系时间，则用事件时间作为新的最近联系时间
        if(oriBaEntity.getLastContactTime()==null||bmsBaEventEntity.getActionDate().toLocalDate().isAfter(oriBaEntity.getLastContactTime())){
            opBaEntity.setLastContactTime(bmsBaEventEntity.getActionDate().toLocalDate());
            opBaEntity.setLastActionUser(bmsBaEventEntity.getActionUser());
        }
//        //如果事件中的下次联系时间早于客户信息中的下次联系时间，并且事件中的下次联系时间晚于当前时间，则用事件中的下次联系时间作为新的下次联系时间
//        if(bmsBaEventEntity.getNextContactDate()!=null&&(oriBaEntity.getNextContactTime() == null || (bmsBaEventEntity.getNextContactDate().isBefore(oriBaEntity.getNextContactTime()) && bmsBaEventEntity.getNextContactDate().isAfter(LocalDate.now())))){
//            opBaEntity.setNextContactTime(bmsBaEventEntity.getNextContactDate());
//        }
        if(bmsBaEventEntity.getNextContactDate()!=null){
            opBaEntity.setNextContactTime(bmsBaEventEntity.getNextContactDate());
        }
        updateById(opBaEntity);
    }
    @Override
    protected void doFillFkRelationship(Collection<BmsBaEntity> entities){
        for(BmsBaEntity entity : entities){
            if(StringUtils.isEmpty(entity.getStateAreaDesc())){
                entity.setStateAreaDesc( StringUtils.defaultString(entity.getState() , "")  + StringUtils.defaultString(entity.getCity() , ""));
            }
            if(!StringUtils.isEmpty(entity.getFirstStatus())){
                entity.setFirstStatusDesc(kvService.getKvTextById(entity.getFirstStatus()));
            }
            if(!StringUtils.isEmpty(entity.getValueCode())){
                entity.setValueCodeDesc(kvService.getKvTextById(entity.getValueCode()));
            }
            List<BmsTagInfoEntity> selectedTags = bmsTagInfoService.getSelectedTag("ba" , entity.getId());
            if(selectedTags!=null&&selectedTags.size()>0) {
                entity.setBaTag(selectedTags);
                String baTagStr = "";
                for (BmsTagInfoEntity btie : selectedTags) {
                    if (baTagStr.equals("")) {
                        baTagStr = kvService.getKvTextById(btie.getTagKeyId());
                    } else {
                        baTagStr += "、" + kvService.getKvTextById(btie.getTagKeyId());
                    }
                }
                entity.setBaTagStr(baTagStr);
            }

            BaAssociationWhereLogic baAssociationWhereLogic = new BaAssociationWhereLogic();
            baAssociationWhereLogic.setModular(BmsConst.BMS_BA_PRODUCT_DIRECTION_MODULAR);
            baAssociationWhereLogic.setBaId(entity.getId());
            List<BmsBaAssociationEntity> selectedProductDirectionOptions = baAssociationService.selectListByWhereLogic(baAssociationWhereLogic);
            if(selectedProductDirectionOptions!=null&&selectedProductDirectionOptions.size()>0){
                entity.setProductDirectionOptions(selectedProductDirectionOptions);
                String productDirectionStr = "";
                for(BmsBaAssociationEntity bbae:selectedProductDirectionOptions){
                    if(productDirectionStr.equals("")){
                        productDirectionStr = kvService.getKvTextById(bbae.getModularInnerId());
                    }else{
                        productDirectionStr += "、" + kvService.getKvTextById(bbae.getModularInnerId());
                    }
                }
                entity.setProductDirectionStr(productDirectionStr);
            }
        }
    }
    public Boolean checkWarningCustomer(BmsBaEntity bbe){
        //inTime
            /*
                @WhereLogicField(wherePart = "(\n" +
            "           ( value_code_ = '2715' and datediff(now(),last_contact_time_) > 7 ) or\n" +  //A类超过7天
            "           ( value_code_ = '2716' and datediff(now(), last_contact_time_) > 30) or\n" + //B类超过30天
            "           ( value_code_ = '2717' and datediff(now(), last_contact_time_) > 180) or\n" + //C类超过半年
            "           datediff(now(), next_contact_time_) >= 0\n" +  //到达“下次联系时间”
            "        )")
             */
        return ("2715".equals(bbe.getValueCode()) && bbe.getLastContactTime() != null && (LocalDate.now().toEpochDay() - bbe.getLastContactTime().toEpochDay()) > 7) ||
                ("2716".equals(bbe.getValueCode()) && bbe.getLastContactTime() != null && (LocalDate.now().toEpochDay() - bbe.getLastContactTime().toEpochDay()) > 30) ||
                ("2717".equals(bbe.getValueCode()) && bbe.getLastContactTime() != null && (LocalDate.now().toEpochDay() - bbe.getLastContactTime().toEpochDay()) > 180) ||
                (bbe.getNextContactTime() != null && (LocalDate.now().toEpochDay() - bbe.getNextContactTime().toEpochDay()) >= 0);
    }
    @Override
    protected void doRefreshSingleEntityAfterInsert(BmsBaEntity entity) {
        refreshBaTags(entity);
        refreshBaAssociationInfo(entity , new String[]{BmsConst.BMS_BA_PRODUCT_DIRECTION_MODULAR});
    }
    @Override
    protected void doRefreshSingleEntityAfterUpdate(BmsBaEntity entity) {
        refreshBaTags(entity);
        refreshBaAssociationInfo(entity , new String[]{BmsConst.BMS_BA_PRODUCT_DIRECTION_MODULAR});
    }
    private void refreshBaTags(BmsBaEntity entity){
        if(entity==null){
            return;
        }
        if(entity.getBaTag()!=null){
            Set<String> tags = entity.getBaTag().stream()
                    .map(BmsTagInfoEntity::getTagKeyId).collect(Collectors.toSet());
            bmsTagInfoService.refresh("ba" , entity.getId(),tags);
        }
    }
    private void refreshBaAssociationInfo(BmsBaEntity entity , String[] modularArray){
        if(entity==null){
            return;
        }
        for(String nodeModular : modularArray){
            if(nodeModular.equals(BmsConst.BMS_BA_PRODUCT_DIRECTION_MODULAR) && entity.getProductDirectionOptions()!=null){
                Set<String> nodeModularInnerIds = entity.getProductDirectionOptions().stream().map(BmsBaAssociationEntity::getModularInnerId).collect(Collectors.toSet());
                baAssociationService.refresh(nodeModular , entity.getId() , nodeModularInnerIds);
            }
        }
    }
    public BaWhereLogic setSearchWhereLogic(BaWhereLogic oriWhereLogic , Integer sl_baViewType , Boolean doWarningViewFlag){
        BaWhereLogic newWhereLogic = oriWhereLogic;
        //sl_baViewType:  1: "所有客户"，即管理员获取全部客户信息；2: "我的客户"，走个人权限
        //doWarningViewFlag:  false:不做任何处理；true: 仅获取亟待联系的客户
        if(sl_baViewType == 1){
        }else {
            RuntimeUserDetails runtimeUserDetails = SecurityHelper.getRuntimeUserAccount();
            Collection<String> s = new ArrayList<>();
            s.addAll(runtimeUserDetails.getUnionRefExpress());
            //s.add("1172011667186290690");//模拟沈艳
            // whereLogic.setBaSelectAuthExpress(userAccountDetails.getUnionRefExpress());
            newWhereLogic.setBaSelectAuthExpress(s);
            if(sl_baViewType == 2){  //我负责的
                newWhereLogic.setAuthPart("owner");
            }else if(sl_baViewType == 3){ //  我协助的（工作人员）
                newWhereLogic.setAuthPart("collabrator");
            }else{   //我查看的（访客）
                newWhereLogic.setAuthPart("guest");
            }
        }
        if(newWhereLogic.getBaTags()!=null && newWhereLogic.getBaTags().size()>0){
            newWhereLogic.setTagKeyCollecExpress(newWhereLogic.getBaTags());
        }
        if(doWarningViewFlag){
            newWhereLogic.setIsWarnignView(true);
        }
        if(ArrayUtils.isEmpty(newWhereLogic.getSort())){
            newWhereLogic.setSort(defaultSortForBa);
        }else{
            if(newWhereLogic.getSort().length == 1){
                String sortStr = newWhereLogic.getSort()[0];
                if(sortStr.indexOf(",")>0){
                    newWhereLogic.setSort(sortStr.split(","));
                }
            }
        }
        if(ArrayUtils.isEmpty(newWhereLogic.getOrder())){
            newWhereLogic.setOrder(defaultOrderForBa);
        }else{
            if(newWhereLogic.getOrder().length == 1){
                String orderStr = newWhereLogic.getOrder()[0];
                if(orderStr.indexOf(",")>0){
                    newWhereLogic.setOrder(orderStr.split(","));
                }
            }
        }
        newWhereLogic.setStatusTarget(true);
        if(newWhereLogic.getSearchOwnerEmptyFlag()!=null&&!newWhereLogic.getSearchOwnerEmptyFlag()) newWhereLogic.setSearchOwnerEmptyFlag(null);
        if(newWhereLogic.getSearchCollaboratorEmptyFlag()!=null&&!newWhereLogic.getSearchCollaboratorEmptyFlag()) newWhereLogic.setSearchCollaboratorEmptyFlag(null);
        if(newWhereLogic.getSearchGuestEmptyFlag()!=null&&!newWhereLogic.getSearchGuestEmptyFlag()) newWhereLogic.setSearchGuestEmptyFlag(null);
        if(!StringUtils.isEmpty(newWhereLogic.getSearchOwnerUserStr()) && newWhereLogic.getSearchOwnerUserStr().indexOf(".")>0){
            String userStr = newWhereLogic.getSearchOwnerUserStr();
            userStr = userStr.substring(userStr.indexOf(".") + 1);
            newWhereLogic.setSearchOwnerUserStr(userStr);
        }
        if(!StringUtils.isEmpty(newWhereLogic.getSearchCollaboratorUserStr()) && newWhereLogic.getSearchCollaboratorUserStr().indexOf(".")>0){
            String userStr = newWhereLogic.getSearchCollaboratorUserStr();
            userStr = userStr.substring(userStr.indexOf(".") + 1);
            newWhereLogic.setSearchCollaboratorUserStr(userStr);
        }
        if(!StringUtils.isEmpty(newWhereLogic.getSearchGuestUserStr()) && newWhereLogic.getSearchGuestUserStr().indexOf(".")>0){
            String userStr = newWhereLogic.getSearchGuestUserStr();
            userStr = userStr.substring(userStr.indexOf(".") + 1);
            newWhereLogic.setSearchGuestUserStr(userStr);
        }
        return newWhereLogic;
    }


//    public SalesContractEntity analysisFile(InputStream demoIn) throws IOException {
//        // Open document
//        //Document pdfDocument = new Document("/Users/peter/Downloads/demo.pdf");
//        //Document pdfDocument = new Document() new Document("C:\\Users\\peter\\Downloads\\demo.pdf");
//        Document pdfDocument = new Document(demoIn);
//        // Create TextAbsorber object to extract text
//        TextAbsorber textAbsorber = new TextAbsorber();
//        // Accept the absorber for all the pages
//        pdfDocument.getPages().accept(textAbsorber);
//        // Get the extracted text
//        String pdfText = textAbsorber.getText();
//
//        SalesContractEntity sce = new SalesContractEntity();
//        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//        if(pdfText.indexOf("工程编号：")>0){
//            sce.setProjectCode((pdfText.substring(pdfText.indexOf("工程编号：")+5,pdfText.indexOf("\n",pdfText.indexOf("工程编号：")))).trim());
//        }
//        if(pdfText.indexOf("工程名称：")>0){
//            sce.setProjectName((pdfText.substring(pdfText.indexOf("工程名称：")+5,pdfText.indexOf("\n",pdfText.indexOf("工程名称：")))).trim());
//        }
//        if(pdfText.indexOf("发包人（甲方）：")>0){
//            sce.setPartyA((pdfText.substring(pdfText.indexOf("发包人（甲方）：")+8,pdfText.indexOf("\n",pdfText.indexOf("发包人（甲方）：")))).trim());
//        }
//        if(pdfText.indexOf("承包人（乙方）：")>0){
//            sce.setPartyB((pdfText.substring(pdfText.indexOf("承包人（乙方）：")+8,pdfText.indexOf("\n",pdfText.indexOf("承包人（乙方）：")))).trim());
//        }
//        if(pdfText.indexOf("工程地点：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("工程地点：")+5,pdfText.indexOf("\n",pdfText.indexOf("工程地点：")))).trim();
//            if(tmpContent.lastIndexOf("。")==tmpContent.length()-1) tmpContent = tmpContent.substring(0,tmpContent.length()-1);
//            sce.setProjectLocation(tmpContent);
//        }
//        if(pdfText.indexOf("工程内容：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("工程内容：")+5,pdfText.indexOf("。",pdfText.indexOf("工程内容：")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            sce.setProjectContent(tmpContent);
//        }
//        if(pdfText.indexOf("承包方式：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("承包方式：")+5,pdfText.indexOf("\n",pdfText.indexOf("承包方式：")))).trim();
//            if(tmpContent.lastIndexOf("。")==tmpContent.length()-1) tmpContent = tmpContent.substring(0,tmpContent.length()-1);
//            sce.setContractMode(tmpContent);
//        }
//        if(pdfText.indexOf("工期：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("工期：")+3,pdfText.indexOf("\n",pdfText.indexOf("工期：")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            if(tmpContent.lastIndexOf("。")==tmpContent.length()-1) tmpContent = tmpContent.substring(0,tmpContent.length()-1);
//            sce.setTimeLimit(tmpContent);
//        }
//        if(pdfText.indexOf("质保期为")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("质保期为")+4,pdfText.indexOf("\n",pdfText.indexOf("质保期为")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            if(tmpContent.lastIndexOf("。")==tmpContent.length()-1) tmpContent = tmpContent.substring(0,tmpContent.length()-1);
//            sce.setGuaranteePeriod(tmpContent);
//        }
//        if(pdfText.indexOf("质量标准：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("质量标准：")+5,pdfText.indexOf("。",pdfText.indexOf("质量标准：")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            sce.setQualityStandard(tmpContent);
//        }
//        if(pdfText.indexOf("价款方式按照以下")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("价款方式按照以下")+8,pdfText.indexOf("。",pdfText.indexOf("价款方式按照以下")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            sce.setContractPayMode(tmpContent);
//        }
//        if(pdfText.indexOf("第十条")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("第十条")+8,pdfText.indexOf("第六章",pdfText.indexOf("第十条")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            if(tmpContent.lastIndexOf("。")==tmpContent.length()-1) tmpContent = tmpContent.substring(0,tmpContent.length()-1);
//            sce.setBreachLiability(tmpContent);
//        }
//        if(pdfText.indexOf("合同价款：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("合同价款：")+7,pdfText.indexOf("大写",pdfText.indexOf("合同价款：")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            sce.setContractAmount(tmpContent);
//        }
//        if(pdfText.indexOf("大写：")>0){
//            String tmpContent = (pdfText.substring(pdfText.indexOf("大写：")+3,pdfText.indexOf("\n",pdfText.indexOf("大写：")))).trim();
//            Matcher m = p.matcher(tmpContent);
//            tmpContent = m.replaceAll("");
//            sce.setContractAmountUpper(tmpContent);
//        }
//
//        return sce;
////        // Create a writer and open the file
////        java.io.FileWriter writer = new java.io.FileWriter(new java.io.File("/Users/peter/Downloads/Extracted_text.txt"));
////        writer.write(extractedText);
////// Write a line of text to the file tw.WriteLine(extractedText);
////// Close the stream
////        writer.close();
//    }
}
