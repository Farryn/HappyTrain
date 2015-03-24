package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import services.StationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	@Qualifier("stationService")
	StationService stationService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user").password("user").roles("user");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/", "/login", "/timetable", "/route")
				.permitAll()
			.antMatchers("/buyticket")
				.hasAnyRole("user, admin, employee")
			.antMatchers("/addrun", "/addtrain", "/addstation", "/alltrains", "/passenger", "/run")
				.hasAnyRole("admin, employee")
			.and()
				.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/")
			.and()
				.logout().logoutSuccessUrl("/")
		    .and()
		    	.csrf().disable(); 
		
	}
}