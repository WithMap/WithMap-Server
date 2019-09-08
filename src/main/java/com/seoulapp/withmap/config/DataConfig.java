package com.seoulapp.withmap.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
//@MapperScan(basePackages = { "com.aceproject.demo.dao" })
public class DataConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		// mybatis 설정
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setCacheEnabled(false);
		configuration.setCallSettersOnNulls(true);

		String mapperLoc = "classpath*:/mappers/**/*.xml";
		Resource[] locations = new PathMatchingResourcePatternResolver().getResources(mapperLoc);

		// lazyConnection 설정
		// dataSource = new LazyConnectionDataSourceProxy(dataSource);

		// sqlSessionFactory 생성
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setConfiguration(configuration);
		factory.setTypeAliasesPackage("com.seoulapp.withmap");
		factory.setMapperLocations(locations);

		// type handler 등록
		List<TypeHandler<?>> typeHandlers = new ArrayList<>();
		typeHandlers.add(new JodaDateTimeTypeHandler());

		factory.setTypeHandlers(typeHandlers.toArray(new TypeHandler<?>[typeHandlers.size()]));

		return factory.getObject();
	}

}