package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.CurrencyDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.redis.RedisProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Date;
import java.util.List;

@Service
public class CacheService {

    @Autowired
    private RedisProperties redisProperties;

    private Jedis jedis;

    private JedisPool pool;

    public Jedis getConnection() {
        pool = new JedisPool(new JedisPoolConfig(), redisProperties.getHost() ,redisProperties.getPort());
        jedis = pool.getResource();
        jedis.auth(redisProperties.getAuth());
        return jedis;
    }

    public void destroyPool() {
        // Close the connection
        if (jedis != null) {
            jedis.close();
        }

        // Destroy the pool
        if (pool != null) {
            pool.destroy();
        }
    }

    public CurrencyDTO getCurrency(String symbol) throws JsonProcessingException {
        jedis = this.getConnection();
        String currency = jedis.get(symbol);
        this.destroyPool();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(currency, CurrencyDTO.class);
    }

    /**
     * Gets current dollar quotation and Stores all received currencies in cache
     * @param currencies list of currencies
     */
    public void storeCurrencyPrices(List<Currency> currencies) throws JsonProcessingException {
        jedis = this.getConnection();

        for(Currency crypto : currencies){
            ObjectMapper mapper = new ObjectMapper();
            String jsonCrypto = mapper.writeValueAsString(new CurrencyDTO(crypto, new Date()));

            jedis.set(crypto.getSymbol(), jsonCrypto);
        }
        this.destroyPool();
    }
}