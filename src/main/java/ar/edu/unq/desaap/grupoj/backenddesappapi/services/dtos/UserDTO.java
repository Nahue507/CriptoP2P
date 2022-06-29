package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

public class UserDTO {
    public String name;
    public String lastname;
    public String email;
    public String address;
    public String password;
    public String cvu;
    public String wallet;

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    public UserDTO(String name, String lastname, String email, String address, String password, String cvu, String wallet) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.wallet = wallet;
    }
}