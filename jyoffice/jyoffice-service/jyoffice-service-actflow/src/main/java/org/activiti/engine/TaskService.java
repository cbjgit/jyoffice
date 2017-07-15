package org.activiti.engine;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.runtime.DataObject;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

public abstract interface TaskService
{
  public abstract Task newTask();

  public abstract Task newTask(String paramString);

  public abstract void saveTask(Task paramTask);

  public abstract void deleteTask(String paramString);

  public abstract void deleteTasks(Collection<String> paramCollection);

  public abstract void deleteTask(String paramString, boolean paramBoolean);

  public abstract void deleteTasks(Collection<String> paramCollection, boolean paramBoolean);

  public abstract void deleteTask(String paramString1, String paramString2);

  public abstract void deleteTasks(Collection<String> paramCollection, String paramString);

  public abstract void claim(String paramString1, String paramString2);

  public abstract void unclaim(String paramString);

  public abstract void complete(String paramString, String deleteReaso);

  public abstract void delegateTask(String paramString1, String paramString2);

  public abstract void resolveTask(String paramString);

  public abstract void resolveTask(String paramString, Map<String, Object> paramMap);

  public abstract void resolveTask(String paramString, Map<String, Object> paramMap1, Map<String, Object> paramMap2);

  public abstract void complete(String paramString, Map<String, Object> paramMap, String deleteReaso);

  public abstract void complete(String paramString, Map<String, Object> paramMap1, Map<String, Object> paramMap2, String deleteReaso);

  public abstract void complete(String paramString, Map<String, Object> paramMap, boolean paramBoolean, String deleteReaso);

  public abstract void setAssignee(String paramString1, String paramString2);

  public abstract void setOwner(String paramString1, String paramString2);

  public abstract List<IdentityLink> getIdentityLinksForTask(String paramString);

  public abstract void addCandidateUser(String paramString1, String paramString2);

  public abstract void addCandidateGroup(String paramString1, String paramString2);

  public abstract void addUserIdentityLink(String paramString1, String paramString2, String paramString3);

  public abstract void addGroupIdentityLink(String paramString1, String paramString2, String paramString3);

  public abstract void deleteCandidateUser(String paramString1, String paramString2);

  public abstract void deleteCandidateGroup(String paramString1, String paramString2);

  public abstract void deleteUserIdentityLink(String paramString1, String paramString2, String paramString3);

  public abstract void deleteGroupIdentityLink(String paramString1, String paramString2, String paramString3);

  public abstract void setPriority(String paramString, int paramInt);

  public abstract void setDueDate(String paramString, Date paramDate);

  public abstract TaskQuery createTaskQuery();

  public abstract NativeTaskQuery createNativeTaskQuery();

  public abstract void setVariable(String paramString1, String paramString2, Object paramObject);

  public abstract void setVariables(String paramString, Map<String, ? extends Object> paramMap);

  public abstract void setVariableLocal(String paramString1, String paramString2, Object paramObject);

  public abstract void setVariablesLocal(String paramString, Map<String, ? extends Object> paramMap);

  public abstract Object getVariable(String paramString1, String paramString2);

  public abstract <T> T getVariable(String paramString1, String paramString2, Class<T> paramClass);

  public abstract VariableInstance getVariableInstance(String paramString1, String paramString2);

  public abstract boolean hasVariable(String paramString1, String paramString2);

  public abstract Object getVariableLocal(String paramString1, String paramString2);

  public abstract <T> T getVariableLocal(String paramString1, String paramString2, Class<T> paramClass);

  public abstract VariableInstance getVariableInstanceLocal(String paramString1, String paramString2);

  public abstract boolean hasVariableLocal(String paramString1, String paramString2);

  public abstract Map<String, Object> getVariables(String paramString);

  public abstract Map<String, VariableInstance> getVariableInstances(String paramString);

  public abstract Map<String, VariableInstance> getVariableInstances(String paramString, Collection<String> paramCollection);

  public abstract Map<String, Object> getVariablesLocal(String paramString);

  public abstract Map<String, Object> getVariables(String paramString, Collection<String> paramCollection);

  public abstract Map<String, Object> getVariablesLocal(String paramString, Collection<String> paramCollection);

  public abstract List<VariableInstance> getVariableInstancesLocalByTaskIds(Set<String> paramSet);

  public abstract Map<String, VariableInstance> getVariableInstancesLocal(String paramString);

  public abstract Map<String, VariableInstance> getVariableInstancesLocal(String paramString, Collection<String> paramCollection);

  public abstract void removeVariable(String paramString1, String paramString2);

  public abstract void removeVariableLocal(String paramString1, String paramString2);

  public abstract void removeVariables(String paramString, Collection<String> paramCollection);

  public abstract void removeVariablesLocal(String paramString, Collection<String> paramCollection);

  public abstract Map<String, DataObject> getDataObjects(String paramString);

  public abstract Map<String, DataObject> getDataObjects(String paramString1, String paramString2, boolean paramBoolean);

  public abstract Map<String, DataObject> getDataObjects(String paramString, Collection<String> paramCollection);

  public abstract Map<String, DataObject> getDataObjects(String paramString1, Collection<String> paramCollection, String paramString2, boolean paramBoolean);

  public abstract DataObject getDataObject(String paramString1, String paramString2);

  public abstract DataObject getDataObject(String paramString1, String paramString2, String paramString3, boolean paramBoolean);

  public abstract Comment addComment(String paramString1, String paramString2, String paramString3);

  public abstract Comment addComment(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract Comment getComment(String paramString);

  public abstract void deleteComments(String paramString1, String paramString2);

  public abstract void deleteComment(String paramString);

  public abstract List<Comment> getTaskComments(String paramString);

  public abstract List<Comment> getTaskComments(String paramString1, String paramString2);

  public abstract List<Comment> getCommentsByType(String paramString);

  public abstract List<Event> getTaskEvents(String paramString);

  public abstract Event getEvent(String paramString);

  public abstract List<Comment> getProcessInstanceComments(String paramString);

  public abstract List<Comment> getProcessInstanceComments(String paramString1, String paramString2);

  public abstract Attachment createAttachment(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, InputStream paramInputStream);

  public abstract Attachment createAttachment(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract void saveAttachment(Attachment paramAttachment);

  public abstract Attachment getAttachment(String paramString);

  public abstract InputStream getAttachmentContent(String paramString);

  public abstract List<Attachment> getTaskAttachments(String paramString);

  public abstract List<Attachment> getProcessInstanceAttachments(String paramString);

  public abstract void deleteAttachment(String paramString);

  public abstract List<Task> getSubTasks(String paramString);
}