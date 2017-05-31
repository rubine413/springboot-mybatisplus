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

	/*
	 * @Bean public ServletRegistrationBean DruidStatViewServle2() { //
	 * org.springframework
	 * .boot.context.embedded.ServletRegistrationBean提供类的进行注册.
	 * ServletRegistrationBean servletRegistrationBean = new
	 * ServletRegistrationBean(new StatViewServlet(), "/druid2/*");
	 * 
	 * // 添加初始化参数：initParams
	 * 
	 * // 白名单： servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
	 * // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not //
	 * permitted to view this page.
	 * servletRegistrationBean.addInitParameter("deny", "192.168.1.73"); //
	 * 登录查看信息的账号密码. servletRegistrationBean.addInitParameter("loginUsername",
	 * "admin2"); servletRegistrationBean.addInitParameter("loginPassword",
	 * "123456"); // 是否能够重置数据.
	 * servletRegistrationBean.addInitParameter("resetEnable", "false"); return
	 * servletRegistrationBean; }
	 * 
	 * @Bean public FilterRegistrationBean druidStatFilter2() {
	 * 
	 * FilterRegistrationBean filterRegistrationBean = new
	 * FilterRegistrationBean(new WebStatFilter());
	 * 
	 * // 添加过滤规则. filterRegistrationBean.addUrlPatterns("/*");
	 * 
	 * // 添加不需要忽略的格式信息. filterRegistrationBean.addInitParameter("exclusions",
	 * "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*"); return
	 * filterRegistrationBean; }
	 */
}
