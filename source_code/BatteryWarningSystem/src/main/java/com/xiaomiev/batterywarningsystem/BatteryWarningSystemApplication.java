package com.xiaomiev.batterywarningsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @file BatteryWarningSystemApplication.java
 * @brief 小米汽车电池预警系统
 * @date 2024-06-18
 * @license Proprietary License
 * @author techotaku@zs
 *
 * 本应用程序用于管理和监控小米汽车的电池预警系统。BMS系统智能化管理及维护各个电池单元，
 * 防止电池出现过充电和过放电，延长电池的使用寿命，并监控电池状态。系统支持规则配置和信号预警，
 * 旨在解决电池各种突发情况并提升用户体验。
 *
 * 功能包括：
 * 1. 车辆信息管理：记录和存储车辆识别码（vid）、电池类型、总里程、电池健康状态等信息。
 * 2. 预警规则管理：配置和存储不同类型电池的预警规则。
 * 3. 信号预警：根据上传的信号数据，按照配置的预警规则计算预警等级，并返回预警结果。
 *
 * 技术栈：
 * - Java
 * - Spring Boot 2.0+
 * - MySQL 5.7+
 * - Redis
 * - HTTP/HTTPS
 *
 * 技术点：
 * 1. 动态解析规则，而不是将规则硬编码到代码中。
 * 2. 在单元测试中，确保信号响应时间级别和预警成功率不低于90%。
 * 3. 实时规则接口性能测试和优化，确保P99响应时间在1秒以内。
 * 4. 设计合理的数据存储和查询方案，以处理每天数百万甚至数千万条信号，考虑系统性能影响。
 *
 */

@SpringBootApplication
@EnableCaching
public class BatteryWarningSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatteryWarningSystemApplication.class, args);
    }
}
