package com.example.kafkaproject.service;

import com.example.kafkaproject.entity.Alarm;
import com.example.kafkaproject.repository.AlarmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private AlarmRepository alarmRepository;

    @KafkaListener(topics = "${topic.name}", groupId = "groupId")
    public void listener(List<Alarm> alarms) {
        System.out.println(alarms.size());
    }
}
