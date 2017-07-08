package com.jyoffice.actflow.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.DelegationState;

public class JumpTaskCmd extends NeedsActiveTaskCmd<Void> {

	private static final long serialVersionUID = 1L;
	protected String destTask;

	public JumpTaskCmd(String taskId, String destTask) {
		super(taskId);
		this.destTask = destTask;
	}

	protected Void execute(CommandContext commandContext, TaskEntity task) {

		if (task.getDelegationState() != null
				&& task.getDelegationState().equals(DelegationState.PENDING)) {
			throw new ActivitiException(
					"A delegated task cannot be completed, but should be resolved instead.");
		}

		commandContext.getProcessEngineConfiguration().getListenerNotificationHelper()
				.executeTaskListeners(task, TaskListener.EVENTNAME_COMPLETE);

		commandContext.getTaskEntityManager().deleteTask(task, null, false, false);

		// Continue process (if not a standalone task)
		/*if (task.getExecutionId() != null) {
			ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(
					task.getExecutionId());
			commandContext.getAgenda().planTriggerExecutionOperation(executionEntity);
		}*/
		return null;
	}

	@Override
	protected String getSuspendedTaskException() {
		return "Cannot complete a suspended task";
	}

}
