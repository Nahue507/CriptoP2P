package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;

public class UserDetailsDTO {
    public Integer id;
    public String name;
    public String lastname;
    public String email;
    public String address;
    public String cvu;
    public String wallet;
    public Integer transactions;
    public String reputation;

    public UserDetailsDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.cvu = user.getCvu();
        this.wallet = user.getWallet();
        this.transactions = user.getTransactions();
        this.reputation = user.getReputation();
    }
}