package ma.fst.jawal.config;

import ma.fst.jawal.entities.Authority;
import ma.fst.jawal.services.AccountImp;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTTokenHelper jwtTokenHelper;
	private final AccountImp accountImp;

	public JWTAuthenticationFilter(JWTTokenHelper jwtTokenHelper, AccountImp accountImp) {
		this.jwtTokenHelper = jwtTokenHelper;
		this.accountImp = accountImp;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authToken=jwtTokenHelper.getToken(request);
		if(null!=authToken) {
			String userName=jwtTokenHelper.getUsernameFromToken(authToken);
			if(null!=userName) {
				// Ici on cherche l'utilisateur par le username.
				ma.fst.jawal.entities.User user = accountImp.loadUserByUsername(userName);
				// Ici on recupere les roles de l'utilisateur et on créer
				// créer une collection GrantedAuthority qu'utilise spring securité
				Collection<GrantedAuthority> authorities = new ArrayList<>();
				for (Authority r : user.getAuthorities()) {
					authorities.add(new SimpleGrantedAuthority(r.getRole()));
				}

				// On retourne le type de User de spring sécurité.
				UserDetails userDetails = new org.springframework.security.core.userdetails.User(
						user.getLogin(), user.getPwd(), authorities
				);
				if(jwtTokenHelper.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			}

		}
		filterChain.doFilter(request, response);
	}

}
