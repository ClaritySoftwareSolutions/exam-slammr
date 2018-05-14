package uk.co.claritysoftware.exam.slammr.lambda.user.dagger;

//import javax.inject.Singleton;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

//import dagger.Module;
//import dagger.Provides;

/**
 * Dagger Module class to create {@link DynamoDBMapper} instances
 */
//@Module
public class DynamoDBMapperModule {

	private static final Regions REGION = Regions.EU_WEST_2;

//	@Singleton
//	@Provides
	public DynamoDBMapper provideDynamoDBMapper() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(REGION)
				.build();
		return new DynamoDBMapper(client);
	}

}
