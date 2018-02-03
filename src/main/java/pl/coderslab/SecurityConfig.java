package pl.coderslab;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.coderslab.service.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringDataUserDetailsService customUserDetailsService() {
		return new SpringDataUserDetailsService();
	}

	

//	 @Autowired
//	 DataSource dataSource;
//
//	 @Override
//	 public void configure(AuthenticationManagerBuilder auth) throws Exception {
//	 auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
//	  .withUser("karr").password(passwordEncoder().encode("pat"))
//	  .roles( "USER");
//	 }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/").hasAnyRole("USER", "ADMIN")
				.and()
				.formLogin().loginPage("/login")
				.and()
				.exceptionHandling().accessDeniedPage("/403")
				.and()
				.logout().logoutSuccessUrl("/").permitAll();
	}

}
