package com.example.kafkaproject.repository;

import com.example.kafkaproject.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
}