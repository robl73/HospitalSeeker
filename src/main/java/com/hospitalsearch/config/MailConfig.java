package com.hospitalsearch.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

@Configuration
@PropertySource(value = "classpath:email.properties")
public class MailConfig {

    @Resource
    Environment properties;

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(properties.getProperty("mail.host"));
        mailSender.setPort(Integer.valueOf(properties.getProperty("mail.smtp.port")));
        mailSender.setUsername(properties.getProperty("mail.username"));
        mailSender.setPassword(properties.getProperty("mail.password"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", properties.getProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.smtp.auth",properties.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.smtp.starttls.enable", properties.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.quitwait", properties.getProperty("mail.smtp.quitwait"));
        javaMailProperties.put("mail.debug", "true");
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public VelocityConfigurer velocityConfigurer() {
        VelocityConfigurer configurer = new VelocityConfigurer();
        configurer.setResourceLoaderPath("/WEB-INF/velocity/");
        return configurer;
    }
}
