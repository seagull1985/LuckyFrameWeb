package com.luckyframe.framework.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

  @Autowired
  private DataSource dataSource;
  
  // 字符编码
  @Value("${flyway.encoding}")
  private String encoding;
  
  // 对执行迁移时基准版本的描述
  @Value("${flyway.baseline-description}")
  private String baselineDescription;

  // 是否自动执行基准迁移
  @Value("${flyway.baseline-on-migrate}")
  private boolean baselineOnMigrate;

  // 指定 baseline 的版本号
  @Value("${flyway.baseline-version}")
  private String baselineVersion;

  // 迁移时是否校验
  @Value("${flyway.validate-on-migrate}")
  private boolean validateOnMigrate;
  
  // 是否允许无序的迁移
  @Value("${flyway.out-of-order}")
  private boolean outOfOrder;
  
  // 当读取元数据表时是否忽略错误的迁移
  @Value("${flyway.ignore-future-migrations}")
  private boolean ignoreFutureMigrations;
  
  // 当初始化好连接时要执行的SQL
  @Value("${flyway.init-sql}")
  private String initSql;
  
  @PostConstruct
  public void migrate() {
    Flyway flyway = Flyway.configure()
    		.dataSource(dataSource)
    		.encoding(encoding)
    		.baselineDescription(baselineDescription)
    		.baselineOnMigrate(baselineOnMigrate)
    		.baselineVersion(baselineVersion)
    		.validateOnMigrate(validateOnMigrate)
    		.outOfOrder(outOfOrder)
    		.ignoreFutureMigrations(ignoreFutureMigrations)
    		.initSql(initSql)
    		.load();
    flyway.migrate();
  }

}
