package emr.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;


@Configuration
@EnableWebMvcSecurity
public class SecurityConfig {
	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		DataSource aclDataSource;
		
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			  auth.jdbcAuthentication().dataSource(aclDataSource)
			  	.usersByUsernameQuery(
		  				"select username,password,enabled from principle where username=?")
		  		.authoritiesByUsernameQuery(
		  				"select username, role from principle where username=?");

			  if(System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME") == null) {
		          auth
		          	.inMemoryAuthentication()
		                .withUser("admin").password("admin").roles("ADMIN");
			  }
		  }
	}

    @Configuration
    @Order(1)
    public static class PatientWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
        	http
        		.antMatcher("/patient/**")
		      	.authorizeRequests()
		          .antMatchers("/patient/**").hasRole("USER")
		          .and().csrf().disable()
		          .httpBasic().realmName("Heartbeat");
        }
    }
    
    @Configuration
    @Order(2)
    public static class UserWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
        	http
        		.antMatcher("/user/**")
		      	.authorizeRequests()
		          .antMatchers("/user/**").hasRole("ADMIN")
		          .and().csrf().disable()
		          .httpBasic().realmName("Heartbeat");
        }
    }
    
    @Configuration
    @Order(3)
    public static class AuthorizedWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
        	http.csrf().disable()
        		.antMatcher("/authorized/**")
		      	.authorizeRequests()
		          .antMatchers("/authorized/**").hasAnyRole("ADMIN", "USER")
		          .and().csrf().disable()
		          .httpBasic().realmName("Heartbeat");
        }
    }

    @Configuration
    @Order(4)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {  	
        	http
    	  	.authorizeRequests()
              .antMatchers("/admin/**").hasRole("ADMIN")
              .and()
    	          .formLogin()
    	            .loginProcessingUrl("/j_spring_security_check")
    	          	.loginPage("/login")
    	          	.failureUrl("/login?error")
    	          	.usernameParameter("username").passwordParameter("password")
    	          	.defaultSuccessUrl("/admin")
              .and()
    	          .logout()
    	            .logoutUrl("/j_spring_security_logout")
    	          	.logoutSuccessUrl("/login?logout")
              .and()
    		  	.exceptionHandling().accessDeniedPage("/403")
              .and()
              	.csrf().disable();
        }
    }
}