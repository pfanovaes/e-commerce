package br.com.letscode.ecommerce.infra.config;
//


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@SuppressWarnings("deprecation")
//@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
////public class SecurityConfig extends WebSecurityConfigurerAdapter {
//////
//////    @Bean
//////    @Override
//////    public AuthenticationManager authenticationManagerBean() throws Exception {
//////        return super.authenticationManagerBean();
//////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/**").permitAll() // <<< LIBERAR TUDO
////                .and()
////                .formLogin().and()
////                .httpBasic();
////
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    public static void main(String [] args){
////        System.out.println(new BCryptPasswordEncoder().encode("123"));
////    }
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
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
    private static final  String usersQuery = "select username, password, true from usuario where username = ?";
    private static final  String rolesQuery = "select username, role From usuario where username = ?";
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
//        http.csrf().disable().authorizeRequests()
//                .anyRequest().authenticated()
//                .and().httpBasic();
        http.httpBasic().and().authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
//        builder.inMemoryAuthentication()
//                .withUser("moises")
//                .password("{noop}123")
//                .roles("ADMIN");
//   }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()

                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        //(NoOpPasswordEncoder)
        return  NoOpPasswordEncoder.getInstance();
    }

}

