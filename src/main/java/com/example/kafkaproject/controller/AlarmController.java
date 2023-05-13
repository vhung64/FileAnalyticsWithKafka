package com.example.kafkaproject.controller;

import com.example.kafkaproject.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AlarmController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping(path = "/push")
    public ResponseEntity<String> publish(@ModelAttribute("file") List<MultipartFile> file) {
        kafkaProducer.sendData(file);
        return ResponseEntity.ok("New Department has been sent to kafka topic");
    }
}
