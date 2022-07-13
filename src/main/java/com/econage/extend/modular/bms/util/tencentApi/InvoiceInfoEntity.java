package com.econage.extend.modular.bms.util.tencentApi;

public class InvoiceInfoEntity {
    //销售方识别号
    private String sellerId;
    //销售方名称
    private String sellerName;
    //购买方识别号
    private String buyerId;
    //购买方名称
    private String buyerName;
    //发票代码
    private String invoiceCode;
    //发票名称
    private String invoiceName;
    //发票号码
    private String invoiceNo;
    //开票日期
    private String makeOutDate;
    //机器编号
    private String machineCode;
    //校验码
    private String proofCode;
    //货物或应税劳务、服务名称 （商品名称）
    private String goodsName;
    //数量
    private String goodsNum;
    //单价（不含税）
    private String singlePriceWithoutTax;
    //金额（不含税）
    private String priceAmountWithoutTax;
    //税率
    private String taxRate;
    //税额
    private String taxAmount;
    //合计金额(不含税)
    private String totalAmountWithoutTax;
    //合计税额
    private String totalTaxAmount;
    //价税合计(大写)
    private String realTotalAmountUpper;
    //小写金额 (价税)
    private String realTotalAmount;
    //销售方地址、电话
    private String sellerAddressPhone;
    //销售方开户行及账号
    private String sellerBankInfo;
    //开票人
    private String makeOutPerson;
    //发票消费类型
    private String invoiceConsumeType;
    //发票类型
    private String invoiceType;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getMakeOutDate() {
        return makeOutDate;
    }

    public void setMakeOutDate(String makeOutDate) {
        this.makeOutDate = makeOutDate;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getProofCode() {
        return proofCode;
    }

    public void setProofCode(String proofCode) {
        this.proofCode = proofCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getSinglePriceWithoutTax() {
        return singlePriceWithoutTax;
    }

    public void setSinglePriceWithoutTax(String singlePriceWithoutTax) {
        this.singlePriceWithoutTax = singlePriceWithoutTax;
    }

    public String getPriceAmountWithoutTax() {
        return priceAmountWithoutTax;
    }

    public void setPriceAmountWithoutTax(String priceAmountWithoutTax) {
        this.priceAmountWithoutTax = priceAmountWithoutTax;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalAmountWithoutTax() {
        return totalAmountWithoutTax;
    }

    public void setTotalAmountWithoutTax(String totalAmountWithoutTax) {
        this.totalAmountWithoutTax = totalAmountWithoutTax;
    }

    public String getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(String totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getRealTotalAmountUpper() {
        return realTotalAmountUpper;
    }

    public void setRealTotalAmountUpper(String realTotalAmountUpper) {
        this.realTotalAmountUpper = realTotalAmountUpper;
    }

    public String getRealTotalAmount() {
        return realTotalAmount;
    }

    public void setRealTotalAmount(String realTotalAmount) {
        this.realTotalAmount = realTotalAmount;
    }

    public String getSellerAddressPhone() {
        return sellerAddressPhone;
    }

    public void setSellerAddressPhone(String sellerAddressPhone) {
        this.sellerAddressPhone = sellerAddressPhone;
    }

    public String getSellerBankInfo() {
        return sellerBankInfo;
    }

    public void setSellerBankInfo(String sellerBankInfo) {
        this.sellerBankInfo = sellerBankInfo;
    }

    public String getMakeOutPerson() {
        return makeOutPerson;
    }

    public void setMakeOutPerson(String makeOutPerson) {
        this.makeOutPerson = makeOutPerson;
    }

    public String getInvoiceConsumeType() {
        return invoiceConsumeType;
    }

    public void setInvoiceConsumeType(String invoiceConsumeType) {
        this.invoiceConsumeType = invoiceConsumeType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
}
