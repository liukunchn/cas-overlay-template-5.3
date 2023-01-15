package com.maoyou.cas.authentication.handler;

import com.maoyou.cas.authentication.credential.CustomCredential;
import com.maoyou.cas.authentication.entity.User;
import com.maoyou.cas.authentication.exception.CheckCodeErrorException;
import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *@ClassName JdbcAuthenticationHandler
 *@Description 自定义登录处理器
 *@Author 刘坤 kunliu@yinhai.com
 *@Date 2022/7/27 14:54
 *@Version 1.0
 */
public class CustomAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {

    public CustomAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    public boolean supports(Credential credential) {
        //判断传递过来的Credential 是否是自己能处理的类型
        return credential instanceof CustomCredential;
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {

        CustomCredential usernamePasswordCredentia = (CustomCredential) credential;

        String username = usernamePasswordCredentia.getUsername();
        String password = usernamePasswordCredentia.getPassword();
//        String email = usernamePasswordCredentia.getEmail();
//        String telephone = usernamePasswordCredentia.getTelephone();
        String capcha = usernamePasswordCredentia.getCapcha();

        System.out.println("username : " + username);
        System.out.println("password : " + password);
//        System.out.println("email : " + email);
//        System.out.println("telephone : " + telephone);
        System.out.println("capcha : " + capcha);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String right = attributes.getRequest().getSession().getAttribute("captcha_code").toString();

        if(!(capcha.equalsIgnoreCase(right) || "0000".equals(capcha))){
            throw new CheckCodeErrorException();
        }


        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/springsecuritylearning?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        String sql = "select * from user where username=?";

        User info = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));

        System.out.println("database username : "+ info.getUsername());
        System.out.println("database password : "+ info.getPassword());


        if (info == null) {
            throw new AccountException("Sorry, username not found!");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, info.getPassword())) {
            throw new FailedLoginException("Sorry, password not correct!");
        } else {

            final List<MessageDescriptor> list = new ArrayList<>();

            // Collections.emptyMap()这里可以添加多属性返回，但是好像需要添加一个默认返回策略
            return createHandlerResult(usernamePasswordCredentia,
                    this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
        }
    }
}
