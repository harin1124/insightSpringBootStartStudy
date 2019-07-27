package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties") //해당 파일을 사용할 수 있도록 설정 파일 위치 지정
@EnableTransactionManagement //스프링에서 제공하는 어노테이션 기반 트랜잭션 활성화
public class DatabaseConfiguration{
	
	@Autowired
	private ApplicationContext applicationContext;
	
	// 위에 적힌 프로퍼티스에서 설정한 데이터베이스 관련 정보를 사용하도록 지정
	// prefix가 hikari로 설정되어 있기 때문에, hikari로 시작하는 설정을 이용해 히카리CP 설정 파일 만듦
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig(){
		return new HikariConfig();
	}
	
	// 앞에서 만든 히카리cp의 설정 파일을 이용해 데이터베이스와 연결하는 데이터 소스 생성
	@Bean
	public DataSource dataSource()throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 스프링-마이바티스에서는 SqlSessionFactory를 생성하기 위해 SqlSessionFactoryBean을 사용하며
		// 스프링이 아닌 마이바티스 단독으로 사용할 경우에는 SqlSessionFactoryBuilder 사용
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 앞에서 만든 데이터 소스 설정
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml*")); // 마이바티스 매퍼 파일 위치 설정
		sqlSessionFactoryBean.setConfiguration(mybatisConfig()); // 마이바티스 설정 추가
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration") // 설정을 가져온다.
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration(); // 가져온 설정을 자바 클래스로 만들어 반환
	}
	
	@Bean // 트랜잭션 매니저 등록
	public PlatformTransactionManager transactionManager()throws Exception{
		return new DataSourceTransactionManager(dataSource());
	}
	
}