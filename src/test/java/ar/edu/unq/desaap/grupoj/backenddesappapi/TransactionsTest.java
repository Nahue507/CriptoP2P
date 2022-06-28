package ar.edu.unq.desaap.grupoj.backenddesappapi;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Intention;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Transaction;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.TransactionType;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
}
