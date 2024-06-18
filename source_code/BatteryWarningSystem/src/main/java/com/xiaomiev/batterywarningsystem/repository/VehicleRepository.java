package com.xiaomiev.batterywarningsystem.repository;

import com.xiaomiev.batterywarningsystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findByCarId(int carId);
}
