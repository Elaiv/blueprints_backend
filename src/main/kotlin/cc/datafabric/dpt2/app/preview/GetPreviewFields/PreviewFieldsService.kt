package cc.datafabric.dpt2.app.preview.GetPreviewFields

import com.mongodb.client.MongoClient
import org.bson.Document
import org.springframework.stereotype.Service

@Service
class PreviewFieldsService(
    val mongoClient: MongoClient
) {
    fun getPreviewFields(id: String, stages: String): ArrayList<Document> {
        println(stages)
        println(Document.parse(stages))
        println(listOf(Document.parse(stages)))
        return mongoClient
            .getDatabase("dpt_data")
            .getCollection("collection_$id")
            .aggregate(listOf(Document.parse(stages))).into(ArrayList())
    }
}