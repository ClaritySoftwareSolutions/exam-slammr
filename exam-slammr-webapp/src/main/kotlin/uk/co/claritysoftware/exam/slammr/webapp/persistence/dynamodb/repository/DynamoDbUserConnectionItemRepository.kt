package uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.repository

import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository
import uk.co.claritysoftware.exam.slammr.webapp.persistence.dynamodb.item.user.UserConnectionItem

/**
 * Spring Data Spring Social repository for [UserConnectionItem]
 */
@EnableScan
interface DynamoDbUserConnectionItemRepository : CrudRepository<UserConnectionItem, String> {

	fun findAllByProviderIdAndProviderUserId(providerId: String, providerUserId: String): List<UserConnectionItem>

	fun findAllByProviderId(providerId: String): List<UserConnectionItem>

	fun findByUserIdAndProviderIdAndProviderUserId(userId: String, providerId: String, providerUserId: String): UserConnectionItem
}
