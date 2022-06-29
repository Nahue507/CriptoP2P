package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions.UserNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final UsersService usersService;

    public JwtUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ar.edu.unq.desaap.grupoj.backenddesappapi.model.User user = null;
        try {
            user = usersService.findByEmail(email);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new User(user.getEmail(), user.getPassword(), authorityList);
    }
}
