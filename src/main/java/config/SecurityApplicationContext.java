package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityApplicationContext extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_URL = "/login.html";

	private static final String HOME_URL = "/home.html";

	private static final String MAIN_URL = "/main.html";

	private static final String ACCESSDENIED_URL = "/error.html";

	private static final Logger LOGGER = LogManager.getLogger(SecurityApplicationContext.class);

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/public/**/**", "/css/**/**", "/js/**/**", "/icons/**/**", "/images/**/**", "/index.html",
				"/error.html", LOGIN_URL, ACCESSDENIED_URL, MAIN_URL);
		web.debug(true);
	}

	public void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable();
		http.anonymous().disable();
		http.csrf().disable();

		// Log out configuration
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl(LOGIN_URL);

		// session Management configuration
		// http.sessionManagement().invalidSessionUrl(LOGIN_URL).maximumSessions(1);
		http.sessionManagement().invalidSessionUrl(LOGIN_URL);

		// Login configuration
		http.formLogin().loginProcessingUrl("/login").loginPage(LOGIN_URL).failureUrl(LOGIN_URL).defaultSuccessUrl(HOME_URL, true);

		// Exception control configuration
		http.exceptionHandling().accessDeniedPage(ACCESSDENIED_URL);

		http.authorizeRequests().anyRequest().authenticated().antMatchers("/forms/**").hasRole("ADMIN");
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}