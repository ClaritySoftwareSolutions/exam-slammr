package uk.co.claritysoftware.examslammr.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import uk.co.claritysoftware.examslammr.security.authentication.AwsCognitoAuthentication;

/**
 * Simple filter to use the {@code x-exam-slammr-identityId} header and create a Spring Security user principal from it
 */
public class IdentityIdHeaderFilter extends GenericFilterBean {

    private static final String IDENTITY_ID_HEADER = "x-exam-slammr-identityId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String accessToken = httpRequest.getHeader(IDENTITY_ID_HEADER);

        if (accessToken != null && accessToken.length() > 0) {
            SecurityContextHolder.getContext().setAuthentication(new AwsCognitoAuthentication(accessToken));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
