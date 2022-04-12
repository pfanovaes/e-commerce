package br.com.letscode.ecommerce.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private BCryptPasswordEncoder passEncoder;


   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable();
       http.authorizeRequests().antMatchers("/login").permitAll()
               .antMatchers("/produtos/").hasRole("USER")
               .antMatchers("/produtos/").hasRole("ADMIN")

               .antMatchers("/usuarios/usuario/").hasRole("USER")


               .antMatchers("/produtos/codigo/**").hasRole("USER")
               .antMatchers("/produtos/codigo/**").hasRole("ADMIN")
               .antMatchers("/produtos/").hasRole("USER")
               .antMatchers("/produtos/create").hasRole("ADMIN")
               .antMatchers("/produtos/").hasRole("USER")

               .antMatchers("/usuarios/**").hasRole("ADMIN")
               .antMatchers("/carrinho/**").hasRole("USER")
               .antMatchers("/fabricantes/create").hasRole("ADMIN");
   }

    @Autowired
    protected void configurerSecurityGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passEncoder)
                .usersByUsernameQuery("select username, password, 1 from usuario where username = ?")
                .authoritiesByUsernameQuery("select username, role From usuario where username = ?");

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

