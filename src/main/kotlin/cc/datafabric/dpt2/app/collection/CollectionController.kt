package cc.datafabric.dpt2.app.collection

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/collections")
class CollectionController(
    private val collectionService: CollectionService
) {
    @GetMapping
    fun getCollections() = collectionService.getCollections()
}