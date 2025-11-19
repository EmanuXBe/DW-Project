package co.edu.javeriana.Proyecto_Web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import co.edu.javeriana.Proyecto_Web.dto.JwtAuthenticationResponse;
import co.edu.javeriana.Proyecto_Web.dto.LoginDTO;
import co.edu.javeriana.Proyecto_Web.model.User;
import co.edu.javeriana.Proyecto_Web.repository.UserRepository;

@Service
public class AuthenticationService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByName(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));
        String jwt = jwtService.generateToken(user.getName());
        return new JwtAuthenticationResponse(jwt, user.getName(), user.getType());
    }
}
