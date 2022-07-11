package cc.datafabric.dpt2.app.workflow

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document
data class Workflow (
    var id: String? = null,
    var name: String,
    var blueprint: org.bson.Document? = null
)

interface WorkflowRepository : MongoRepository<Workflow, String>