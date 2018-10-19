package com.gtaoeng.test;

import com.gtaoeng.viewbuilder.MXField;

public class DataBeanCls {

    private Long gid;
    @MXField(nameEn = "编号", fieldType = MXField.FeldType.NormalType, sort = 1)
    private String id;
    @MXField(nameEn = "水层", fieldType = MXField.FeldType.NormalType, sort = 2)
    private String layer;
    @MXField(nameEn = "深度", fieldType = MXField.FeldType.NumberType, sort = 3)
    private Integer depth;
    @MXField(nameEn = "水温", fieldType = MXField.FeldType.NumberType, sort = 4)
    private Integer temperature;
    @MXField(nameEn = "水位", fieldType = MXField.FeldType.NumberType, sort = 5)
    private Integer waterLevel;
    @MXField(nameEn = "流量", fieldType = MXField.FeldType.NumberType, sort = 6)
    private Integer velocity=0;
    @MXField(nameEn = "网具", fieldType = MXField.FeldType.SelectType, sort = 7)
    protected String idCatchTool;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Integer waterLevel) {
        this.waterLevel = waterLevel;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public String getIdCatchTool() {
        return idCatchTool;
    }

    public void setIdCatchTool(String idCatchTool) {
        this.idCatchTool = idCatchTool;
    }
}
