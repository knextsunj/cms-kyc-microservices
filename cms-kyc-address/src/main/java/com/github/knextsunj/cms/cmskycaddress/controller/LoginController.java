package com.github.knextsunj.cms.cmskycaddress.controller;

import com.github.knextsunj.cms.cmskycaddress.domain.AccountCredentials;
import com.github.knextsunj.cms.cmskycaddress.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;



    @RequestMapping(value="/login", method= RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        var role ="";
           UsernamePasswordAuthenticationToken creds =
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword());

        Authentication auth = authenticationManager.authenticate(creds);
           Optional<String> optionalRole = auth.getAuthorities().stream().map((grantedAuthority -> {
                return grantedAuthority.getAuthority();
            })).findFirst();

           if(optionalRole.isPresent()) {
                role = optionalRole.get();
           }
        // Generate token
        String jwts = jwtService.getToken(auth.getName());

        // Build response with the generated token
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .header("role",role)
                .build();

    }
}
