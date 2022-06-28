package ar.edu.unq.desaap.grupoj.backenddesappapi;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.UsersException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testUserEmailWithoutAtShouldFail() {
        User user = new User();
        user.setEmail("pepito.hotmail.com");

        assertFalse(user.isValidEmail());
    }

    @Test
    void testUserEmailWithoutDomainShouldFail() {
        User user = new User();
        user.setEmail("pepito@hotmail");

        assertFalse(user.isValidEmail());
    }

    @Test
    void testUserShortNameLengthShouldFail() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(2));

        assertFalse(user.isValidName());
    }

    @Test
    void testUserLongNameLengthShouldFail() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(31));

        assertFalse(user.isValidName());
    }

    @Test
    void testUserShortLastNameLengthShouldFail() {
        User user = new User();
        user.setLastname(GetRandomStringOfLengthOnlyLetters(2));

        assertFalse(user.isValidLastName());
    }

    @Test
    void testUserLongLastNameLengthShouldFail() {
        User user = new User();
        user.setLastname(GetRandomStringOfLengthOnlyLetters(31));

        assertFalse(user.isValidLastName());
    }

    @Test
    void testUserShortAddressLengthShouldFail() {
        User user = new User();
        user.setAddress(GetRandomStringOfLengthOnlyLetters(9));

        assertFalse(user.isValidAddress());
    }

    @Test
    void testUserLongAddressLengthShouldFail() {
        User user = new User();
        user.setAddress(GetRandomStringOfLengthOnlyLetters(21));

        assertFalse(user.isValidAddress());
    }

    @Test
    void testUserInvalidCVULengthShouldFail() {
        User user = new User();
        user.setCvu(GetRandomStringOfLengthOnlyLetters(21));

        assertFalse(user.isValidCVU());
    }

    @Test
    void testUserInvalidWalletLengthShouldFail() {
        User user = new User();
        user.setWallet(GetRandomStringOfLengthOnlyLetters(7));

        assertFalse(user.isValidWallet());
    }

    @Test
    void testUserInvalidNameShouldThrowException() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(2));

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid Name length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserInvalidLastNameShouldThrowException() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(10));
        user.setLastname(GetRandomStringOfLengthOnlyLetters(2));

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid lastname length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserWithInvalidEmailThrowException() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(10));
        user.setLastname(GetRandomStringOfLengthOnlyLetters(10));
        user.setEmail("pepito.hotmail.com");

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid Email";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserInvalidPasswordShouldThrowException() {
        User user = new User(
                GetRandomStringOfLengthOnlyLetters(10),
                GetRandomStringOfLengthOnlyLetters(10),
                "pepito@hotmail.com",
                GetRandomStringOfLengthOnlyLetters(15),
                "123",
                "",
                ""
        );

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid Password";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserInvalidAddressShouldThrowException() {
        User user = new User(
                GetRandomStringOfLengthOnlyLetters(10),
                GetRandomStringOfLengthOnlyLetters(10),
                "pepito@hotmail.com",
                GetRandomStringOfLengthOnlyLetters(9),
                "Abc123$",
                "",
                ""
        );

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid Address length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserInvalidCVUShouldThrowException() {
        User user = new User(
                GetRandomStringOfLengthOnlyLetters(10),
                GetRandomStringOfLengthOnlyLetters(10),
                "pepito@hotmail.com",
                GetRandomStringOfLengthOnlyLetters(15),
                "Abc123$",
                GetRandomStringOfLengthOnlyLetters(21),
                GetRandomStringOfLengthOnlyLetters(8)
        );

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid CVU length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUserInvalidWalletShouldThrowException() {
        User user = new User(
                GetRandomStringOfLengthOnlyLetters(10),
                GetRandomStringOfLengthOnlyLetters(10),
                "pepito@hotmail.com",
                GetRandomStringOfLengthOnlyLetters(15),
                "Abc123$",
                GetRandomStringOfLengthOnlyLetters(22),
                GetRandomStringOfLengthOnlyLetters(7)
        );

        Exception exception = assertThrows(UsersException.class, user::testIsValid);

        String expectedMessage = "Invalid Wallet length";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testTransactionStartInZero() {
        User user = new User();
        assertEquals(0, user.getTransactions());
    }

    @Test
    void testUserWithNoTransactionShouldReturnNoReputation() {
        User user = new User();
        assertEquals("No Reputation", user.getReputation());
    }

    @Test
    void testUserReputationShouldIncrease() {
        User user = new User();
        user.addTransaction();
        user.addReputation(5);
        user.addTransaction();
        user.addReputation(10);
        assertEquals("15", user.getReputation());
    }

    @Test
    void testUserReputationShouldDecrease() {
        User user = new User();
        user.addTransaction();
        user.addReputation(50);
        user.addTransaction();
        user.addReputation(-10);
        assertEquals("40", user.getReputation());
    }

    @Test
    void testUserReputationCannotBeNegative() {
        User user = new User();
        user.addTransaction();
        user.addReputation(-30);
        assertEquals("0", user.getReputation());
    }

    @Test
    void testAddTransactionCalledTwiceShouldBeTwo() {
        User user = new User();
        user.addTransaction();
        user.addTransaction();
        assertEquals(2, user.getTransactions());
    }

    private String GetRandomStringOfLengthOnlyLetters(Integer length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String GetRandomStringNumericOfLength(Integer length) {
        return RandomStringUtils.randomNumeric(length);
    }
}
