package foo.bar.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Value("${user.password:passw0rd}")
	String password;

	@Autowired
	JwtSecurityFilter jwtSecurityFilter;

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password(password).authorities("ADMIN")
				.build();

		UserDetails user1 = User.withDefaultPasswordEncoder().username("user1").password(password).authorities("USER")
				.build();

		return new InMemoryUserDetailsManager(admin, user1);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeHttpRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated();

		http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
