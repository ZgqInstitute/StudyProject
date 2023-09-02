package com.studyFrame.springbootDemo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = {"com.studyFrame.springbootDemo.domain.mapper"})
//@EnableTransactionManagement  使用springboot开发可以不加这个注解
public class ConfigurationInfo {
    /**
     * 使用springboot开发事务可以不用在配置文件中配置事务管理器
     */
//    @Bean
//    public DataSourceTransactionManager transactionManager(DataSource dataSource){
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource);
//        return transactionManager;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource){
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        jdbcTemplate.setDataSource(dataSource);
//        return  jdbcTemplate;
//    }
//
//    @Bean
//    public DataSource dataSource(){
//        MysqlDataSource mysqlDataSource = new MysqlDataSource();
//        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
//        mysqlDataSource.setDatabaseName("com.mysql.jdbc.Driver");
//        mysqlDataSource.setUser("root");
//        mysqlDataSource.setPassword("root");
//        return  mysqlDataSource;
//    }
}
