package co.id.telkom.digitalent.jwt;

import co.id.telkom.digitalent.response.WriteResponse;
import co.id.telkom.digitalent.response.DataResponse;
import co.id.telkom.digitalent.utils.SecurityUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorization extends BasicAuthenticationFilter {

    public JWTAuthorization(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityUtils.HEADER_STRING);

        // Check if Authorization Header Has Token
        if (header == null || header.startsWith(SecurityUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(SecurityUtils.HEADER_STRING);

        try {
            if (token != null) {
                String user = JWT.require(Algorithm.HMAC256(SecurityUtils.SECRET.getBytes()))
                              .build()
                              .verify(token.replace(SecurityUtils.TOKEN_PREFIX, ""))
                              .getSubject();

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
        } catch (Exception e) {
            WriteResponse.responseInternalServerError(response, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
