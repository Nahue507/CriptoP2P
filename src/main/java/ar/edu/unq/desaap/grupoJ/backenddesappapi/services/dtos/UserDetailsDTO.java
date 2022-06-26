package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos;

public class UserDetailsDTO {
    public Integer id;
    public String name;
    public String lastname;
    public String email;
    public String address;
    public String cvu;
    public String wallet;
    public Long reputation;

    public UserDetailsDTO(Integer id, String name, String lastname, String email, String address, String cvu, String wallet, Long reputation) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.cvu = cvu;
        this.wallet = wallet;
        this.reputation = reputation;
    }
}