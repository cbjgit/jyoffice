package com.jyoffice.actflow.inter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyoffice.actflow.inter.request.TaskCommentRequest;
import com.jyoffice.actflow.inter.response.BaseResponse;
import com.jyoffice.actflow.inter.response.TaskCommentResponse;
import com.jyoffice.actflow.service.ActEngineService;
import com.jyoffice.util.StringUtil;

@Controller
@RequestMapping(value = "/task/comment")
public class TaskCommentInter extends BaseInter {

	@Autowired
	ActEngineService actEngineService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody BaseResponse saveComment(HttpServletRequest request, @RequestBody String body) {

		log.info("body:" + body);

		TaskCommentRequest spRequest = jsonToBean(body, TaskCommentRequest.class);
		BaseResponse response = validate(spRequest);
		if (response != null) {
			return response;
		}

		if (this.validSign(toMap(spRequest), spRequest.getSignMsg())) {
			log.info("验签成功");

			actEngineService.saveComment(spRequest.getTaskId(), spRequest.getUserId(),
					spRequest.getComment());
			return BaseResponse.successResponse();

		} else {
			log.info("验签失败");
			return BaseResponse.errResponse("签名错误", "请检查签名字符串是否正确");
		}
	}

	@RequestMapping(value = "/get/{instanceId}", method = RequestMethod.GET)
	public @ResponseBody BaseResponse getComment(HttpServletRequest request,
			@PathVariable String instanceId) {

		try {
			List<Comment> commentList = actEngineService.getProcessComment(instanceId);
			List<TaskCommentResponse.RspComment> responseList = new ArrayList<TaskCommentResponse.RspComment>();
			TaskCommentResponse response = new TaskCommentResponse();
			response.setSuccess(true);
			
			for (Comment comment : commentList) {
				TaskCommentResponse.RspComment rspComment = response.new RspComment();
				rspComment.setTaskId(comment.getTaskId());
				rspComment.setUserId(comment.getUserId());
				rspComment.setTime(sdf.format(comment.getTime()));
				rspComment.setMessage(comment.getFullMessage());
				responseList.add(rspComment);
			}
			response.setComment(responseList);
			return response;

		} catch (Exception e) {
			log.info("", e);
			return BaseResponse.errResponse(e.getMessage(), "系统出错请联系管理员");
		}
	}

	private TreeMap<String, Object> toMap(TaskCommentRequest tcRequest) {

		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put("taskId", tcRequest.getTaskId());
		map.put("userId", tcRequest.getUserId());
		map.put("comment", tcRequest.getComment());
		map.put("timestamp", tcRequest.getTimestamp());
		return map;
	}

	private BaseResponse validate(TaskCommentRequest stRequest) {
		if (stRequest == null) {
			return BaseResponse.errResponse("数据格式错误", "请检查body值是否正确");
		}

		if (StringUtil.isBlank(stRequest.getTaskId())) {
			return BaseResponse.errResponse("任务ID为空", "请传入TaskId");

		} else if (StringUtil.isBlank(stRequest.getUserId())) {
			return BaseResponse.errResponse("用户为空", "请传入用户UserId");

		} else if (StringUtil.isBlank(stRequest.getTimestamp())
				|| stRequest.getTimestamp().length() != 10) {
			return BaseResponse.errResponse("时间戳不正确", "请传入10位的时间戳，精确到秒");

		} else if (StringUtil.isBlank(stRequest.getSignMsg())) {
			return BaseResponse.errResponse("签名内容为空", "请传入签名字符串");

		} else if (StringUtil.isBlank(stRequest.getComment())) {
			return BaseResponse.errResponse("审批意见为空", "请传入审批意见");

		}

		return null;
	}
}
