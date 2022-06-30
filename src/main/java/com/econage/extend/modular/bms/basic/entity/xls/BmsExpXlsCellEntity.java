package com.econage.extend.modular.bms.basic.entity.xls;

public class BmsExpXlsCellEntity {
    private String cellValue;
    private Enum<BmsExpXlsCellDataTypeEnum> dataType;

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public Enum<BmsExpXlsCellDataTypeEnum> getDataType() {
        return dataType;
    }

    public void setDataType(Enum<BmsExpXlsCellDataTypeEnum> dataType) {
        this.dataType = dataType;
    }
}
