package Sachin.journalApp.filter;

import Sachin.journalApp.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter{ // OncePerRequestFilter - if any request it will filtered at least once
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization"); // get header from the request that is authorisation
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // if the auth came like this then
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt); // extracting username from jwt token
        }
        if (username != null) { // if username is not present then userDetail nikallo
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // This is the username from DB
            if (jwtUtil.validateToken(jwt)) { // Now validate through jwt
                // if person is authenticated then following process of setting the auth will be done
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        response.addHeader("admin", "Sachin");
        chain.doFilter(request, response); // we send request and response for further chains if there are any
    }
}