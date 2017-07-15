package org.activiti.engine.impl.cmd;

import java.util.Map;
import org.activiti.engine.compatibility.Activiti5CompatibilityHandler;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.util.Activiti5Util;

public class CompleteTaskCmd extends AbstractCompleteTaskCmd
{
  private static final long serialVersionUID = 1L;
  protected Map<String, Object> variables;
  protected Map<String, Object> transientVariables;
  protected boolean localScope;

  protected String deleteReason;
  
  public CompleteTaskCmd(String taskId, Map<String, Object> variables,String deleteReason)
  {
    super(taskId);
    this.variables = variables;
    this.deleteReason = deleteReason;
  }

  public CompleteTaskCmd(String taskId, Map<String, Object> variables, boolean localScope,String deleteReason) {
    this(taskId, variables,deleteReason);
    this.localScope = localScope;
    this.deleteReason = deleteReason;
  }

  public CompleteTaskCmd(String taskId, Map<String, Object> variables, Map<String, Object> transientVariables,String deleteReason) {
    this(taskId, variables,deleteReason);
    this.transientVariables = transientVariables;
    this.deleteReason = deleteReason;
  }

  protected Void execute(CommandContext commandContext, TaskEntity task)
  {
    if ((task.getProcessDefinitionId() != null) && 
      (Activiti5Util.isActiviti5ProcessDefinitionId(commandContext, task.getProcessDefinitionId()))) {
      Activiti5CompatibilityHandler activiti5CompatibilityHandler = Activiti5Util.getActiviti5CompatibilityHandler();
      activiti5CompatibilityHandler.completeTask(task, this.variables, this.localScope);
      return null;
    }

    if (this.variables != null) {
      if (this.localScope)
        task.setVariablesLocal(this.variables);
      else if (task.getExecutionId() != null)
        task.setExecutionVariables(this.variables);
      else {
        task.setVariables(this.variables);
      }
    }

    if (this.transientVariables != null) {
      if (this.localScope)
        task.setTransientVariablesLocal(this.transientVariables);
      else {
        task.setTransientVariables(this.transientVariables);
      }
    }

    executeTaskComplete(commandContext, task, this.variables, this.localScope, this.deleteReason);
    return null;
  }

  protected String getSuspendedTaskException()
  {
    return "Cannot complete a suspended task";
  }
}