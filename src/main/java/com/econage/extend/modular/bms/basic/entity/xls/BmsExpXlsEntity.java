package com.econage.extend.modular.bms.basic.entity.xls;

import java.util.ArrayList;

public class BmsExpXlsEntity {
    private ArrayList<BmsExpXlsHeaderEntity> headerEntities;
    private ArrayList<ArrayList<BmsExpXlsCellEntity>> dataEntities;

    public ArrayList<BmsExpXlsHeaderEntity> getHeaderEntities() {
        return headerEntities;
    }

    public void setHeaderEntities(ArrayList<BmsExpXlsHeaderEntity> headerEntities) {
        this.headerEntities = headerEntities;
    }

    public ArrayList<ArrayList<BmsExpXlsCellEntity>> getDataEntities() {
        return dataEntities;
    }

    public void setDataEntities(ArrayList<ArrayList<BmsExpXlsCellEntity>> dataEntities) {
        this.dataEntities = dataEntities;
    }
}
