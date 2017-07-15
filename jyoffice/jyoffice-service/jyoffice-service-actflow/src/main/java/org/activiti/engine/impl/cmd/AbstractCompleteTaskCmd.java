package org.activiti.engine.impl.cmd;

import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.DelegationState;

public abstract class AbstractCompleteTaskCmd extends NeedsActiveTaskCmd<Void>
{
  private static final long serialVersionUID = 1L;

  public AbstractCompleteTaskCmd(String taskId)
  {
    super(taskId);
  }

  protected void executeTaskComplete(CommandContext commandContext, TaskEntity taskEntity, Map<String, Object> variables, boolean localScope,String deleteReason)
  {
    if ((taskEntity.getDelegationState() != null) && (taskEntity.getDelegationState().equals(DelegationState.PENDING))) {
      throw new ActivitiException("A delegated task cannot be completed, but should be resolved instead.");
    }

    commandContext.getProcessEngineConfiguration().getListenerNotificationHelper().executeTaskListeners(taskEntity, "complete");
    if ((Authentication.getAuthenticatedUserId() != null) && (taskEntity.getProcessInstanceId() != null)) {
      ExecutionEntity processInstanceEntity = (ExecutionEntity)commandContext.getExecutionEntityManager().findById(taskEntity.getProcessInstanceId());
      commandContext.getIdentityLinkEntityManager().involveUser(processInstanceEntity, Authentication.getAuthenticatedUserId(), "participant");
    }

    ActivitiEventDispatcher eventDispatcher = Context.getProcessEngineConfiguration().getEventDispatcher();
    if (eventDispatcher.isEnabled()) {
      if (variables != null)
        eventDispatcher.dispatchEvent(ActivitiEventBuilder.createEntityWithVariablesEvent(ActivitiEventType.TASK_COMPLETED, taskEntity, variables, localScope));
      else {
        eventDispatcher.dispatchEvent(ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_COMPLETED, taskEntity));
      }
    }

    commandContext.getTaskEntityManager().deleteTask(taskEntity, deleteReason, false, false);
    
    if (taskEntity.getExecutionId() != null) {
      ExecutionEntity executionEntity = (ExecutionEntity)commandContext.getExecutionEntityManager().findById(taskEntity.getExecutionId());
      Context.getAgenda().planTriggerExecutionOperation(executionEntity);
      commandContext.getHistoryManager().recordActivityEnd(executionEntity, deleteReason);
    }
  }
}