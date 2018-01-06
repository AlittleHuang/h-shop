package config.spring;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shengchuang.shiro.UserRealm;

@Configuration
public class SpringConfig {

	protected final Log logger = LogFactory.getLog(getClass());

	static {
		/*
		 * if (System.getProperty("os.name").toLowerCase().startsWith("win"))
		 * LogManager.getRootLogger().setLevel(Level.DEBUG);
		 */
	}

	//@Bean
	public EhCacheManager getEhCacheManager() {
		EhCacheManager manager = new EhCacheManager();
		manager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return manager;
	}

	@Bean
	public static UserRealm getUserRealm() {
		return new UserRealm();
	}

	public static DefaultWebSecurityManager securityManager;

	@Bean("securityManager")
	public static DefaultWebSecurityManager getSecurityManager() {
		if (securityManager != null)
			return securityManager;
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//securityManager.setCacheManager(getEhCacheManager());
		securityManager.setRealm(getUserRealm());
		return securityManager;
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

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(getSecurityManager());
		bean.setLoginUrl("/login.html");
		bean.setSuccessUrl("/success.html");
		bean.setUnauthorizedUrl("/unauthorized.html");
		Map<String, String> map = new LinkedHashMap<>();
		map.put("/login*", "anon");
		map.put("/index.jsp", "anon");
		map.put("/logout", "logout");
		map.put("/admin*/**", "roles[admin]");
		map.put("/user/**", "authc");
		bean.setFilterChainDefinitionMap(map);
		return bean;
	}

	@Bean
	public static AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(getSecurityManager());
		return advisor;
	}
}
