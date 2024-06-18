package com.xiaomiev.batterywarningsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;

import java.util.UUID;

@Entity
@Table(name = "vehicles", uniqueConstraints = { @UniqueConstraint(columnNames = {"car_id", "battery_type"})}) //对应数据库中的vehicles表
public class Vehicle {
    @Id
    @Column(length = 16)
    private String vid;

    @Column(name = "car_id", nullable = false)
    private int carId;

    @Column(name = "battery_type", nullable = false)
    private String batteryType;

    @Column(name = "total_mileage_km", nullable = false)
    private int totalMileageKm;

    @Column(name = "battery_health_percentage", nullable = false)
    private int batteryHealthPercentage;

    // 构造函数、getter 和 setter
    public Vehicle() {
        this.vid = generateVid();
    }

    public String generateVid() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    // getter和setter方法
    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public int getCarId() {
        return carId;
    }


    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public int getTotalMileageKm() {
        return totalMileageKm;
    }

    public void setTotalMileageKm(int totalMileageKm) {
        this.totalMileageKm = totalMileageKm;
    }

    public int getBatteryHealthPercentage() {
        return batteryHealthPercentage;
    }

    public void setBatteryHealthPercentage(int batteryHealthPercentage) {
        this.batteryHealthPercentage = batteryHealthPercentage;
    }
}
