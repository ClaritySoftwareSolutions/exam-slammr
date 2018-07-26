package uk.co.claritysoftware.exam.slammr.web.interceptor;

import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring web interceptor to add the user's identity id to the MDC context before the request is processed
 */
public class IdentityIdMdcInterceptor extends HandlerInterceptorAdapter {

    private static final String IDENTITY_ID = "identityId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String identityId = SecurityContextHolder.getContext().getAuthentication().getName();
        MDC.put(IDENTITY_ID, identityId);

        return true;
    }

}
