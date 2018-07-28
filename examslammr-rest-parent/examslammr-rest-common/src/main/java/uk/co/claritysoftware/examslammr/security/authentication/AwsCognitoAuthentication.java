package uk.co.claritysoftware.examslammr.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Simple {@link org.springframework.security.core.Authentication} implementation that just carries the AWS Cognito Identity Id
 */
public class AwsCognitoAuthentication extends AbstractAuthenticationToken {

    private static final List<GrantedAuthority> USER_AUTHORITIES = singletonList(new SimpleGrantedAuthority("USER"));

    private final String identityId;

    public AwsCognitoAuthentication(String identityId) {
        super(USER_AUTHORITIES);
        super.setAuthenticated(true);
        this.identityId = identityId;
    }

    @Override
    public Object getCredentials() {
        return this.identityId;
    }

    @Override
    public Object getPrincipal() {
        return this.identityId;
    }
}
