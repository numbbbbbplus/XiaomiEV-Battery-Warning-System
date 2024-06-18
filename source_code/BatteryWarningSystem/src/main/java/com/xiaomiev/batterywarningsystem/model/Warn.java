package com.xiaomiev.batterywarningsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "warn") // 对应数据库中的warn表
public class Warn {
    @Id
    private int id;

    @Column(name = "warn_id", nullable = false)
    private int warnId;

    @Column(name = "warn_name", nullable = false)
    private String warnName;

    @Column(name = "battery_type", nullable = false)
    private String batteryType;

    @Column(name = "warn_rule", nullable = false)
    private String warnRule;

    // 构造函数、getter和setter
    public Warn() {}

    // getter和setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarnId() {
        return warnId;
    }

    public void setWarnId(int warnId) {
        this.warnId = warnId;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public String getWarnRule() {
        return warnRule;
    }

    public void setWarnRule(String warnRule) {
        this.warnRule = warnRule;
    }
}
