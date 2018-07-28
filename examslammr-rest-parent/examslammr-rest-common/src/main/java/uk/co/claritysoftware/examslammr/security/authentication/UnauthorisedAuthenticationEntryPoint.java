package uk.co.claritysoftware.examslammr.security.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * {@link AuthenticationEntryPoint} that responds with a {@link HttpServletResponse#SC_UNAUTHORIZED} (HTTP 401)
 */
public class UnauthorisedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.sendError(SC_UNAUTHORIZED);
    }
}
