package ar.edu.unq.desaap.grupoJ.backenddesappapi;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void testUserEmailWithoutAtShouldFail() {
        User user = new User();
        user.setEmail("pepito.hotmail.com");

        assertFalse(user.isValidEmail());
    }

    @Test
    public void testUserEmailWithoutDomainShouldFail() {
        User user = new User();
        user.setEmail("pepito@hotmail");

        assertFalse(user.isValidEmail());
    }

    @Test
    public void testUserShortNameLengthShouldFail() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(3));

        assertFalse(user.isValidName());
    }

    @Test
    public void testUserLongNameLengthShouldFail() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(31));

        assertFalse(user.isValidName());
    }

    @Test
    public void testUserShortLastNameLengthShouldFail() {
        User user = new User();
        user.setLastname(GetRandomStringOfLengthOnlyLetters(3));

        assertFalse(user.isValidLastName());
    }

    @Test
    public void testUserLongLastNameLengthShouldFail() {
        User user = new User();
        user.setLastname(GetRandomStringOfLengthOnlyLetters(31));

        assertFalse(user.isValidLastName());
    }

    @Test
    public void testUserShortAddressLengthShouldFail() {
        User user = new User();
        user.setAddress(GetRandomStringOfLengthOnlyLetters(9));

        assertFalse(user.isValidAddress());
    }

    @Test
    public void testUserLongAddressLengthShouldFail() {
        User user = new User();
        user.setAddress(GetRandomStringOfLengthOnlyLetters(21));

        assertFalse(user.isValidAddress());
    }

    @Test
    public void testUserInvalidCVULengthShouldFail() {
        User user = new User();
        user.setCvu(GetRandomStringOfLengthOnlyLetters(21));

        assertFalse(user.isValidCVU());
    }

    @Test
    public void testUserInvalidWalletLengthShouldFail() {
        User user = new User();
        user.setWallet(GetRandomStringOfLengthOnlyLetters(8));

        assertFalse(user.isValidWallet());
    }

    @Test
    public void testUserWithCorrectEmail() {
        User user = new User();
        user.setEmail("pepito@hotmail.com");

        assertTrue(user.isValidEmail());
    }


    @Test
    public void testUserCorrectLengthName() {
        User user = new User();
        user.setName(GetRandomStringOfLengthOnlyLetters(10));
        System.out.println(user.getName().length());
        assertTrue(user.isValidName());
    }
       @Test
    public void testUserCorrectLastName() {
        User user = new User();
        user.setLastname(GetRandomStringOfLengthOnlyLetters(10));

        assertTrue(user.isValidLastName());
    }


    @Test
    public void testUserCorrectLengthAddress() {
        User user = new User();
        user.setAddress(GetRandomStringOfLengthOnlyLetters(10) + GetRandomStringNumericOfLengthOnlyLetters(4));

        assertTrue(user.isValidAddress());
    }


    @Test
    public void testUserValidCVU() {
        User user = new User();
        user.setCvu(GetRandomStringNumericOfLengthOnlyLetters(8));

        assertTrue(user.isValidCVU());
    }

    @Test
    public void testUserValidWalletLength() {
        User user = new User();
        user.setWallet(GetRandomStringOfLengthOnlyLetters(8));

        assertTrue(user.isValidWallet());
    }




    private String GetRandomStringOfLengthOnlyLetters(Integer length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
    private String GetRandomStringNumericOfLengthOnlyLetters(Integer length){
        int number = (int) (Math.random() * length) + 1;
        return number + "";
    }


}
