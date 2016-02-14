package br.alecrim.alecrim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
    
@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.alecrim.alecrim")
@EnableAspectJAutoProxy(proxyTargetClass = false)
@Import(value = {WebConfig.class}) 
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}