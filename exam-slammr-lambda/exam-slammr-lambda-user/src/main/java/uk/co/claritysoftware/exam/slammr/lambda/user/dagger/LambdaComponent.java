package uk.co.claritysoftware.exam.slammr.lambda.user.dagger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

//@Singleton
//@Component(modules = { DynamoDBMapperModule.class })
public interface LambdaComponent {

	DynamoDBMapper getDBMapper();
}
