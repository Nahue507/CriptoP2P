package ar.edu.unq.desaap.grupoj.backenddesappapi.controller;

import ar.edu.unq.desaap.grupoj.backenddesappapi.services.JwtUserDetailsService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.UsersService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.util.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    protected final Log logger = LogFactory.getLog(getClass());

    final UsersService usersService;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;

    public AuthenticationController(UsersService usersService, AuthenticationManager authenticationManager,
                                    JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email
                    , password));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "User is disabled");
            return ResponseEntity.status(500).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Invalid Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

}