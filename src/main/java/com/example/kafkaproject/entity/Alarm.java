package com.example.kafkaproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
//@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    // ALARMTYPE
    private Integer alarmType;
    // RAW_CARD
    private String rawCard;
    // NE_TYPE
    private String networkType;
    // NE_ID
    private String networkId;
    // NV_PROBABLE_CAUSE
    private Integer probableCause;
    // NV_PERCEIVED_SEVERITY
    private Integer perceivedSeverity;
    // NE_NAME
    private String networkName;
    // MESSAGE
    private String message;
    // RAW_PORT
    private String rawPost;
    // NV_ALARM_ID
    private String alarmId;
    // NV_EVENT_TIME
    private String eventTime;
    // NV_MANAGED_OBJECT_INSTANCE
    private String managedObjectInstance;
    // NV_IRPAGENT_ID
    private String irpagentId;
    // SEVERITY
    private Integer severity;
    // ALARM_NAME
    private String alarmName;

}