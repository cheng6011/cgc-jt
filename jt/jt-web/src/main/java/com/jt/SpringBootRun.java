package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/**
 * 如果程序启动报数据源相关的错误,则springBoot程序启动时会根据pom.xml文件中的数据源的jar包加载相关配置
 * 但是jt-web项目只引用jar包,但是不需要连接数据源,所有yml配置中没有该配置,导致url报错
 * 
 * 1.在yml文件中加载数据源
 * @author tedu
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class SpringBootRun {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}
}
