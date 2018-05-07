package uk.co.claritysoftware.exam.slammr.lambda.user.dagger;

import javax.inject.Singleton;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import dagger.Component;

@Singleton
@Component(modules = { DynamoDBMapperModule.class })
public interface LambdaComponent {

	DynamoDBMapper getDBMapper();
}
