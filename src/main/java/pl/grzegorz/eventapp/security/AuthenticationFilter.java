package pl.grzegorz.eventapp.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.grzegorz.eventapp.employees.EmployeeDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
class AuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer";

    private final EmployeeDetailsService employeeDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        checkAuthorizationHeader(request, response, filterChain, authorizationHeader);
        final String jwtToken;
        jwtToken = authorizationHeader.substring(TOKEN_PREFIX.length());
        String userEmail = jwtUtils.extractUsername(jwtToken);
        checkUserEmailAndSecurityContextHolder(jwtToken, userEmail);
        filterChain.doFilter(request, response);
    }

    private void checkUserEmailAndSecurityContextHolder(String jwtToken, String userEmail) {
        if (Objects.nonNull(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = employeeDetailsService.loadUserByUsername(userEmail);
            final boolean isTokenValid = jwtUtils.isTokenValid(jwtToken, userDetails);
            checkIsTokenValid(userDetails, isTokenValid);
        }
    }

    private void checkIsTokenValid(UserDetails userDetails, boolean isTokenValid) {
        if (isTokenValid) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
    }

    private void checkAuthorizationHeader(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain filterChain,
                                          String authorizationHeader)
            throws IOException, ServletException {
        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
        }
    }
}