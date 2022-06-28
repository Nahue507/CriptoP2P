package ar.edu.unq.desaap.grupoj.backenddesappapi;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class TransactionsTest {

    @Test
    void testTransactionPriceIncreased() {
        Intention intention = new Intention();
        intention.setPrice(100);

        Transaction transaction = new Transaction();
        transaction.setPrice(120);
        transaction.setSaleIntention(intention);

        assertTrue(transaction.priceIncreased());
    }

    @Test
    void testTransactionPriceDecreased() {
        Intention intention = new Intention();
        intention.setPrice(100);

        Transaction transaction = new Transaction();
        transaction.setPrice(90);
        transaction.setSaleIntention(intention);

        assertTrue(transaction.priceDecreased());
    }

    @Test
    void transactionWithSameBuyerAndSellerShouldBeCancelled() {
        User buyer = new User();
        buyer.setId(1);

        User seller = new User();
        seller.setId(1);

        Transaction transaction = new Transaction();
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);

        assertTrue(transaction.shouldBeCancelled());
    }

    @Test
    void saleWithPriceBelow5PercentShouldBeCancelled() {
        User buyer = new User();
        buyer.setId(1);

        User seller = new User();
        buyer.setId(2);

        Intention intention = new Intention();
        intention.setPrice(100);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.SALE);
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setPrice(90);
        transaction.setSaleIntention(intention);

        assertTrue(transaction.shouldBeCancelled());
    }

    @Test
    void saleWithPriceIn5PercentShouldBeCancelled() {
        User buyer = new User();
        buyer.setId(1);

        User seller = new User();
        buyer.setId(2);

        Intention intention = new Intention();
        intention.setPrice(100);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.SALE);
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setPrice(98);
        transaction.setSaleIntention(intention);

        assertFalse(transaction.shouldBeCancelled());
    }

    @Test
    void buyWithPriceAbove5PercentShouldBeCancelled() {
        User buyer = new User();
        buyer.setId(1);

        User seller = new User();
        buyer.setId(2);

        Intention intention = new Intention();
        intention.setPrice(100);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.BUY);
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setPrice(110);
        transaction.setSaleIntention(intention);

        assertTrue(transaction.shouldBeCancelled());
    }

    @Test
    void buyWithPriceIn5PercentShouldBeCancelled() {
        User buyer = new User();
        buyer.setId(1);

        User seller = new User();
        buyer.setId(2);

        Intention intention = new Intention();
        intention.setPrice(100);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.BUY);
        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setPrice(103);
        transaction.setSaleIntention(intention);

        assertFalse(transaction.shouldBeCancelled());
    }

    @Test
    void testTransactionProperties() {
        Date date = new Date(2022, Calendar.JULY, 1);
        User issuer = new User();
        issuer.setName("Name");

        Intention buyIntention = new Intention();
        buyIntention.setDate(date);

        Intention saleIntention = new Intention();
        saleIntention.setIssuer(issuer);

        Currency currency = new Currency();
        currency.setPrice("1000");

        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setType(TransactionType.SALE);
        transaction.setBuyIntention(buyIntention);
        transaction.setSaleIntention(saleIntention);
        transaction.setCurrency(currency);
        transaction.setQuantity(200);
        transaction.setDate(date);
        transaction.setStatus(TransactionStatus.TO_BE_CONFIRMED);

        assertEquals(1, transaction.getId());
        assertEquals(TransactionType.SALE, transaction.getType());
        assertEquals(date, transaction.getBuyIntention().getDate());
        assertEquals("Name", transaction.getSaleIntention().getIssuer().getName());
        assertEquals("1000", transaction.getCurrency().getPrice());
        assertEquals(200, transaction.getQuantity());
        assertEquals(date, transaction.getDate());
        assertEquals(TransactionStatus.TO_BE_CONFIRMED, transaction.getStatus());
    }
}
