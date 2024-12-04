package com.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.rbac.entity"})
public class RbacAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacAssignmentApplication.class, args);
    }

}
