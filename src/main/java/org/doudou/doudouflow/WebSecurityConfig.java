package org.doudou.doudouflow;

import org.doudou.doudouflow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/error", "/favicon.ico", "/assets/**")
		        .antMatchers("/test*")
		        .antMatchers("/*/**.js","/*/**.css")
				.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/**/api-docs");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().httpStrictTransportSecurity().disable().frameOptions().sameOrigin().and()
		.formLogin().successHandler(new FlowAuthenticationSuccessHandler())
		.failureHandler(new FlowAuthenticationFailureHandler("/login?error"))
		.loginPage("/login").loginProcessingUrl("/login")
		.and()
		.rememberMe()
		//.tokenRepository(persistentTokenRepository()) // 设置数据访问层
		.tokenValiditySeconds(24 * 60 * 60) //记住我的时间(秒)
		//.userDetailsService(userService)
		.and()
		.antMatcher("/**").authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
		.and()
		.exceptionHandling().accessDeniedHandler(new FlowAccessDeniedHandler()).authenticationEntryPoint(new FlowAuthenticationEntryPoint());
	}
	
	/**
	 * 
	 * 密码加密用的bean<br>
	 * <p>
	 * 创建时间：2019年4月16日
	 * </p>
	 * 
	 * @author decai
	 * @since 1.0
	 * @return BCryptPasswordEncoder对象
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
	
	@Autowired
	private UserService userService;
	
//	  @Autowired
//    private DataSource dataSource; // 数据源
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.eraseCredentials(true);
	}
	
	
	/**
     * 持久化token
     * 
     * Security中，默认是使用PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建表persistent_logins，将token持久化到数据库
     */
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource); // 设置数据源
//        //tokenRepository.setCreateTableOnStartup(true); // 启动创建表，创建成功后注释掉
//        return tokenRepository;
//    }
	
}