package com.jyoffice.actflow.listeners;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>{@link JyTaskListener}</code>
 * 
 * TODO : 任务监听
 * 
 * @author chenbj
 */
public class JyTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	public void notify(DelegateTask delegatetask) {

		//在这里可以发送邮件通知
		String eventName = delegatetask.getEventName();
		if ("create".equals(eventName)) {
			log.error("分配任务" + delegatetask.getName() + "/" + delegatetask.getTaskDefinitionKey() + "给"
					+ delegatetask.getAssignee());
		} else {
			log.error(delegatetask.getAssignee() + "完成任务" + delegatetask.getName() + "/"
					+ delegatetask.getTaskDefinitionKey());
		}
	}
}
