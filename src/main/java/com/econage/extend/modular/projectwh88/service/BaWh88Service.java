package com.econage.extend.modular.projectwh88.service;

import com.econage.core.service.ServiceImpl;
import com.econage.core.web.extension.response.BasicDataGridRows;
import com.econage.extend.modular.bms.ba.entity.BmsBaForImportEntity;
import com.econage.extend.modular.bms.ba.service.BmsBaForImportService;
import com.econage.extend.modular.projectwh88.entity.BaWh88Entity;
import com.econage.extend.modular.projectwh88.mapper.BaWh88Mapper;
import com.econage.extend.modular.projectwh88.trivial.Wh88ByDbTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

@Service
@Wh88ByDbTransactional
public class BaWh88Service extends ServiceImpl<BaWh88Mapper, BaWh88Entity> {
    private BmsBaForImportService bmsBaForImportService;
    @Autowired
    protected void setBmsBaService(BmsBaForImportService bmsBaForImportService) {
        this.bmsBaForImportService = bmsBaForImportService;
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean importBmsData(BasicDataGridRows data){
        Collection<BaWh88Entity> list = (Collection<BaWh88Entity>)data.getRows();
        //System.out.println("####list:" + list.size());
        Iterator it = list.iterator();
        while(it.hasNext()){
            BaWh88Entity baEntity = (BaWh88Entity)it.next();
            BmsBaForImportEntity bmsBaEntity = new BmsBaForImportEntity();
            bmsBaEntity.setLastContactTime(baEntity.getLastContactTime());
            bmsBaEntity.setNextContactTime(baEntity.getNextContactTime());
            bmsBaEntity.setOrderSeq(baEntity.getOrderSeq());
            bmsBaEntity.setId(baEntity.getId());
            bmsBaEntity.setBaName(baEntity.getBaName());
            bmsBaEntity.setShortName(baEntity.getShortName());
            bmsBaEntity.setCode(baEntity.getCode());
            bmsBaEntity.setRelationCode(baEntity.getRelationCode());
            bmsBaEntity.setIndustryCode(baEntity.getIndustryCode());
            bmsBaEntity.setFirstStatus(baEntity.getFirstStatus());
            bmsBaEntity.setSecondStatus(baEntity.getSecondStatus());
            bmsBaEntity.setLastActionUser(baEntity.getLastActionUser());
            bmsBaEntity.setValueCode(baEntity.getValueCode());
            bmsBaEntity.setClientContactPerson(baEntity.getClientContactPerson());
            bmsBaEntity.setCurrentSoftware(baEntity.getCurrentSoftware());
            bmsBaEntity.setCountry(baEntity.getCountry());
            bmsBaEntity.setState(baEntity.getState());
            bmsBaEntity.setCity(baEntity.getCity());
            bmsBaEntity.setOwnershipCode(baEntity.getOwnershipCode());
            bmsBaEntity.setPosCode(baEntity.getPosCode());
            bmsBaEntity.setCustomerType(baEntity.getCustomerType());
            bmsBaEntity.setScaleCode(baEntity.getScaleCode());
            bmsBaEntity.setContactsStatus(baEntity.getContactsStatus());
            bmsBaEntity.setSourceCode(baEntity.getSourceCode());
            bmsBaEntity.setRevenue(baEntity.getRevenue());
            bmsBaEntity.setNumOfEmp(baEntity.getNumOfEmp());
            bmsBaEntity.setPhoneNo(baEntity.getPhoneNo());
            bmsBaEntity.setFaxNo(baEntity.getFaxNo());
            bmsBaEntity.setAddress(baEntity.getAddress());
            bmsBaEntity.setWebUrl(baEntity.getWebUrl());
            bmsBaEntity.setEmailAddr(baEntity.getEmailAddr());
            bmsBaEntity.setBankName(baEntity.getBankName());
            bmsBaEntity.setBankAccount(baEntity.getBankAccount());
            bmsBaEntity.setTaxId(baEntity.getTaxId());
            bmsBaEntity.setComments(baEntity.getComments());
            bmsBaEntity.setCreateUser(baEntity.getCreateUser());
            bmsBaEntity.setCreateDate(baEntity.getCreateDate());
            bmsBaEntity.setModDate(baEntity.getModDate());
            bmsBaEntity.setModUser(baEntity.getModUser());
            System.out.println("$$$$$"+bmsBaEntity.getId()+"###"+bmsBaEntity.getBaName());
            bmsBaForImportService.insert(bmsBaEntity);
        }
        return true;
    }


}
