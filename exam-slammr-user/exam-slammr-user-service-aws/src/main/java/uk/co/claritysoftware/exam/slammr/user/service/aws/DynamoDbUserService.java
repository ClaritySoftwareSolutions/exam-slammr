package uk.co.claritysoftware.exam.slammr.user.service.aws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import uk.co.claritysoftware.exam.slammr.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import uk.co.claritysoftware.exam.slammr.user.service.domain.UserProfile;

/**
 * DynamoDB backed implementation of {@link UserService}
 */
@Slf4j
public class DynamoDbUserService implements UserService {

    private DynamoDBMapper dynamoDBMapper;

    @Override
    public UserProfile getUserProfile(String identityId) {

        UserProfile userProfile = dynamoDBMapper.load(UserProfile.class, identityId);

        return null;
    }

}
