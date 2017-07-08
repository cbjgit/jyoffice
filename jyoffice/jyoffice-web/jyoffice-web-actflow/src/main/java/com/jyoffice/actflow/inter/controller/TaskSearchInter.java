package com.jyoffice.actflow.inter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyoffice.actflow.inter.response.BaseResponse;
import com.jyoffice.actflow.inter.response.TaskSearchResponse;
import com.jyoffice.actflow.service.ActEngineService;

@Controller
@RequestMapping(value = "/task/search")
public class TaskSearchInter extends BaseInter {

	@Autowired
	ActEngineService actEngineService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/run/{userId}/{processKey}/{periodStart}/{periodend}", method = RequestMethod.GET)
	public @ResponseBody BaseResponse runtask(HttpServletRequest request, @PathVariable String userId,
			@PathVariable String processKey, @PathVariable long periodStart, @PathVariable long periodend) {

		try {
			processKey = "-".equals(processKey) ? "" : processKey;
			List<Map> taskList = actEngineService.getTaskList(userId, processKey, periodStart,
					periodend);
			List<TaskSearchResponse.RspTask> responseList = new ArrayList<TaskSearchResponse.RspTask>();
			TaskSearchResponse response = new TaskSearchResponse();
			response.setSuccess(true);

			for (Map task : taskList) {
				TaskSearchResponse.RspTask rspTask = response.new RspTask();
				rspTask.setTaskId(String.valueOf(task.get("id")));
				rspTask.setBusKey(String.valueOf(task.get("busKey")));
				rspTask.setTaskName(String.valueOf(task.get("name")));
				rspTask.setTaskKey(String.valueOf(task.get("taskDefKey")));
				rspTask.setUrl(String.valueOf(task.get("taskUrl")));
				responseList.add(rspTask);
			}
			response.setTask(responseList);

			return response;

		} catch (Exception e) {
			log.info("", e);
			return BaseResponse.errResponse(e.getMessage(), "系统出错请联系管理员");
		}
	}
}
