package com.xiaomiev.batterywarningsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomiev.batterywarningsystem.model.Vehicle;
import com.xiaomiev.batterywarningsystem.model.Warn;
import com.xiaomiev.batterywarningsystem.repository.VehicleRepository;
import com.xiaomiev.batterywarningsystem.repository.WarnRepository;
import com.xiaomiev.batterywarningsystem.controller.WarningController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Objects;

@Service
public class WarningService {

    private final VehicleRepository vehicleRepository;
    private final WarnRepository warnRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public WarningService(VehicleRepository vehicleRepository, WarnRepository warnRepository) {
        this.vehicleRepository = vehicleRepository;
        this.warnRepository = warnRepository;
    }

    public List<WarningController.WarningResult> processWarnings(List<WarningController.WarningRequest> warningRequests) {
        List<WarningController.WarningResult> results = new ArrayList<>();

        for (WarningController.WarningRequest request : warningRequests) {
            int carId = request.getCarId();
            Integer warnId = request.getWarnId(); // 允许warnId为null，非必传
            String signal = request.getSignal();

            // 查询车辆信息
//            Vehicle vehicle = vehicleRepository.findByCarId(carId);
            Vehicle vehicle = getVehicleByCarId(carId);
            if (vehicle == null) {
                throw new RuntimeException("Car with ID " + carId + " not found");
            }

            if (warnId == null) {
                // 如果warnId为空，则分别处理warnId为1和2的情况
                results.addAll(processWarningForWarnId(carId, signal, vehicle, 1));
                results.addAll(processWarningForWarnId(carId, signal, vehicle, 2));
            } else {
                // 根据warnId处理单个警告请求
                results.addAll(processWarningForWarnId(carId, signal, vehicle, warnId));
            }
        }

        return results;
    }

    @Cacheable(value = "vehicles", key = "#carId")
    public Vehicle getVehicleByCarId(int carId) {
        return vehicleRepository.findByCarId(carId);
    }

    private List<WarningController.WarningResult> processWarningForWarnId(int carId, String signal, Vehicle vehicle, int warnId) {
        List<WarningController.WarningResult> results = new ArrayList<>();

        // 根据电池类型-batteryType和预警ID-warnId查询预警信息
//        Warn warn = warnRepository.findByBatteryTypeAndWarnId(vehicle.getBatteryType(), warnId);
        Warn warn = getWarnByBatteryTypeAndWarnId(vehicle.getBatteryType(), warnId);
        if (warn == null) {
            throw new RuntimeException("Warning with ID " + warnId + " not found for battery type " + vehicle.getBatteryType());
        }

        // 根据(信号-signal)和(预警规则-warnRule)计算(预警等级-warnLevel)
        int warnLevel = evaluateWarningLevel(signal, warn.getWarnRule(), warnId);
        results.add(new WarningController.WarningResult(carId, vehicle.getBatteryType(), warn.getWarnName(), warnLevel));

        return results;
    }

    @Cacheable(value = "warns", key = "#batteryType + '_' + #warnId")
    public Warn getWarnByBatteryTypeAndWarnId(String batteryType, int warnId) {
        return warnRepository.findByBatteryTypeAndWarnId(batteryType, warnId);
    }


    // 根据(信号-signal)、(预警规则-warnRule)、(规则编号-warnId)计算(预警等级-warnLevel)
    private int evaluateWarningLevel(String signal, String warnRule, int warnId) {
        try {
            Map<String, Double> signalMap = objectMapper.readValue(signal, Map.class);
            double mx = signalMap.getOrDefault("Mx", 0.0);
            double mi = signalMap.getOrDefault("Mi", 0.0);
            double ix = signalMap.getOrDefault("Ix", 0.0);
            double ii = signalMap.getOrDefault("Ii", 0.0);
            double difference = 0.0; // 电压差或电流差

            //如果规则编号为1(代表电压差报警)，则计算Mx-Mi
            if(warnId == 1){
                difference = mx - mi;  // 计算Mx-Mi
            }
            //如果规则编号为2(代表电流差报警)，则计算Ix-Ii
            else if(warnId == 2){
                difference = ix - ii;  // 计算Ix-Ii
            };

            // 将warnRule按换行符分割，得到规则数组
            String[] rules = warnRule.split("\n");
            for (String rule : rules) {
                // 替换(Mx-Mi)或(Ix-Ii)为 电压差或电流差-differece
                if (rule.contains("(Mx-Mi)")) {
                    rule = rule.replace("(Mx-Mi)", String.valueOf(difference));
                } else if (rule.contains("(Ix-Ii)")) {
                    rule = rule.replace("(Ix-Ii)", String.valueOf(difference));
                }

                // 检查是否包含",报警等级："部分
                if (rule.contains(",报警等级：")) {
                    // 按",报警等级："分割规则，得到条件和报警等级两部分
                    String[] parts = rule.split(",报警等级：");
                    String condition = parts[0].trim();  // 获取条件部分并去除首尾空白字符
                    int warnLevel = Integer.parseInt(parts[1].trim());  // 将报警等级部分转换为整数

                    // 调用evaluateCondition方法评估条件，如果满足条件，则返回对应的报警等级, condition be like "0.5<=0.8<1.0"
                    if (evaluateCondition(condition)) {
                        return warnLevel;
                    }
                } else {
                    // 处理不包含",报警等级："的规则，假设为不报警条件
                    String condition = rule.trim();
                    if (evaluateCondition(condition)) {
                        return -1; // 不报警
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing warning rule", e);
        }

        return -1; // 默认不报警
    }

    private boolean evaluateCondition(String condition) {
        condition = condition.trim(); // 去除字符串首尾的空白字符
        return evaluateExpression(condition);
    }

    private boolean evaluateExpression(String condition) {
        // 处理 "<="
        if (condition.contains("<=")) {
            String[] parts = condition.split("<=");
            for (int i = 0; i < parts.length - 1; i++) {
                double leftValue = Double.parseDouble(parts[i].trim());
                double rightValue;
                if (parts[i + 1].contains("<")) {
                    rightValue = Double.parseDouble(parts[i + 1].substring(0, parts[i + 1].indexOf("<")).trim());
                } else {
                    rightValue = Double.parseDouble(parts[i + 1].trim());
                }
                if (!(leftValue <= rightValue)) {
                    return false;
                }
            }
            return true;
        }

        // 处理 "<"
        if (condition.contains("<")) {
            String[] parts = condition.split("<");
            for (int i = 0; i < parts.length - 1; i++) {
                double leftValue = Double.parseDouble(parts[i].trim());
                double rightValue;
                if (parts[i + 1].contains("<=")) {
                    rightValue = Double.parseDouble(parts[i + 1].substring(0, parts[i + 1].indexOf("<=")).trim());
                } else {
                    rightValue = Double.parseDouble(parts[i + 1].trim());
                }
                if (!(leftValue < rightValue)) {
                    return false;
                }
            }
            return true;
        }

        return false; // 如果条件字符串不符合预期格式，返回false
    }

}
