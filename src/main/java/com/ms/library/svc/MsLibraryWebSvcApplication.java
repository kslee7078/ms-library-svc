package com.ms.library.svc;

import jakarta.servlet.http.HttpServletRequest;
import java.net.http.HttpHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestHeader;

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
