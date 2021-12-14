package pl.wsiz.restaurantservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfiguration(DataSource dataSource, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.dataSource = dataSource;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**", "/api/user/**")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and().cors();
    }

    /**
     * JdbcUserDetailsManagerConfigurer#usersByUsernameQuery requires 3 columns in query result:
     * username, password and 'enabled'. At this point application don't support enable/disable user account feature and
     * for that reason 'true' was hardcoded in query result.
     */
    @Autowired
    public void inMemoryAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, 'true' from user where email = ?")
                .authoritiesByUsernameQuery("select email, role from user where email = ?")
                .passwordEncoder(bcryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
}
