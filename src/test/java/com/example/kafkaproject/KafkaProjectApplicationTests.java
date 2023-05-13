package com.example.kafkaproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class KafkaProjectApplicationTests {

	@Test
	void contextLoads() {
		String s = "2;Test_DKMB_07_4G;BTS3900;Test_DKMB_07_4G;317;3;Test_DKMB_07_4G;;534536211;534536211;28/08/2007 06:55:15;DC=www.huawei.com , SubNetwork=1 , ManagedElement=99903;DC=www.huawei.com , SubNetwork=1 , ManagementNode=1 , IRPAgent=1;3;Configuration File Damaged";
		List<String> list = List.of(s.split(";"));
		list.forEach(s1 -> System.out.println(s1));
	}

}
