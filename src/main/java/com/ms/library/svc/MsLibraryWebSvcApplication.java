package com.ms.library.svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan
@EnableTransactionManagement
@EnableJpaRepositories({
    "com.ms.library.svc.repository"
})
public class MsLibraryWebSvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsLibraryWebSvcApplication.class, args);
  }

}
