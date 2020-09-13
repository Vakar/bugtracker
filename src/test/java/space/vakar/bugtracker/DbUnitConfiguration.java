package space.vakar.bugtracker;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbUnitConfiguration {

  @Autowired private DataSource dataSource;

  @Bean
  public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
    DatabaseConfigBean bean = new DatabaseConfigBean();
    bean.setDatatypeFactory(new H2DataTypeFactory());

    DatabaseDataSourceConnectionFactoryBean dbConnectionFactory =
        new DatabaseDataSourceConnectionFactoryBean(dataSource);
    dbConnectionFactory.setDatabaseConfig(bean);
    return dbConnectionFactory;
  }
}
