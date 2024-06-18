package com.xiaomiev.batterywarningsystem.controller;

import com.xiaomiev.batterywarningsystem.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WarningController {

    private final WarningService warningService;

    @Autowired
    public WarningController(WarningService warningService) {
        this.warningService = warningService;
    }

    @PostMapping("/warn")
    public ResponseEntity<WarningResponse> processWarnings(@RequestBody List<WarningRequest> warningRequests) {
        try {
            List<WarningResult> results = warningService.processWarnings(warningRequests);
            return ResponseEntity.ok(new WarningResponse(200, "ok", results));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WarningResponse(500, e.getMessage(), null));
        }
    }

    // 内部类，用于接收请求体中的数据
    public static class WarningRequest {
        private int carId;
        private Integer warnId; // 将warnId的类型修改为Integer, 使其可以为空(非必传)
        private String signal;

        // getter和setter方法
        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public Integer getWarnId() {
            return warnId;
        }

        public void setWarnId(Integer warnId) {
            this.warnId = warnId;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(String signal) {
            this.signal = signal;
        }
    }

    // 返回格式的响应体
    public static class WarningResponse {
        private int status; // 修改字段名称为 "status"
        private String msg; // 修改字段名称为 "msg"
        private List<WarningResult> data;

        public WarningResponse(int status, String msg, List<WarningResult> data) {
            this.status = status;
            this.msg = msg;
            this.data = data;
        }

        // getter和setter方法
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<WarningResult> getData() {
            return data;
        }

        public void setData(List<WarningResult> data) {
            this.data = data;
        }
    }

    // 内部类，用于封装处理结果
    public static class WarningResult {
        private int 车架编号; // 修改字段名称为 "车架编号"
        private String 电池类型; // 修改字段名称为 "电池类型"
        private String warnName;
        private int warnLevel;

        public WarningResult(int 车架编号, String 电池类型, String warnName, int warnLevel) {
            this.车架编号 = 车架编号;
            this.电池类型 = 电池类型;
            this.warnName = warnName;
            this.warnLevel = warnLevel;
        }

        // getter和setter方法
        public int get车架编号() {
            return 车架编号;
        }

        public void set车架编号(int 车架编号) {
            this.车架编号 = 车架编号;
        }

        public String get电池类型() {
            return 电池类型;
        }

        public void set电池类型(String 电池类型) {
            this.电池类型 = 电池类型;
        }

        public String getWarnName() {
            return warnName;
        }

        public void setWarnName(String warnName) {
            this.warnName = warnName;
        }

        public int getWarnLevel() {
            return warnLevel;
        }

        public void setWarnLevel(int warnLevel) {
            this.warnLevel = warnLevel;
        }
    }
}
