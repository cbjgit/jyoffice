
package com.jyoffice.actflow.listeners;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>{@link JyTaskListener}</code>
 * 
 * TODO : 任务监听,流程中每一个任务必须监听此类
 * 
 * @author chenbj
 */
public class JyTaskListener implements TaskListener {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	public void notify(DelegateTask delegatetask){

		String eventName = delegatetask.getEventName();
		
		SqlSession sqlSession = Context.getCommandContext().getDbSqlSession().getSqlSession();
			
		//sqlSession.insert("insert into act_ru_task_path", new HashMap<String, Object>());
		log.info("eventName:"+eventName);
		log.info("assignee:"+delegatetask.getAssignee());
		log.info("executionId:"+delegatetask.getExecutionId());
		
		log.info("instanceId:"+delegatetask.getProcessInstanceId());
		log.info("taskId:"+delegatetask.getId());
		log.info("taskName:"+delegatetask.getName());
		log.info("taskKey:"+delegatetask.getTaskDefinitionKey());

	}

}
