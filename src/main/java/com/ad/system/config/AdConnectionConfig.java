package com.ad.system.config;

import com.ad.system.utls.AdConstant;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ad.system.repository", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "platformTransactionManager")
public class AdConnectionConfig {
    private final AdSystemDBConfig dbConfig;

    public AdConnectionConfig(AdSystemDBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    PlatformTransactionManager platformTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dbConfig.dataSource());
        factoryBean.setPackagesToScan("com.ad.system.entity");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put(AdConstant.DIALECT_KEY, AdConstant.DIALECT_VALUE);
        properties.put("hibernate.dialect", "com.ad.system.config.DialectConfig");
        properties.put(AdConstant.SHOW_SQL_KEY, AdConstant.SHOW_SQL_VALUE);
        properties.put(AdConstant.FORMAT_SQL_KEY, AdConstant.FORMAT_SQL_VALUE);
        properties.put("spring.jpa.hibernate.ddl-auto", "update");
        return properties;
    }
}
