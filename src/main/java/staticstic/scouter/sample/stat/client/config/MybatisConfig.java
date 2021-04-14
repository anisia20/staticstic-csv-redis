package staticstic.scouter.sample.stat.client.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <pre>
 * Mybatis 설정 클래스
 * application.yml 의 정보를 로딩하여 connnect 및 map xml 설정 주입
 * </pre>
 */
@Configuration
public class MybatisConfig {
	@Bean(name = "apiDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.api")
    public DataSource apiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="apiSqlSessionFactory")
    public SqlSessionFactory apiSqlSessionFactory(@Qualifier("apiDataSource") DataSource apiDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(apiDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("file://"+System.getProperty("mybaits.mapper")));
        //sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("file://D:/aa/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="apiSqlSessionTemplate")
    public SqlSessionTemplate apiSqlSessionTemplate(SqlSessionFactory apiSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(apiSqlSessionFactory);
    }
	
	@Bean(name="apiSqlSessionBatchTemplate")
	public SqlSessionTemplate apiSqlSessionBatchTemplate(SqlSessionFactory apiSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(apiSqlSessionFactory, ExecutorType.BATCH);
	}
	
}
