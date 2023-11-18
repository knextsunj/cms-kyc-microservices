package com.github.knextsunj.cms.login.config.auth;

import com.github.knextsunj.cms.login.service.impl.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Get token from Authorization header
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.debug("jws checking");
        logger.debug("Request URL : {}",request.getRequestURI());

        if (jws != null) {
            // Verify token and get user
            String user = jwtService.getAuthUser(request);
            logger.debug("jws is not null");
            // Authenticate
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }
}
