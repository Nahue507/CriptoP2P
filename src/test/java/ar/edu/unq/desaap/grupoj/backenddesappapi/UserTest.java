package ar.edu.unq.desaap.grupoj.backenddesappapi;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testUserWithCorrectEmail() {
        User user = new User();
        user.setEmail("pepito@hotmail.com");

        assertTrue(user.isValidEmail());
    }

    @Test
    void testUserCorrectLengthName() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(10));
        System.out.println(user.getName().length());
        assertTrue(user.isValidName());
    }

    @Test
    void testUserCorrectLastName() {
        User user = new User();
        user.setLastname(GetRandomStringOfLengthOnlyLetters(10));

        assertTrue(user.isValidLastName());
    }

    @Test
    void testUserCorrectLengthAddress() {
        User user = new User();
        user.setAddress(GetRandomStringOfLengthOnlyLetters(10) + " " + GetRandomStringNumericOfLength(4));

        assertTrue(user.isValidAddress());
    }

    @Test
    void testUserValidCVU() {
        User user = new User();
        user.setCvu(GetRandomStringNumericOfLength(22));

        assertTrue(user.isValidCVU());
    }

    @Test
    void testUserValidWalletLength() {
        User user = new User();
        user.setWallet(GetRandomStringOfLengthOnlyLetters(8));

        assertTrue(user.isValidWallet());
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
