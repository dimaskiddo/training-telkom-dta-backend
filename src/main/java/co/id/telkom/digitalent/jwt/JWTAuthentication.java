package co.id.telkom.digitalent.jwt;

import co.id.telkom.digitalent.model.JWTModel;
import co.id.telkom.digitalent.response.WriteResponse;
import co.id.telkom.digitalent.response.DataResponse;
import co.id.telkom.digitalent.utils.SecurityUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthentication extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthentication(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // Set Login URL
        setFilterProcessesUrl(SecurityUtils.SIGN_IN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>()));
        } catch (Exception e) {
            WriteResponse.responseUnauthorized(response, e.getMessage().toUpperCase());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                       .withSubject(((User) authResult.getPrincipal()).getUsername())
                       .withExpiresAt(new Date(System.currentTimeMillis() + SecurityUtils.EXPIRATION_TIME))
                       .sign(Algorithm.HMAC256(SecurityUtils.SECRET));

        JWTModel jwtModel = new JWTModel();
        DataResponse<JWTModel> dataResponse = new DataResponse<>();

        jwtModel.setToken(token);

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(jwtModel);

        WriteResponse.responseSuccessWithData(response, dataResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        WriteResponse.responseInternalServerError(response, "");
    }
}
