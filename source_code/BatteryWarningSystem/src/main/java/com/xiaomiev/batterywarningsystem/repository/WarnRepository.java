package com.xiaomiev.batterywarningsystem.repository;

import com.xiaomiev.batterywarningsystem.model.Warn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarnRepository extends JpaRepository<Warn, Integer> {
    Warn findByBatteryTypeAndWarnId(String batteryType, int warnId);
}
