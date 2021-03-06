package ma.fst.jawal.config;

import ma.fst.jawal.entities.Authority;
import ma.fst.jawal.entities.User;
import ma.fst.jawal.services.accounts.AccountImp;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JWTTokenHelper jWTTokenHelper;
	private final AccountImp accountImp;
	private final AuthenticationEntryPoint authenticationEntryPoint;

	public SecurityConfiguration(JWTTokenHelper jWTTokenHelper, AccountImp accountImp, AuthenticationEntryPoint authenticationEntryPoint) {
		this.jWTTokenHelper = jWTTokenHelper;
		this.accountImp = accountImp;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(username -> {
			// Ici on cherche l'utilisateur par le username.
			User user = accountImp.loadUserByUsername(username);

			// Ici on recupere les roles de l'utilisateur et on cr??er
			// cr??er une collection GrantedAuthority qu'utilise spring securit??
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			for (Authority r : user.getAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(r.getRole()));
			}

			// On retourne le type de User de spring s??curit??.
			return new org.springframework.security.core.userdetails.User(
					user.getLogin(), user.getPwd(), authorities
			);
		}).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Tous les requ??tes.
//		http.authorizeRequests().anyRequest().permitAll();

		// D??sactivation du statefull et activ?? le stateless.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)


		// On va authoriser un requ??te particuli??re.
//		http.authorizeRequests().antMatchers("/api/auth/login").permitAll();
		.and().authorizeRequests((request) -> request.antMatchers("/api/auth/login").permitAll()

		// On va authoriser une seule requ??te apr??s authentification.
//		http.authorizeRequests().anyRequest().authenticated();
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated());

		// Impl??mentation des JWT:
		// Ajouter un filtre.
//		http.addFilter(new JWTAuthenticationFilter(authenticationManagerBean(), new JWTTokenHelper()));
		http.addFilterBefore(new JWTAuthenticationFilter(jWTTokenHelper, accountImp), UsernamePasswordAuthenticationFilter.class);

		// Si on utilise un statefull, on active le csrf pour garder
		// s??curis?? les donn??es des formulaires.
		http.csrf().disable().cors();

		// D??sactivation des blocages des frames, en statefull,
		// pour le page g??rer par spring s??curit??.
		http.headers().frameOptions().disable();
	}
}