package org.activiti.engine.impl.persistence.entity;

import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.compatibility.Activiti5CompatibilityHandler;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryManager;
import org.activiti.engine.impl.persistence.CountingExecutionEntity;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.TaskDataManager;
import org.activiti.engine.impl.util.Activiti5Util;
import org.activiti.engine.runtime.Clock;
import org.activiti.engine.task.Task;

public class TaskEntityManagerImpl extends AbstractEntityManager<TaskEntity>
  implements TaskEntityManager
{
  protected TaskDataManager taskDataManager;

  public TaskEntityManagerImpl(ProcessEngineConfigurationImpl processEngineConfiguration, TaskDataManager taskDataManager)
  {
    super(processEngineConfiguration);
    this.taskDataManager = taskDataManager;
  }

  protected DataManager<TaskEntity> getDataManager()
  {
    return this.taskDataManager;
  }

  public TaskEntity create()
  {
    TaskEntity taskEntity = (TaskEntity)super.create();
    taskEntity.setCreateTime(getClock().getCurrentTime());
    return taskEntity;
  }

  public void insert(TaskEntity taskEntity, boolean fireCreateEvent)
  {
    if (taskEntity.getOwner() != null) {
      addOwnerIdentityLink(taskEntity, taskEntity.getOwner());
    }
    if (taskEntity.getAssignee() != null) {
      addAssigneeIdentityLinks(taskEntity);
    }

    super.insert(taskEntity, fireCreateEvent);
  }

  public void insert(TaskEntity taskEntity, ExecutionEntity execution)
  {
    if ((execution != null) && (execution.getTenantId() != null)) {
      taskEntity.setTenantId(execution.getTenantId());
    }

    if (execution != null) {
      execution.getTasks().add(taskEntity);
      taskEntity.setExecutionId(execution.getId());
      taskEntity.setProcessInstanceId(execution.getProcessInstanceId());
      taskEntity.setProcessDefinitionId(execution.getProcessDefinitionId());

      getHistoryManager().recordTaskExecutionIdChange(taskEntity.getId(), taskEntity.getExecutionId());
    }

    insert(taskEntity, true);

    if ((execution != null) && (isExecutionRelatedEntityCountEnabled(execution))) {
      CountingExecutionEntity countingExecutionEntity = (CountingExecutionEntity)execution;
      countingExecutionEntity.setTaskCount(countingExecutionEntity.getTaskCount() + 1);
    }

    if ((getEventDispatcher().isEnabled()) && 
      (taskEntity.getAssignee() != null)) {
      getEventDispatcher().dispatchEvent(
        ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_ASSIGNED, taskEntity));
    }

    getHistoryManager().recordTaskCreated(taskEntity, execution);
    getHistoryManager().recordTaskId(taskEntity);
    if (taskEntity.getFormKey() != null)
      getHistoryManager().recordTaskFormKeyChange(taskEntity.getId(), taskEntity.getFormKey());
  }

  public void changeTaskAssignee(TaskEntity taskEntity, String assignee)
  {
    if (((taskEntity.getAssignee() != null) && (!taskEntity.getAssignee().equals(assignee))) || (
      (taskEntity
      .getAssignee() == null) && (assignee != null))) {
      taskEntity.setAssignee(assignee);
      fireAssignmentEvents(taskEntity);

      if (taskEntity.getId() != null) {
        getHistoryManager().recordTaskAssigneeChange(taskEntity.getId(), taskEntity.getAssignee());
        addAssigneeIdentityLinks(taskEntity);
        update(taskEntity);
      }
    }
  }

  public void changeTaskOwner(TaskEntity taskEntity, String owner)
  {
    if (((taskEntity.getOwner() != null) && (!taskEntity.getOwner().equals(owner))) || (
      (taskEntity
      .getOwner() == null) && (owner != null))) {
      taskEntity.setOwner(owner);

      if (taskEntity.getId() != null) {
        getHistoryManager().recordTaskOwnerChange(taskEntity.getId(), taskEntity.getOwner());
        addOwnerIdentityLink(taskEntity, taskEntity.getOwner());
        update(taskEntity);
      }
    }
  }

  protected void fireAssignmentEvents(TaskEntity taskEntity) {
    getProcessEngineConfiguration().getListenerNotificationHelper()
      .executeTaskListeners(taskEntity, "assignment");

    getHistoryManager().recordTaskAssignment(taskEntity);

    if (getEventDispatcher().isEnabled())
      getEventDispatcher().dispatchEvent(ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_ASSIGNED, taskEntity));
  }

  private void addAssigneeIdentityLinks(TaskEntity taskEntity)
  {
    if ((taskEntity.getAssignee() != null) && (taskEntity.getProcessInstance() != null))
      getIdentityLinkEntityManager().involveUser(taskEntity.getProcessInstance(), taskEntity.getAssignee(), "participant");
  }

  protected void addOwnerIdentityLink(TaskEntity taskEntity, String owner)
  {
    if ((owner == null) && (taskEntity.getOwner() == null)) {
      return;
    }

    if ((owner != null) && (taskEntity.getProcessInstanceId() != null))
      getIdentityLinkEntityManager().involveUser(taskEntity.getProcessInstance(), owner, "participant");
  }

  public void deleteTasksByProcessInstanceId(String processInstanceId, String deleteReason, boolean cascade)
  {
    List<TaskEntity> tasks = findTasksByProcessInstanceId(processInstanceId);

    for (TaskEntity task : tasks) {
      if ((getEventDispatcher().isEnabled()) && (!task.isCanceled())) {
        task.setCanceled(true);
        getEventDispatcher().dispatchEvent(
          ActivitiEventBuilder.createActivityCancelledEvent(task
          .getExecution().getActivityId(), task.getName(), task
          .getExecutionId(), task.getProcessInstanceId(), task
          .getProcessDefinitionId(), "userTask", deleteReason));
      }

      deleteTask(task, deleteReason, cascade, false);
    }
  }

  public void deleteTask(TaskEntity task, String deleteReason, boolean cascade, boolean cancel)
  {
    if (!task.isDeleted()) {
      getProcessEngineConfiguration().getListenerNotificationHelper()
        .executeTaskListeners(task, "delete");

      task.setDeleted(true);

      String taskId = task.getId();

      List<Task> subTasks = findTasksByParentTaskId(taskId);
      for (Task subTask : subTasks) {
        deleteTask((TaskEntity)subTask, deleteReason, cascade, cancel);
      }

      getIdentityLinkEntityManager().deleteIdentityLinksByTaskId(taskId);
      getVariableInstanceEntityManager().deleteVariableInstanceByTask(task);

      if (cascade)
        getHistoricTaskInstanceEntityManager().delete(taskId);
      else {
        getHistoryManager().recordTaskEnd(taskId, deleteReason);
      }

      delete(task, false);

      if (getEventDispatcher().isEnabled()) {
        if ((cancel) && (!task.isCanceled())) {
          task.setCanceled(true);
          getEventDispatcher().dispatchEvent(
            ActivitiEventBuilder.createActivityCancelledEvent(task
            .getExecution() != null ? task.getExecution().getActivityId() : null, task
            .getName(), task.getExecutionId(), task
            .getProcessInstanceId(), task
            .getProcessDefinitionId(), "userTask", deleteReason));
        }

        getEventDispatcher().dispatchEvent(ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_DELETED, task));
      }
    }
  }

  public void delete(TaskEntity entity, boolean fireDeleteEvent)
  {
    super.delete(entity, fireDeleteEvent);

    if ((entity.getExecutionId() != null) && (isExecutionRelatedEntityCountEnabledGlobally())) {
      CountingExecutionEntity countingExecutionEntity = (CountingExecutionEntity)entity.getExecution();
      if (isExecutionRelatedEntityCountEnabled(countingExecutionEntity))
        countingExecutionEntity.setTaskCount(countingExecutionEntity.getTaskCount() - 1);
    }
  }

  public List<TaskEntity> findTasksByExecutionId(String executionId)
  {
    return this.taskDataManager.findTasksByExecutionId(executionId);
  }

  public List<TaskEntity> findTasksByProcessInstanceId(String processInstanceId)
  {
    return this.taskDataManager.findTasksByProcessInstanceId(processInstanceId);
  }

  public List<Task> findTasksByQueryCriteria(TaskQueryImpl taskQuery)
  {
    return this.taskDataManager.findTasksByQueryCriteria(taskQuery);
  }

  public List<Task> findTasksAndVariablesByQueryCriteria(TaskQueryImpl taskQuery)
  {
    return this.taskDataManager.findTasksAndVariablesByQueryCriteria(taskQuery);
  }

  public long findTaskCountByQueryCriteria(TaskQueryImpl taskQuery)
  {
    return this.taskDataManager.findTaskCountByQueryCriteria(taskQuery);
  }

  public List<Task> findTasksByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults)
  {
    return this.taskDataManager.findTasksByNativeQuery(parameterMap, firstResult, maxResults);
  }

  public long findTaskCountByNativeQuery(Map<String, Object> parameterMap)
  {
    return this.taskDataManager.findTaskCountByNativeQuery(parameterMap);
  }

  public List<Task> findTasksByParentTaskId(String parentTaskId)
  {
    return this.taskDataManager.findTasksByParentTaskId(parentTaskId);
  }

  public void deleteTask(String taskId, String deleteReason, boolean cascade)
  {
    TaskEntity task = (TaskEntity)findById(taskId);

    if (task != null) {
      if (task.getExecutionId() != null) {
        throw new ActivitiException("The task cannot be deleted because is part of a running process");
      }

      if (Activiti5Util.isActiviti5ProcessDefinitionId(getCommandContext(), task.getProcessDefinitionId())) {
        Activiti5CompatibilityHandler activiti5CompatibilityHandler = Activiti5Util.getActiviti5CompatibilityHandler();
        activiti5CompatibilityHandler.deleteTask(taskId, deleteReason, cascade);
        return;
      }

      deleteTask(task, deleteReason, cascade, false);
    } else if (cascade) {
      getHistoricTaskInstanceEntityManager().delete(taskId);
    }
  }

  public void updateTaskTenantIdForDeployment(String deploymentId, String newTenantId)
  {
    this.taskDataManager.updateTaskTenantIdForDeployment(deploymentId, newTenantId);
  }

  public TaskDataManager getTaskDataManager() {
    return this.taskDataManager;
  }

  public void setTaskDataManager(TaskDataManager taskDataManager) {
    this.taskDataManager = taskDataManager;
  }
}