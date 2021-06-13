package by.yaromkin.user_manager.config;

import by.yaromkin.user_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/", "/login").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/user", "/view").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                //Доступ разрешен всем пользователей
                .antMatchers("/", "/home", "**/").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated();

        httpSecurity.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("initialPassword")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
