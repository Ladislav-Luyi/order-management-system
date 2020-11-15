package com.customer.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderManagementSystemApplication {

//    @Autowired
//    JobLauncher jobLauncher;
//
//    @Autowired
//    Job job;



    public static void main(String[] args) {
        // mvn clean compile assembly:single // for building package with dependencies
        System.out.println(com.customer.ordermanagementsystem.OrderManagementSystemApplication.class);
        SpringApplication.run(OrderManagementSystemApplication.class, args);



    }

//    @Scheduled(cron = "0 * * * * ?")
//    public void perform() throws Exception {
//        JobParameters params = new JobParametersBuilder()
//                .addString("JobID", String.valueOf(System.currentTimeMillis()))
//                .toJobParameters();
//        jobLauncher.run(job, params);
//    }
}
