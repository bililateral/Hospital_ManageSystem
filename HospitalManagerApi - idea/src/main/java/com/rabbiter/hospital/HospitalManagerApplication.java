package com.rabbiter.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rabbiter.hospital.mapper")  // 扫描MyBatis的Mapper接口
public class HospitalManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalManagerApplication.class, args);
    }
}
