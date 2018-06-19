package uk.co.claritysoftware.exam.slammr.user.service;

import uk.co.claritysoftware.exam.slammr.user.service.domain.UserProfile;

/**
 * Service API for User concerns
 */
public interface UserService {

    /**
     * Returns the {@link UserProfile} associated with the specified id.
     *
     * @param identityId the id that uniquely identities the user
     * @return the UserProfile
     */
    UserProfile getUserProfile(String identityId);
}
