package com.muzi;

import com.muzi.utils.PathUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@MapperScan("com.muzi.mapper")
@SpringBootApplication
public class ElectronicMallApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("Project Path: " + PathUtils.getClassLoadRootPath());
        SpringApplication.run(ElectronicMallApplication.class, args);
    }

    @Override
    @Order(1)
    public void run(String... args) throws Exception {
        System.out.println("==========================================");
        System.out.println("   电子商城系统后端服务启动成功！！！");
        System.out.println("==========================================");
    }

}
