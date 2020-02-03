 package com.tech.oauth.security.configuration.support;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.tech.oauth.security.provider.token.AppRedisTokenStore;

/**
 * @author YK
 */
@Configuration
public class BeanConfig {
     @Bean
     public PasswordEncoder passwordEncoder() throws Exception {
         return new BCryptPasswordEncoder();
     }
     
//     @Bean
//     @ConditionalOnProperty(name = "spring.redis.app.enabled", havingValue="true")
//     public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
//         return new AppRedisTokenStore(redisConnectionFactory);
//     }
     
//     @Bean
//     public AppAuthenticationProvider authenticationProvider() {
//         return new AppAuthenticationProvider();
//     }
}
