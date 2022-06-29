package ar.edu.unq.desaap.grupoj.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional <User> findByEmail(String email);


}
