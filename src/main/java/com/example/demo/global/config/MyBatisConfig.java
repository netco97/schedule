package com.example.demo.global.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {
        "com.example.demo.schedule.mapper", // ScheduleMapperをスキャン
        "com.example.demo.user.mapper"      // UserMapperをスキャン
})
public class MyBatisConfig {

    // SqlSessionFactoryをBeanとして登録
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

        // データソースを設定
        factory.setDataSource(dataSource);

        // Mapper XMLの場所を指定
        factory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml")
        );

        // TypeAliasパッケージを複数指定（;で区切る）
        factory.setTypeAliasesPackage(
                "com.example.demo.schedule.domain;com.example.demo.user.domain"
        );

        return factory.getObject();
    }
}
