package org.activiti.engine.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.AddCommentCmd;
import org.activiti.engine.impl.cmd.AddIdentityLinkCmd;
import org.activiti.engine.impl.cmd.ClaimTaskCmd;
import org.activiti.engine.impl.cmd.CompleteTaskCmd;
import org.activiti.engine.impl.cmd.CreateAttachmentCmd;
import org.activiti.engine.impl.cmd.DelegateTaskCmd;
import org.activiti.engine.impl.cmd.DeleteAttachmentCmd;
import org.activiti.engine.impl.cmd.DeleteCommentCmd;
import org.activiti.engine.impl.cmd.DeleteIdentityLinkCmd;
import org.activiti.engine.impl.cmd.DeleteTaskCmd;
import org.activiti.engine.impl.cmd.GetAttachmentCmd;
import org.activiti.engine.impl.cmd.GetAttachmentContentCmd;
import org.activiti.engine.impl.cmd.GetCommentCmd;
import org.activiti.engine.impl.cmd.GetIdentityLinksForTaskCmd;
import org.activiti.engine.impl.cmd.GetProcessInstanceAttachmentsCmd;
import org.activiti.engine.impl.cmd.GetProcessInstanceCommentsCmd;
import org.activiti.engine.impl.cmd.GetSubTasksCmd;
import org.activiti.engine.impl.cmd.GetTaskAttachmentsCmd;
import org.activiti.engine.impl.cmd.GetTaskCommentsByTypeCmd;
import org.activiti.engine.impl.cmd.GetTaskCommentsCmd;
import org.activiti.engine.impl.cmd.GetTaskDataObjectCmd;
import org.activiti.engine.impl.cmd.GetTaskDataObjectsCmd;
import org.activiti.engine.impl.cmd.GetTaskEventCmd;
import org.activiti.engine.impl.cmd.GetTaskEventsCmd;
import org.activiti.engine.impl.cmd.GetTaskVariableCmd;
import org.activiti.engine.impl.cmd.GetTaskVariableInstanceCmd;
import org.activiti.engine.impl.cmd.GetTaskVariableInstancesCmd;
import org.activiti.engine.impl.cmd.GetTaskVariablesCmd;
import org.activiti.engine.impl.cmd.GetTasksLocalVariablesCmd;
import org.activiti.engine.impl.cmd.GetTypeCommentsCmd;
import org.activiti.engine.impl.cmd.HasTaskVariableCmd;
import org.activiti.engine.impl.cmd.NewTaskCmd;
import org.activiti.engine.impl.cmd.RemoveTaskVariablesCmd;
import org.activiti.engine.impl.cmd.ResolveTaskCmd;
import org.activiti.engine.impl.cmd.SaveAttachmentCmd;
import org.activiti.engine.impl.cmd.SaveTaskCmd;
import org.activiti.engine.impl.cmd.SetTaskDueDateCmd;
import org.activiti.engine.impl.cmd.SetTaskPriorityCmd;
import org.activiti.engine.impl.cmd.SetTaskVariablesCmd;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.runtime.DataObject;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

public class TaskServiceImpl extends ServiceImpl
  implements TaskService
{
  public TaskServiceImpl()
  {
  }

  public TaskServiceImpl(ProcessEngineConfigurationImpl processEngineConfiguration)
  {
    super(processEngineConfiguration);
  }

  public Task newTask() {
    return newTask(null);
  }

  public Task newTask(String taskId) {
    return (Task)this.commandExecutor.execute(new NewTaskCmd(taskId));
  }

  public void saveTask(Task task) {
    this.commandExecutor.execute(new SaveTaskCmd(task));
  }

  public void deleteTask(String taskId) {
    this.commandExecutor.execute(new DeleteTaskCmd(taskId, null, false));
  }

  public void deleteTasks(Collection<String> taskIds) {
    this.commandExecutor.execute(new DeleteTaskCmd(taskIds, null, false));
  }

  public void deleteTask(String taskId, boolean cascade) {
    this.commandExecutor.execute(new DeleteTaskCmd(taskId, null, cascade));
  }

  public void deleteTasks(Collection<String> taskIds, boolean cascade) {
    this.commandExecutor.execute(new DeleteTaskCmd(taskIds, null, cascade));
  }

  public void deleteTask(String taskId, String deleteReason)
  {
    this.commandExecutor.execute(new DeleteTaskCmd(taskId, deleteReason, false));
  }

  public void deleteTasks(Collection<String> taskIds, String deleteReason)
  {
    this.commandExecutor.execute(new DeleteTaskCmd(taskIds, deleteReason, false));
  }

  public void setAssignee(String taskId, String userId) {
    this.commandExecutor.execute(new AddIdentityLinkCmd(taskId, userId, AddIdentityLinkCmd.IDENTITY_USER, "assignee"));
  }

  public void setOwner(String taskId, String userId) {
    this.commandExecutor.execute(new AddIdentityLinkCmd(taskId, userId, AddIdentityLinkCmd.IDENTITY_USER, "owner"));
  }

  public void addCandidateUser(String taskId, String userId) {
    this.commandExecutor.execute(new AddIdentityLinkCmd(taskId, userId, AddIdentityLinkCmd.IDENTITY_USER, "candidate"));
  }

  public void addCandidateGroup(String taskId, String groupId) {
    this.commandExecutor.execute(new AddIdentityLinkCmd(taskId, groupId, AddIdentityLinkCmd.IDENTITY_GROUP, "candidate"));
  }

  public void addUserIdentityLink(String taskId, String userId, String identityLinkType) {
    this.commandExecutor.execute(new AddIdentityLinkCmd(taskId, userId, AddIdentityLinkCmd.IDENTITY_USER, identityLinkType));
  }

  public void addGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
    this.commandExecutor.execute(new AddIdentityLinkCmd(taskId, groupId, AddIdentityLinkCmd.IDENTITY_GROUP, identityLinkType));
  }

  public void deleteCandidateGroup(String taskId, String groupId) {
    this.commandExecutor.execute(new DeleteIdentityLinkCmd(taskId, null, groupId, "candidate"));
  }

  public void deleteCandidateUser(String taskId, String userId) {
    this.commandExecutor.execute(new DeleteIdentityLinkCmd(taskId, userId, null, "candidate"));
  }

  public void deleteGroupIdentityLink(String taskId, String groupId, String identityLinkType) {
    this.commandExecutor.execute(new DeleteIdentityLinkCmd(taskId, null, groupId, identityLinkType));
  }

  public void deleteUserIdentityLink(String taskId, String userId, String identityLinkType) {
    this.commandExecutor.execute(new DeleteIdentityLinkCmd(taskId, userId, null, identityLinkType));
  }

  public List<IdentityLink> getIdentityLinksForTask(String taskId) {
    return (List)this.commandExecutor.execute(new GetIdentityLinksForTaskCmd(taskId));
  }

  public void claim(String taskId, String userId) {
    this.commandExecutor.execute(new ClaimTaskCmd(taskId, userId));
  }

  public void unclaim(String taskId) {
    this.commandExecutor.execute(new ClaimTaskCmd(taskId, null));
  }

  public void complete(String taskId,String deleteReason) {
    this.commandExecutor.execute(new CompleteTaskCmd(taskId, null, deleteReason));
  }

  public void complete(String taskId, Map<String, Object> variables,String deleteReason) {
    this.commandExecutor.execute(new CompleteTaskCmd(taskId, variables, deleteReason));
  }

  public void complete(String taskId, Map<String, Object> variables, Map<String, Object> transientVariables,String deleteReason)
  {
    this.commandExecutor.execute(new CompleteTaskCmd(taskId, variables, transientVariables, deleteReason));
  }

  public void complete(String taskId, Map<String, Object> variables, boolean localScope,String deleteReason) {
    this.commandExecutor.execute(new CompleteTaskCmd(taskId, variables, localScope, deleteReason));
  }

  public void delegateTask(String taskId, String userId) {
    this.commandExecutor.execute(new DelegateTaskCmd(taskId, userId));
  }

  public void resolveTask(String taskId) {
    this.commandExecutor.execute(new ResolveTaskCmd(taskId, null));
  }

  public void resolveTask(String taskId, Map<String, Object> variables) {
    this.commandExecutor.execute(new ResolveTaskCmd(taskId, variables));
  }

  public void resolveTask(String taskId, Map<String, Object> variables, Map<String, Object> transientVariables)
  {
    this.commandExecutor.execute(new ResolveTaskCmd(taskId, variables, transientVariables));
  }

  public void setPriority(String taskId, int priority) {
    this.commandExecutor.execute(new SetTaskPriorityCmd(taskId, priority));
  }

  public void setDueDate(String taskId, Date dueDate) {
    this.commandExecutor.execute(new SetTaskDueDateCmd(taskId, dueDate));
  }

  public TaskQuery createTaskQuery() {
    return new TaskQueryImpl(this.commandExecutor, this.processEngineConfiguration.getDatabaseType());
  }

  public NativeTaskQuery createNativeTaskQuery() {
    return new NativeTaskQueryImpl(this.commandExecutor);
  }

  public Map<String, Object> getVariables(String taskId) {
    return (Map)this.commandExecutor.execute(new GetTaskVariablesCmd(taskId, null, false));
  }

  public Map<String, Object> getVariablesLocal(String taskId) {
    return (Map)this.commandExecutor.execute(new GetTaskVariablesCmd(taskId, null, true));
  }

  public Map<String, Object> getVariables(String taskId, Collection<String> variableNames) {
    return (Map)this.commandExecutor.execute(new GetTaskVariablesCmd(taskId, variableNames, false));
  }

  public Map<String, Object> getVariablesLocal(String taskId, Collection<String> variableNames) {
    return (Map)this.commandExecutor.execute(new GetTaskVariablesCmd(taskId, variableNames, true));
  }

  public Object getVariable(String taskId, String variableName) {
    return this.commandExecutor.execute(new GetTaskVariableCmd(taskId, variableName, false));
  }

  public <T> T getVariable(String taskId, String variableName, Class<T> variableClass)
  {
    return variableClass.cast(getVariable(taskId, variableName));
  }

  public boolean hasVariable(String taskId, String variableName) {
    return ((Boolean)this.commandExecutor.execute(new HasTaskVariableCmd(taskId, variableName, false))).booleanValue();
  }

  public Object getVariableLocal(String taskId, String variableName) {
    return this.commandExecutor.execute(new GetTaskVariableCmd(taskId, variableName, true));
  }

  public <T> T getVariableLocal(String taskId, String variableName, Class<T> variableClass)
  {
    return variableClass.cast(getVariableLocal(taskId, variableName));
  }

  public List<VariableInstance> getVariableInstancesLocalByTaskIds(Set<String> taskIds) {
    return (List)this.commandExecutor.execute(new GetTasksLocalVariablesCmd(taskIds));
  }

  public boolean hasVariableLocal(String taskId, String variableName) {
    return ((Boolean)this.commandExecutor.execute(new HasTaskVariableCmd(taskId, variableName, true))).booleanValue();
  }

  public void setVariable(String taskId, String variableName, Object value) {
    if (variableName == null) {
      throw new ActivitiIllegalArgumentException("variableName is null");
    }
    Map variables = new HashMap();
    variables.put(variableName, value);
    this.commandExecutor.execute(new SetTaskVariablesCmd(taskId, variables, false));
  }

  public void setVariableLocal(String taskId, String variableName, Object value) {
    if (variableName == null) {
      throw new ActivitiIllegalArgumentException("variableName is null");
    }
    Map variables = new HashMap();
    variables.put(variableName, value);
    this.commandExecutor.execute(new SetTaskVariablesCmd(taskId, variables, true));
  }

  public void setVariables(String taskId, Map<String, ? extends Object> variables) {
    this.commandExecutor.execute(new SetTaskVariablesCmd(taskId, variables, false));
  }

  public void setVariablesLocal(String taskId, Map<String, ? extends Object> variables) {
    this.commandExecutor.execute(new SetTaskVariablesCmd(taskId, variables, true));
  }

  public void removeVariable(String taskId, String variableName) {
    Collection variableNames = new ArrayList();
    variableNames.add(variableName);
    this.commandExecutor.execute(new RemoveTaskVariablesCmd(taskId, variableNames, false));
  }

  public void removeVariableLocal(String taskId, String variableName) {
    Collection variableNames = new ArrayList(1);
    variableNames.add(variableName);
    this.commandExecutor.execute(new RemoveTaskVariablesCmd(taskId, variableNames, true));
  }

  public void removeVariables(String taskId, Collection<String> variableNames) {
    this.commandExecutor.execute(new RemoveTaskVariablesCmd(taskId, variableNames, false));
  }

  public void removeVariablesLocal(String taskId, Collection<String> variableNames) {
    this.commandExecutor.execute(new RemoveTaskVariablesCmd(taskId, variableNames, true));
  }

  public Comment addComment(String taskId, String processInstance, String message) {
    return (Comment)this.commandExecutor.execute(new AddCommentCmd(taskId, processInstance, message));
  }

  public Comment addComment(String taskId, String processInstance, String type, String message) {
    return (Comment)this.commandExecutor.execute(new AddCommentCmd(taskId, processInstance, type, message));
  }

  public Comment getComment(String commentId)
  {
    return (Comment)this.commandExecutor.execute(new GetCommentCmd(commentId));
  }

  public Event getEvent(String eventId)
  {
    return (Event)this.commandExecutor.execute(new GetTaskEventCmd(eventId));
  }

  public List<Comment> getTaskComments(String taskId) {
    return (List)this.commandExecutor.execute(new GetTaskCommentsCmd(taskId));
  }

  public List<Comment> getTaskComments(String taskId, String type) {
    return (List)this.commandExecutor.execute(new GetTaskCommentsByTypeCmd(taskId, type));
  }

  public List<Comment> getCommentsByType(String type) {
    return (List)this.commandExecutor.execute(new GetTypeCommentsCmd(type));
  }

  public List<Event> getTaskEvents(String taskId) {
    return (List)this.commandExecutor.execute(new GetTaskEventsCmd(taskId));
  }

  public List<Comment> getProcessInstanceComments(String processInstanceId) {
    return (List)this.commandExecutor.execute(new GetProcessInstanceCommentsCmd(processInstanceId));
  }

  public List<Comment> getProcessInstanceComments(String processInstanceId, String type) {
    return (List)this.commandExecutor.execute(new GetProcessInstanceCommentsCmd(processInstanceId, type));
  }

  public Attachment createAttachment(String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, InputStream content) {
    return (Attachment)this.commandExecutor.execute(new CreateAttachmentCmd(attachmentType, taskId, processInstanceId, attachmentName, attachmentDescription, content, null));
  }

  public Attachment createAttachment(String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url) {
    return (Attachment)this.commandExecutor.execute(new CreateAttachmentCmd(attachmentType, taskId, processInstanceId, attachmentName, attachmentDescription, null, url));
  }

  public InputStream getAttachmentContent(String attachmentId) {
    return (InputStream)this.commandExecutor.execute(new GetAttachmentContentCmd(attachmentId));
  }

  public void deleteAttachment(String attachmentId) {
    this.commandExecutor.execute(new DeleteAttachmentCmd(attachmentId));
  }

  public void deleteComments(String taskId, String processInstanceId) {
    this.commandExecutor.execute(new DeleteCommentCmd(taskId, processInstanceId, null));
  }

  public void deleteComment(String commentId)
  {
    this.commandExecutor.execute(new DeleteCommentCmd(null, null, commentId));
  }

  public Attachment getAttachment(String attachmentId) {
    return (Attachment)this.commandExecutor.execute(new GetAttachmentCmd(attachmentId));
  }

  public List<Attachment> getTaskAttachments(String taskId)
  {
    return (List)this.commandExecutor.execute(new GetTaskAttachmentsCmd(taskId));
  }

  public List<Attachment> getProcessInstanceAttachments(String processInstanceId)
  {
    return (List)this.commandExecutor.execute(new GetProcessInstanceAttachmentsCmd(processInstanceId));
  }

  public void saveAttachment(Attachment attachment) {
    this.commandExecutor.execute(new SaveAttachmentCmd(attachment));
  }

  public List<Task> getSubTasks(String parentTaskId) {
    return (List)this.commandExecutor.execute(new GetSubTasksCmd(parentTaskId));
  }

  public VariableInstance getVariableInstance(String taskId, String variableName)
  {
    return (VariableInstance)this.commandExecutor.execute(new GetTaskVariableInstanceCmd(taskId, variableName, false));
  }

  public VariableInstance getVariableInstanceLocal(String taskId, String variableName)
  {
    return (VariableInstance)this.commandExecutor.execute(new GetTaskVariableInstanceCmd(taskId, variableName, true));
  }

  public Map<String, VariableInstance> getVariableInstances(String taskId)
  {
    return (Map)this.commandExecutor.execute(new GetTaskVariableInstancesCmd(taskId, null, false));
  }

  public Map<String, VariableInstance> getVariableInstances(String taskId, Collection<String> variableNames)
  {
    return (Map)this.commandExecutor.execute(new GetTaskVariableInstancesCmd(taskId, variableNames, false));
  }

  public Map<String, VariableInstance> getVariableInstancesLocal(String taskId)
  {
    return (Map)this.commandExecutor.execute(new GetTaskVariableInstancesCmd(taskId, null, true));
  }

  public Map<String, VariableInstance> getVariableInstancesLocal(String taskId, Collection<String> variableNames)
  {
    return (Map)this.commandExecutor.execute(new GetTaskVariableInstancesCmd(taskId, variableNames, true));
  }

  public Map<String, DataObject> getDataObjects(String taskId)
  {
    return (Map)this.commandExecutor.execute(new GetTaskDataObjectsCmd(taskId, null));
  }

  public Map<String, DataObject> getDataObjects(String taskId, String locale, boolean withLocalizationFallback)
  {
    return (Map)this.commandExecutor.execute(new GetTaskDataObjectsCmd(taskId, null, locale, withLocalizationFallback));
  }

  public Map<String, DataObject> getDataObjects(String taskId, Collection<String> dataObjectNames)
  {
    return (Map)this.commandExecutor.execute(new GetTaskDataObjectsCmd(taskId, dataObjectNames));
  }

  public Map<String, DataObject> getDataObjects(String taskId, Collection<String> dataObjectNames, String locale, boolean withLocalizationFallback)
  {
    return (Map)this.commandExecutor.execute(new GetTaskDataObjectsCmd(taskId, dataObjectNames, locale, withLocalizationFallback));
  }

  public DataObject getDataObject(String taskId, String dataObject)
  {
    return (DataObject)this.commandExecutor.execute(new GetTaskDataObjectCmd(taskId, dataObject));
  }

  public DataObject getDataObject(String taskId, String dataObjectName, String locale, boolean withLocalizationFallback)
  {
    return (DataObject)this.commandExecutor.execute(new GetTaskDataObjectCmd(taskId, dataObjectName, locale, withLocalizationFallback));
  }
}