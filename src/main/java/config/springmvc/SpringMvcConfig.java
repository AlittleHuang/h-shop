package config.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.shengchuang.web.view.Json;

import config.spring.SpringConfig;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 配置SpringMVC
 */
@Configuration
public class SpringMvcConfig {

	protected final Log logger = LogFactory.getLog(getClass());

	public static final String TEMPLATE_LOADER_PATH = "/views";

	@Bean
	public FreeMarkerViewResolver getFreeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setOrder(0);
		resolver.setSuffix(".html");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setCache(true);
		resolver.setRequestContextAttribute("request");
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setExposeSpringMacroHelpers(true);
		return resolver;
	}

	@Bean
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer() {

			@Override
			public void afterPropertiesSet() throws IOException, TemplateException {
				super.afterPropertiesSet();
				this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
			}
		};
		configurer.setTemplateLoaderPath(TEMPLATE_LOADER_PATH);
		configurer.setDefaultEncoding("UTF-8");
		Properties settings = new Properties();
		settings.setProperty("locale", "zh_CN");
		settings.setProperty("locale", "zh_CN");
		settings.setProperty("template_exception_handler", TemplateExceptionHandlerImpl.class.getName());
		configurer.setFreemarkerSettings(settings);
		return configurer;
	}

	@Bean
	public MappingJackson2JsonView getMappingJackson2JsonView() {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		return jsonView;
	}

	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		getLifecycleBeanPostProcessor();
		return new DefaultAdvisorAutoProxyCreator();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		DefaultWebSecurityManager securityManager = SpringConfig.getSecurityManager();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	/**
	 * controller异常处理
	 * 
	 * @return
	 */
	@Bean
	public HandlerExceptionResolver geExceptionResolver() {
		return (request, response, handler, ex) -> {
			logger.error(ex.getLocalizedMessage());
			StringWriter info = new StringWriter();
			ex.printStackTrace(new PrintWriter(info));
			logger.info(info);
			Json json = new Json().failedMsg("服务器错误")
					.add("error", ex.getLocalizedMessage())
					.add("info", info.toString());
			if (request.getRequestURI().endsWith(".html"))
				return new ModelAndView("err").addAllObjects(json);
			return new ModelAndView(json);
		};
	}

	/**
	 * freemarker异常处理
	 * 
	 * @author HuangChengwei
	 *
	 */
	public static class TemplateExceptionHandlerImpl implements TemplateExceptionHandler {
		@Override
		public void handleTemplateException(TemplateException te, Environment env, Writer out)
				throws TemplateException
		{
			try {
				// out.write("[error]");
			} catch (Exception e) {
				// 没有异常
			}
		}
	}

}
