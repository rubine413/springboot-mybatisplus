package cc.rubine;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 程序启动类
 * @author Rubine
 *
 */
@SpringBootApplication(scanBasePackages = { "cc.rubine" })
@ServletComponentScan
public class BootApplication {

	static Logger logger = LoggerFactory.getLogger(BootApplication.class);

	public static void main(String[] args) throws Exception {
		// 从命令行启动，使用内置tomcat
		new SpringApplicationBuilder(BootApplication.class).run(args);
	}

	// 注册一个component，当ContextRefreshedEvent被触发时调用，此事件web应用初始化完毕后会触发
	@Component
	public static class BeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent> {

		@Override
		public void onApplicationEvent(ContextRefreshedEvent arg0) {
			// TODO Auto-generated method stub
			
			logger.debug("=========cmsp started.");
		}

	}	

	// 注册ServletContextListener，目的是为了deregister JDBC驱动
	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextDestroyed(ServletContextEvent sce) {

			}

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				// 启动后运行，可以做一些初始化的工作
				logger.info("ServletContext initialized!!!!");

			}
		};
	}

	/**
	 * 允许跨域请求
	 * 
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("*"); // 1
		corsConfig.addAllowedHeader("*"); // 2
		corsConfig.addAllowedMethod("*"); // 3
		source.registerCorsConfiguration("/**", corsConfig); // 4
		return new CorsFilter(source);
	}
	
}
