package com.saptarshi.authentication.filter;

import com.saptarshi.authentication.model.User_;
import com.saptarshi.authentication.repository.BlackListTokenRepository;
import com.saptarshi.authentication.repository.UserRepository;
import com.saptarshi.authentication.util.JWTUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final BlackListTokenRepository blackListTokenRepository;

    private final UserRepository userRepository;

    private final JWTUtility jwtUtility;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String userEmail = null;
        User_ user =null;

        if(header != null && header.startsWith("Bearer ")){
            token = header.substring(7);
            userEmail = jwtUtility.getUserNameFromToken(token);
            user = userRepository.findByEmail(userEmail).orElseThrow();
        }
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() ==null
                && blackListTokenRepository.findByToken(token).isEmpty() && !user.getRefreshToken().equals(token)){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtUtility.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        System.out.println("test");
        filterChain.doFilter(request,response);
    }
}
