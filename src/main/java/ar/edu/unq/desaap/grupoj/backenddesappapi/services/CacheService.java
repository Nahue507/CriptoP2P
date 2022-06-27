package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.redis.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

@Service
public class CacheService {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private QuotationService quotationService;

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

    public String getCurrentPrice (String symbol){
        jedis = this.getConnection();
        return jedis.get(symbol);
    }

    public Currency getCurrency(String symbol){
        String price = jedis.get(symbol);
        return new Currency(symbol, price);
    }

    /**
     * Gets current dollar quotation and Stores all received currencies in cache with their price in ARS
     * @param currencies list of currencies
     */
    public void storeCurrencyPrices(List<Currency> currencies){
        jedis = this.getConnection();
        String dollarQuotation = quotationService.getDollarQuotation();

        for(Currency crypto : currencies){
            jedis.set(crypto.getSymbol(), quotationService.convert(crypto.getPrice(), dollarQuotation));
        }
        this.destroyPool();
    }
}