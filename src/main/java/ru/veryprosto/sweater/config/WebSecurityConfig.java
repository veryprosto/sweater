package ru.veryprosto.sweater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.veryprosto.sweater.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//аннотация для того чтобы работала аннотация @PreAuthorize("hasAuthority('ADMIN')") в контроллере
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll()//это значит что доступ на главную страницу без авторизации, статик добавил для того чтобы стили раздавались без авторизации
                .anyRequest().authenticated() //а на остальные требуется аунтификация
                .and()
                .formLogin()//включаем форму авторизации - логин
                .loginPage("/login")//эта форма находится на этой странице
                .permitAll()// доступ к этой форме у всех
                .and()
                .logout()//разрешаем логаут
                .permitAll(); //всем
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}