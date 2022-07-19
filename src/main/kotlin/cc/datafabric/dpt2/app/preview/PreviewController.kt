package cc.datafabric.dpt2.app.preview

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/preview")
class PreviewController (
        private val previewService: PreviewService
) {
    @GetMapping
    fun getPreview(
            @RequestParam("id") id : String,
            @RequestParam("limit") limit: Int,
            @RequestParam("pipeline") pipeline: List<String>? = null
    ) = previewService.getPreview(id, limit, pipeline)
}