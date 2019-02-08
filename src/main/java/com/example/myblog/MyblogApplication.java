package com.example.myblog;

import com.example.myblog.controller.BlogController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class MyblogApplication {

    private int maxUploadSizeInMb = 10 * 1024 *1024;

    public static void main(String[] args) {
        new File(BlogController.uploadDirectory).mkdir();
        SpringApplication.run(MyblogApplication.class, args);
    }
}

