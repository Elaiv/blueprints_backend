package cc.datafabric.dpt2.app.collection

import com.mongodb.client.MongoClient
import org.springframework.stereotype.Service

@Service
class CollectionService(
    private val collectionRepository: CollectionRepository,
    private val mongoClient: MongoClient
) {
    fun getCollections(): MutableList<Collection> {
        return collectionRepository.findAll()
    }
}