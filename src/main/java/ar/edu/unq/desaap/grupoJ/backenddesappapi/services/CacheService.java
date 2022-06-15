package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;


import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices.BinanceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;


@Service
public class CacheService {
    private BinanceController binance = new BinanceController();
    private Jedis jedis;
    private JedisPool pool;

    @Autowired
    private CurrencyConverter currencyConverter;


    public Jedis getDirectConnection() {
        jedis = new Jedis("redis-18204.c56.east-us.azure.cloud.redislabs.com",18204);
        jedis.auth("tTcCCmGqmRpA0zhfONjQzE1KUPmY3YWx");
        System.out.println("Logee en redis");
        return jedis;
    }
    public void closeDirectConnection() {
        if (jedis != null) {
            jedis.close();
        }
    }
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
    public Currency getCurrentPriceAsCurrency(String symbol){
        Jedis conection = this.getConnection();
        Currency currency = new Currency(symbol,jedis.get(symbol));
        return currency;
    }
   @Scheduled(fixedDelay = 600000)
    private void updatePrices(){
        List<Currency> currencies = binance.getAllPrices();
        Jedis conection = this.getConnection();

        for(Currency crypto : currencies){
            jedis.set(crypto.getSymbol(), currencyConverter.getPriceArs(crypto.getPriceAsString()));
        }
        this.destroyPool();
    }

}
