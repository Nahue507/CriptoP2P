package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

@Service
public class CacheService {

    @Autowired
    private QuotationService quotationService;

    private Jedis jedis;

    private JedisPool pool;

    public Jedis getConnection() {
        pool = new JedisPool(new JedisPoolConfig(), "redis-18204.c56.east-us.azure.cloud.redislabs.com",18204);
        jedis = pool.getResource();
        jedis.auth("tTcCCmGqmRpA0zhfONjQzE1KUPmY3YWx");
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

    public void storeCurrencyPrices(List<Currency> currencies){
        for(Currency crypto : currencies){
            jedis.set(crypto.getSymbol(), quotationService.getPriceArs(crypto.getPrice()));
        }
        this.destroyPool();
    }
}