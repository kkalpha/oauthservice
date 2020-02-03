package com.tech.oauth.security.configuration.support;

import com.tech.oauth.security.provider.token.AppRedisTokenStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.oauth2.provider.token.TokenStore;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@ConditionalOnProperty(name = "spring.redis.app.enabled", havingValue ="true")
public class RedisConfig {

	@Value("${spring.redis.timeout}")
	private String timeOut;

	// @Value("${spring.redis.cluster.nodes}")
	// private String nodes;
	//
	// @Value("${spring.redis.cluster.max-redirects}")
	// private int maxRedirects;

	@Value("${spring.redis.host}")
	private String hostName;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private int maxWait;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWait);

		return config;
	}


	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(hostName);
		configuration.setPort(port);
		// configuration.setMaxRedirects(maxRedirects);

		return configuration;
	}

	/**
	 * JedisConnectionFactory
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration configuration,
			JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration);
		connectionFactory.setPoolConfig(jedisPoolConfig);
		return connectionFactory;
	}


	/**
	 * 使用Jackson序列化对象
	 * 
	 * 
	 * @date 2018/7/30 16:16
	 * @since 1.0.0
	 * @return org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer<java.lang.Object>
	 */
	@Bean
	public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(objectMapper);

		return serializer;
	}

	/**
	 * RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory,
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		// 字符串方式序列化KEY
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);

		// JSON方式序列化VALUE
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	@Bean
	public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
		return new AppRedisTokenStore(redisConnectionFactory);
	}

}
