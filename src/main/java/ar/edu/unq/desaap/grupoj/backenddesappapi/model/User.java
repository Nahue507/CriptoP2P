package ar.edu.unq.desaap.grupoj.backenddesappapi.model;

import ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions.UsersException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class User {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String address;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String cvu;

    @Column(nullable = false)
    private String wallet;

    @Column
    private Integer transactions = 0;

    @Column
    private Integer reputation = 0;


    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final Set<UserCurrency> currencies;

    private String role;

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public String getReputation() {
        return this.transactions != 0 ? this.reputation.toString() : "No Reputation";
    }

    public void addReputation(Integer points) {
        this.reputation = Math.max(reputation + points, 0);
    }

    public User() {
        this.currencies = new HashSet<>();
    }

    public User(String name, String lastname, String email, String address, String password, String cvu, String wallet) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.wallet = wallet;
        this.currencies = new HashSet<>();
    }

    public void testIsValid() throws UsersException {
        if (!this.isValidName()) {
            throw new UsersException("Invalid Name length");
        }
        if (!this.isValidLastName()) {
            throw new UsersException("Invalid lastname length");
        }
        if (!this.isValidEmail()) {
            throw new UsersException("Invalid Email");
        }
        if (!this.isValidPassword()) {
            throw new UsersException("Invalid Password");
        }
        if (!this.isValidAddress()) {
            throw new UsersException("Invalid Address length");
        }
        if (!this.isValidWallet()) {
            throw new UsersException("Invalid Wallet length");
        }
        if (!this.isValidCVU()) {
            throw new UsersException("Invalid CVU length");
        }
    }

    @JsonIgnore
    public boolean isValidName() {
        return isValidLength(this.name, 3, 30);
    }

    @JsonIgnore
    public boolean isValidLastName() {
        return isValidLength(this.lastname, 3, 30);
    }

    @JsonIgnore
    public boolean isValidAddress() {
        return isValidLength(this.address, 10, 20);
    }

    @JsonIgnore
    public boolean isValidCVU() {
        return this.cvu.length() == 22;
    }

    @JsonIgnore
    public boolean isValidWallet() {
        return this.wallet.length() == 8;
    }

    @JsonIgnore
    public boolean isValidEmail() {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(this.email);
        return matcher.find();
    }

    @JsonIgnore
    public boolean isValidPassword() {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(this.password);
        return matcher.find();
    }

    public void addTransaction() {
        this.transactions++;
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", Pattern.CASE_INSENSITIVE);

    private boolean isValidLength(String string, Integer min, Integer max) {
        return string.length() >= min && string.length() <= max;
    }
}