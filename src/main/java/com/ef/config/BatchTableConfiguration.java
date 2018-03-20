package com.ef.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class BatchTableConfiguration {

    @Value("classpath:org/springframework/batch/core/schema-drop-mysql.sql")
    private Resource schemaDropMySql;

    @Value("classpath:org/springframework/batch/core/schema-mysql.sql")
    private Resource schemaMySql;

    @Value("classpath:schema.sql")
    private Resource schemaAccessLogSql;

    @Autowired
    public DataSource dataSource;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaDropMySql);
        populator.addScript(schemaMySql);
        populator.addScript(schemaAccessLogSql);
        return populator;
    }

}
