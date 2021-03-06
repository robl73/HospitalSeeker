package com.hospitalsearch.config;

import java.net.URLDecoder;
import java.util.Properties;
import java.util.function.Function;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import com.hospitalsearch.validator.ImageValidator;


/**
 * Created by speedfire on 4/28/16.
 */

//here is all configuration for spring parts
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:app.properties")
@ComponentScan(basePackages = "com.hospitalsearch")
@EnableCaching
@Import({MailConfig.class})
public class SpringRootConfig {
    @Resource
    Environment properties;

    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROP_HIBERNATE_ENTITY_PACKAGE = "hibernate.entity.package";
    private static final String PROP_HIBERNATE_IMPORT_FILE = "hibernate.hbm2ddl.import_files";
    private static final String PROP_HIBERNATE_EHCACHE = "hibernate.cache.provider_class";
    private static final String PROP_HIBERNATE_EHCACHE_LEVEL = "hibernate.cache.use_second_level_cache";
    private static final String PROP_HIBERNATE_EHCACHE_REGION_FACTORY = "hibernate.cache.region.factory_class";
    private static final String PROP_HIBERNATE_EHCACHE_SHOWSQL = "hibernate.cache.use_query_cache";
    private static final String PROP_HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER = "hibernate.search.default.directory_provider";
    private static final String PROP_HIBERNATE_SEARCH_INDEX_BASE = "hibernate.search.default.indexBase";

    private static final String PROP_HIBERNATE_EJB_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(properties.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(properties.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(properties.getRequiredProperty(PROP_DATABASE_PASSWORD));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.setPackagesToScan(new String[]{properties.getRequiredProperty(PROP_HIBERNATE_ENTITY_PACKAGE)});

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return transactionManager;
    }

    public Properties hibernateProperties() {
        Properties props = new Properties();
        props.put(PROP_HIBERNATE_DIALECT, properties.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        props.put(PROP_HIBERNATE_SHOW_SQL, properties.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        props.put(PROP_HIBERNATE_EHCACHE, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE));
        props.put(PROP_HIBERNATE_EHCACHE_LEVEL, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE_LEVEL));
        props.put(PROP_HIBERNATE_EHCACHE_SHOWSQL, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE_SHOWSQL));
        props.put(PROP_HIBERNATE_EHCACHE_REGION_FACTORY, properties.getRequiredProperty(PROP_HIBERNATE_EHCACHE_REGION_FACTORY));
        props.put(PROP_HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER, properties.getRequiredProperty(PROP_HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER));
        props.put(PROP_HIBERNATE_SEARCH_INDEX_BASE, properties.getRequiredProperty(PROP_HIBERNATE_SEARCH_INDEX_BASE));

        props.put(PROP_HIBERNATE_HBM2DDL_AUTO, properties.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        props.put(PROP_HIBERNATE_IMPORT_FILE, properties.getRequiredProperty(PROP_HIBERNATE_IMPORT_FILE));

        
        props.put(PROP_HIBERNATE_HBM2DDL_AUTO, properties.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        props.put(PROP_HIBERNATE_IMPORT_FILE, properties.getRequiredProperty(PROP_HIBERNATE_IMPORT_FILE));
        
        return props;
    }

    /**
     * @author Oleksandr Mukonin
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public Function<String, String> currentUrlWithoutParam() {
        return param -> currentUrlWithoutParamWrapped(param);
    }

    public String currentUrlWithoutParamWrapped(String param) {
        try {
            return URLDecoder.decode(
                    ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ImageValidator imageValidator() {
        return new ImageValidator();
    }


}
