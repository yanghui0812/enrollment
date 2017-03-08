package config;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
public class SecurityApplicationContext extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_URL = "/login.html";

	private static final String HOME_URL = "/home.html";
	
	private static final String MAIN_URL = "/main.html";

	private static final String ACCESSDENIED_URL = "/error.html";

	private static final Logger LOGGER = LogManager.getLogger(SecurityApplicationContext.class);

	@Resource(name = "securityService")
	private UserDetailsService securityService;

	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/public/**/**", "/css/**/**", "/js/**/**", "/icons/**/**",
				"/images/**/**", "/error.html", LOGIN_URL, ACCESSDENIED_URL, MAIN_URL, "/image.jpg");
	}

	public void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable();
		http.anonymous().disable();
		http.csrf().disable();

		// Log out configuration
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl(LOGIN_URL);

		// session Management configuration
		//http.sessionManagement().invalidSessionUrl(LOGIN_URL).maximumSessions(1);
		http.sessionManagement().invalidSessionUrl(LOGIN_URL);

		// Login configuration
		http.formLogin().loginProcessingUrl("/login").loginPage(LOGIN_URL).failureUrl(LOGIN_URL).defaultSuccessUrl(HOME_URL);

		// Exception control configuration
		http.exceptionHandling().accessDeniedPage(ACCESSDENIED_URL);

		http.authorizeRequests().anyRequest().authenticated().and().addFilterBefore(buildSecurityFilter(), FilterSecurityInterceptor.class);
	}

	protected UserDetailsService userDetailsService() {
		return securityService;
	}

	private Filter buildSecurityFilter() throws Exception {
		FilterSecurityInterceptor filter = new FilterSecurityInterceptor();
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix(StringUtils.EMPTY);
		AffirmativeBased accessDecisionManager = new AffirmativeBased(Arrays.asList(roleVoter, new AuthenticatedVoter()));
		filter.setAccessDecisionManager(accessDecisionManager);

		AuthenticationManager authenticationManager = authenticationManager();
		filter.setAuthenticationManager(authenticationManager);
		filter.setSecurityMetadataSource(securityMetadataSource());
		return filter;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	private FilterInvocationSecurityMetadataSource securityMetadataSource() {
		FilterInvocationSecurityMetadataSource metadataSource = new DefaultFilterInvocationSecurityMetadataSource(new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>());
		return metadataSource;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}