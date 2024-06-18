package com.xiaomiev.batterywarningsystem.repository;

import com.xiaomiev.batterywarningsystem.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    // 测试正常情况
    @Test
    public void testFindByCarId_WithValidCarId_ShouldReturnVehicle() {
        // 准备
        Vehicle vehicle = new Vehicle();
        vehicle.setCarId(123);
        vehicle.setBatteryType("三元电池");
        vehicle.setTotalMileageKm(1000);
        vehicle.setBatteryHealthPercentage(95);
        vehicleRepository.save(vehicle);

        // 执行
        Vehicle foundVehicle = vehicleRepository.findByCarId(123);

        // 验证
        assertNotNull(foundVehicle);
        assertEquals(123, foundVehicle.getCarId());
    }

    // 测试车辆ID不存在的情况
    @Test
    public void testFindByCarId_WithNonExistentCarId_ShouldReturnNull() {
        // 执行
        Vehicle foundVehicle = vehicleRepository.findByCarId(999);

        // 验证
        assertNull(foundVehicle);
    }

    // 测试边界情况：当carId为最小值时
    @Test
    public void testFindByCarId_WithMinCarId_ShouldReturnNull() {
        // 执行
        Vehicle foundVehicle = vehicleRepository.findByCarId(Integer.MIN_VALUE);

        // 验证
        assertNull(foundVehicle);
    }

    // 测试边界情况：当carId为最大值时
    @Test
    public void testFindByCarId_WithMaxCarId_ShouldReturnVehicle() {
        // 准备
        Vehicle vehicle = new Vehicle();
        vehicle.setCarId(Integer.MAX_VALUE);
        vehicle.setBatteryType("铁锂电池");
        vehicle.setTotalMileageKm(500);
        vehicle.setBatteryHealthPercentage(90);
        vehicleRepository.save(vehicle);

        // 执行
        Vehicle foundVehicle = vehicleRepository.findByCarId(Integer.MAX_VALUE);

        // 验证
        assertNotNull(foundVehicle);
        assertEquals(Integer.MAX_VALUE, foundVehicle.getCarId());
    }
}
