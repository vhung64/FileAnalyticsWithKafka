package com.example.kafkaproject.service;

import com.example.kafkaproject.entity.Alarm;
import com.example.kafkaproject.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Value("${topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, List<Alarm>> kafkaTemplate;

    public void sendData(List<MultipartFile> files) {
        files.forEach(file -> {
            validateFile(file);
            try {
                // Get the file contents as a byte array
                List<Alarm> alarms = new ArrayList<>();
                String content = new String(file.getBytes());
                List<String> stringList = List.of(content.split("\n"));
                stringList.forEach(s -> {
                    Alarm alarm = toAlarm(s);
                    alarms.add(alarm);
                    if (alarms.size() == 1000) {
                        kafkaTemplate.send(topicName, alarms);
                        alarms.removeAll(alarms);
                    }
                });
                if (alarms.size() > 0) {
                    kafkaTemplate.send(topicName, alarms);
                }
                LOGGER.info("upload_success");
            } catch (Exception e) {
                // Handle the exception
                LOGGER.info("upload_error");
            }
        });
    }

    public static Alarm toAlarm(String s) {
        List<String> list = List.of(s.split(";"));
        if (list.size() != 15) throw new RuntimeException("File data lỗi ở đoạn : " + s);
        Alarm alarm = Alarm.builder()
                .alarmType(Integer.valueOf(list.get(0)))
                .rawCard(list.get(1))
                .networkType(list.get(2))
                .networkId(list.get(3))
                .probableCause(Integer.valueOf(list.get(4)))
                .perceivedSeverity(Integer.valueOf(list.get(5)))
                .networkName(list.get(6))
                .message(list.get(7))
                .rawPost(list.get(8))
                .alarmId(list.get(9))
                .eventTime(list.get(10))
                .managedObjectInstance(list.get(11))
                .irpagentId(list.get(12))
                .severity(Integer.valueOf(list.get(13)))
                .alarmName(list.get(14))
                .build();
        return alarm;
    }

    private void validateFile(MultipartFile file) {
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new BadRequestException("file không không được để trống");
        }
        String fileExtension = getFileExtensiton(fileName);
        if (!checkFileExtension(fileExtension)) {
            throw new BadRequestException("file không đúng định dạng");
        }
        // Kiểm tra dung lượng file (<= 100MB)
        double fileSize = (double) (file.getSize() / 1_048_576);
        if (fileSize > 100) {
            throw new BadRequestException("file không được vượt quá 100MB");
        }
    }

    private String getFileExtensiton(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOf + 1);
    }

    private boolean checkFileExtension(String fileExtension) {
        List<String> extensions = new ArrayList<>(List.of("txt"));
        return extensions.contains(fileExtension.toLowerCase());
    }

}
