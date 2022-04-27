package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private String lastname;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String password;
    @Column
    private String cvu;
    @Column
    private String wallet;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public User() {
    }

    public User(String name, String lastname, String email, String address, String password, String cvu, String wallet) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.wallet = wallet;
    }

    public boolean isValidName(){
        return isValidLength(this.name, 3, 30);
    }

    public boolean isValidLastName(){
        return isValidLength(this.lastname, 3, 30);
    }

    public boolean isValidAddress(){
        return isValidLength(this.address, 10, 20);
    }

    public boolean isValidCVU(){
        return isValidLength(this.cvu, 22, 22);
    }

    public boolean isValidWallet(){
        return isValidLength(this.wallet, 8, 8);
    }

    public boolean isValidEmail(){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(this.email);
        return matcher.find();
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean isValidLength(String string, Integer min, Integer max){
        return string.length() < min && string.length() > max;
    }
}
