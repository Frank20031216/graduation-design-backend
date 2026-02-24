package com.ruoyi.framework.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Flowable 数据源配置
 * 解决若依多数据源环境下 Flowable 无法建表的问题
 */
@Configuration
public class FlowableDataSourceConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    private final DataSource masterDataSource;

    public FlowableDataSourceConfig(@Qualifier("masterDataSource") DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    @Override
    public void configure(SpringProcessEngineConfiguration configuration) {
        // 设置数据源为主库
        configuration.setDataSource(masterDataSource);

        // 设置数据库更新策略：true=自动建表/更新表
        configuration.setDatabaseSchemaUpdate("true");

        // 开启异步执行器
        configuration.setAsyncExecutorActivate(true);

        // 可选：设置数据库类型（通常会自动检测）
        // configuration.setDatabaseType("mysql");
    }
}