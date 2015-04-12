package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import util.CustomAccessDeniedHandler;
import util.CustomAuthenticationEntryPoint;
import filters.RestAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	/*@Bean
	public AuthenticationManager authenticationManager() throws Exception {
	      return super.authenticationManagerBean();
	}*/
	
	
	
	 @Configuration
	 @EnableWebSecurity
	 @Order(1)
	 public static class RestWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
	     protected void configure(HttpSecurity http) throws Exception {
	         http.antMatcher("/tickets/**")
	         		.authorizeRequests()    
		         	 .antMatchers("/tickets/**").hasAnyAuthority("admin, employee")    
		         	 .antMatchers("/checkuser/**").permitAll()
	             .and()
		         	.httpBasic()
		         	.authenticationEntryPoint(customAuthenticationEntryPoint)
		         .and()
		         	.addFilterBefore(restAuthFilter, BasicAuthenticationFilter.class)
		         	.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
	             .and()
			    	.csrf().disable()
	             	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	             
	     }
	     
	    @Autowired
	 	private RestAuthFilter restAuthFilter; 
	    
	    @Autowired
	 	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint; 
	    
	    @Autowired
	 	private CustomAccessDeniedHandler customAccessDeniedHandler; 
	  }
	 
	 @Configuration
	 @EnableWebSecurity
	 @Order(2)
	 public static class AllWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
	
			http.authorizeRequests()
				.antMatchers("/", "/login", "/timetable", "/route")
					.permitAll()
				.antMatchers("/buyticket")
					.hasAnyAuthority("client, admin, employee")
				.antMatchers("/addrun", "/addtrain", "/addstation", "/alltrains", "/passenger", "/run")
					.hasAnyAuthority("admin, employee")
				.and()
					.formLogin()
						.loginPage("/login")
						.failureUrl("/login?error")
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/")
				.and()
					.logout().logoutSuccessUrl("/").logoutUrl("/j_spring_security_logout")
			    .and()
			    	.csrf().disable();
		}
	 }
	
	
}