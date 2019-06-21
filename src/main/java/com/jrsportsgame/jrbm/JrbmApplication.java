package com.jrsportsgame.jrbm;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jrsportsgame.jrbm.mapper")
public class JrbmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JrbmApplication.class, args);
    }

}
