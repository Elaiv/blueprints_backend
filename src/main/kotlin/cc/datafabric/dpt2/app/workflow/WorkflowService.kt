package cc.datafabric.dpt2.app.workflow

import org.springframework.stereotype.Service

@Service
class WorkflowService(
    private val workflowRepository: WorkflowRepository,
) {
    fun saveWorkflow(workflow: Workflow): Workflow {
        return workflowRepository.save(workflow)
    }

    fun getAllWorkflows(): MutableList<Workflow> {
        return workflowRepository.findAll()
    }
}