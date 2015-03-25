package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		/*auth.inMemoryAuthentication()
				.withUser("user").password("user").roles("user")
				.and()
				.withUser("admin").password("admin").roles("admin");*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/", "/login", "/timetable", "/route")
				.permitAll()
			.antMatchers("/buyticket")
				.hasAnyAuthority("user, admin, employee")
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
				.logout().logoutSuccessUrl("/")
		    .and()
		    	.csrf().disable();
		    	
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}