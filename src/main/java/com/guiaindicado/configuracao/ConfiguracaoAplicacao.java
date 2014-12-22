package com.guiaindicado.configuracao;

import java.util.Properties;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@ComponentScan("com.guiaindicado")
public class ConfiguracaoAplicacao {

    @Configuration
    @Profile("dev")
    @PropertySource({"classpath:aplicacao-dev.properties"})
    static class Desenvolvimento {
    }

    @Configuration
    @Profile("prd")
    @PropertySource({"classpath:aplicacao-prd.properties"})
    static class Producao {
    }

    @Bean
    public DataSource dataSource() {
        try {
            return (DataSource) new InitialContext().lookup("java:comp/env/jdbc/GuiaIndicadoDS");
        } catch (NamingException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    @Bean
    public Session sessaoEmail() {
        try {
            return (Session) new InitialContext().lookup("java:comp/env/mail/Session");
        } catch (NamingException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    @Bean
    public VelocityEngineFactoryBean velocityEngineFactoryBean() {
        VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean();
        factoryBean.setResourceLoaderPath("classpath:modelos");

        return factoryBean;
    }

    @Bean
    public LocalSessionFactoryBean hibernateSessionFactory() {
        Properties propriedades = new Properties();
        propriedades.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        propriedades.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        propriedades.put("hibernate.show_sql", false);
        propriedades.put("hibernate.use_sql_comments", false);
        propriedades.put("hibernate.format_sql", false);
        propriedades.put("hibernate.default_batch_fetch_size", 10);

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(propriedades);
        sessionFactory.setPackagesToScan("com.guiaindicado.dominio");
            
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager gerenciador = new HibernateTransactionManager();
        gerenciador.setAutodetectDataSource(false);
        gerenciador.setDataSource(dataSource());
        gerenciador.setSessionFactory(hibernateSessionFactory().getObject());

        return gerenciador;
    }
}
