package com.tech;

import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.tech.**.mapper")
public class OAuthServerApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(OAuthServerApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OAuthServerApplication.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(OAuthServerApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            pritAll(ctx);
        };
    }

    private static void pritAll(ApplicationContext ctx) {
        logger.debug("######################## all IoC managed beans:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.debug(beanName);
        }
        logger.debug("######################## ");
    }
}
