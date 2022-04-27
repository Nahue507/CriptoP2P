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
        user.setName(GetRandomStringOfLength(3));

        assertFalse(user.isValidName());
    }

    @Test
    public void testUserLongNameLengthShouldFail() {
        User user = new User();
        user.setName(GetRandomStringOfLength(31));

        assertFalse(user.isValidName());
    }

    @Test
    public void testUserShortLastNameLengthShouldFail() {
        User user = new User();
        user.setLastname(GetRandomStringOfLength(3));

        assertFalse(user.isValidLastName());
    }

    @Test
    public void testUserLongLastNameLengthShouldFail() {
        User user = new User();
        user.setLastname(GetRandomStringOfLength(31));

        assertFalse(user.isValidLastName());
    }

    @Test
    public void testUserShortAddressLengthShouldFail() {
        User user = new User();
        user.setAddress(GetRandomStringOfLength(9));

        assertFalse(user.isValidAddress());
    }

    @Test
    public void testUserLongAddressLengthShouldFail() {
        User user = new User();
        user.setAddress(GetRandomStringOfLength(21));

        assertFalse(user.isValidAddress());
    }

    @Test
    public void testUserInvalidCVULengthShouldFail() {
        User user = new User();
        user.setCvu(GetRandomStringOfLength(21));

        assertFalse(user.isValidCVU());
    }

    @Test
    public void testUserInvalidWalletLengthShouldFail() {
        User user = new User();
        user.setWallet(GetRandomStringOfLength(8));

        assertFalse(user.isValidWallet());
    }

    private String GetRandomStringOfLength(Integer length){
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
