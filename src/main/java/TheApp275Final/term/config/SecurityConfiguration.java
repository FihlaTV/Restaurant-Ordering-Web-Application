package TheApp275Final.term.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(1)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	MyAccessDeniedHandler myAccessDeniedHandler;
	
	@Autowired
	LoginFailureHandler loginFailureHandler;
	
	@Autowired
	LoginSucessHandler loginSuccessHandler;

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
		.usersByUsernameQuery("select username,password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
    
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/resources/css/**").permitAll()
		.antMatchers("/resources/js/**").permitAll()
		.antMatchers("/resources/images/**").permitAll()
		.antMatchers("/images/**").permitAll()
		.antMatchers("/user/**").access("hasRole('USER')")//("/", "/user/**")
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		.and()
			.formLogin()
				.loginPage("/login")
				.failureHandler(loginFailureHandler)
				.successHandler(loginSuccessHandler)
				.usernameParameter("username").passwordParameter("password")
		.and()
			.csrf()
		.and()
			.exceptionHandling()
			.accessDeniedHandler(myAccessDeniedHandler)
			//.accessDeniedPage("/Access_Denied")
		.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.invalidSessionUrl("/login?");
	}
	
}