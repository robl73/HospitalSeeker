package com.hospitalsearch.config.web;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * Created by george on 10/13/2016.
 */

@SpringBootApplication
@PropertySource(value = "classpath:springBoot.properties")
public class SpringBoot {

    @Resource
    Environment properties;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory(Integer.parseInt(properties.getProperty("server-port")));
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) con -> {
            Http11NioProtocol proto = (Http11NioProtocol) con.getProtocolHandler();
            proto.setSSLEnabled(true);
            con.setScheme("https");
            con.setSecure(true);
            proto.setKeystoreFile(properties.getProperty("server-key-store-file"));
            proto.setKeystorePass(properties.getProperty("server-key-store-pass"));
        });
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot.class, args);
    }

}
