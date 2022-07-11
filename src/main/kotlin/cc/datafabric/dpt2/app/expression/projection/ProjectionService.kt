package cc.datafabric.dpt2.app.expression.projection

import cc.datafabric.dpt2.app.expression.toParameterDeclarations
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.mongodb.client.MongoClient
import org.bson.Document
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.util.LinkedList

@Service
class ProjectionService(
    projections: List<Projection<*>>,
    private val mongoClient: MongoClient
) {
    private val _projections = projections.associateBy { it.name()!! }
    private val objectMapper = ObjectMapper()

    fun projections() = _projections.values.map {
        ProjectionHeaderResponse(
            name = it.name()!!,
            description = it.description()!!
        )
    }

    fun pipeline(fields: List<ProjectionField>): List<Document> {
        val pipeline = LinkedList<Document>()

        val removeFields = fields.filter { it.remove }

        fields.filter { it.expression != null }.forEach {
            pipeline.push(Document("\$set", Document(it.name, getExpression(it.expression!!))))
        }

        if (removeFields.isNotEmpty()) {
            val removeStage = Document()

            removeFields.forEach {
                removeStage[it.name] = 0
            }

            pipeline.push(Document("\$project", removeStage))
        }

        return pipeline
    }

    fun fields(connection: String, db: String, collection: String, limit: Int): Set<String> {
        val fields = mutableSetOf<String>()

        mongoClient
            .getDatabase(db)
            .getCollection(collection)
            .find()
            .forEach { fields.addAll(it.keys) }

        return fields
    }

    fun preview(
        fields: List<ProjectionField>,
        connection: String,
        db: String,
        collection: String,
        limit: Int
    ): List<Document> {
        val pipeline = pipeline(fields)

        return mongoClient
            .getDatabase(db)
            .getCollection(collection)
            .aggregate(pipeline + listOf(
                Document("\$set", Document("_id", Document("\$toString", "\$_id"))),
                Document("\$limit", limit)
            ))
            .into(ArrayList())
    }

    fun execute(
        fields: List<ProjectionField>,
        connection: String,
        db: String,
        collection: String,
        output: String,
        user: Principal
    ){
        val pipeline = pipeline(fields)

        mongoClient
            .getDatabase(db)
            .getCollection(collection)
            .aggregate(pipeline + listOf(
                Document("\$out", output)
            ))
            .toCollection()
    }

    fun declaration(name: String) = _projections[name]?.parameters()?.toParameterDeclarations()

    fun getExpression(
        expression: ProjectionExpression
    ): Any {
        val projection = _projections[expression.name]

        if (projection != null) {
            return getExpression(projection, expression.parameters)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    private fun <T> getExpression(
        projection: Projection<T>,
        parameters: ObjectNode
    ) = projection.expression(objectMapper.convertValue(parameters, projection.parameters()))
}


data class ProjectionField(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("expression")
    val expression: ProjectionExpression?,
    @JsonProperty("remove")
    val remove: Boolean
)

data class ProjectionExpression(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("parameters")
    val parameters: ObjectNode
)

data class ProjectionHeaderResponse(
    val name: String,
    val description: String
)