package ma.fst.jawal.config;

import ma.fst.jawal.services.Account;
import ma.fst.jawal.services.AccountImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTTokenHelper jWTTokenHelper;
	private AccountImp accountImp;
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
//				.authenticationEntryPoint(authenticationEntryPoint).and()
//				.authorizeRequests((request) -> request.antMatchers("/test/**", "/api/auth/login", "/ws/**").permitAll()
//					//	.antMatchers("/api/responsable/**").hasAuthority("RESPONSABLE")
//				.antMatchers(HttpMethod.OPTIONS).permitAll();
////				.addFilterBefore(new JWTAuthenticationFilter(userService, jWTTokenHelper), UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable().cors().and().headers().frameOptions().disable();
	}
}