package com.example.getiryemek_clone.config;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("localhost");
        config.setPort(6379);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, ApiResponse<?>> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, ApiResponse<?>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ApiResponse.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ApiResponse.class));

        return redisTemplate;
    }

    @Bean
    public CacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(ApiResponse.class)))
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(1))
                .computePrefixWith(cacheName ->  cacheName + ":");

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}






