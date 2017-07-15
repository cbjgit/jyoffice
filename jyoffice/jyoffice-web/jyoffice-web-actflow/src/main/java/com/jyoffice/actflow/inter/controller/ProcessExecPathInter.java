package com.jyoffice.actflow.inter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyoffice.actflow.inter.response.BaseResponse;
import com.jyoffice.actflow.inter.response.ProcessExecPath;
import com.jyoffice.actflow.model.ActHiActinst;
import com.jyoffice.actflow.service.ActEngineService;

@Controller
@RequestMapping(value = "/process/execpath")
public class ProcessExecPathInter extends BaseInter {

	@Autowired
	ActEngineService actEngineService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@RequestMapping(value = "/{instanceId}", method = RequestMethod.GET)
	public @ResponseBody BaseResponse search(HttpServletRequest request, @PathVariable String instanceId) {

		try {

			BaseResponse response = new BaseResponse(true);

			List<ActHiActinst> list = actEngineService.getProcessExecPath(instanceId);
			List<ProcessExecPath> data = new ArrayList<ProcessExecPath>();
			for (ActHiActinst act : list) {
				ProcessExecPath path = new ProcessExecPath();
				if(act.getActName() == null){
					path.setActName(act.getActId());
				}else{
					path.setActName(act.getActName());
				}
				path.setAssignee(act.getAssignee());
				if("complete".equals(act.getDeleteReason())){
					path.setDeleteReason("通过");
				}else if("reject".equals(act.getDeleteReason())){
					path.setDeleteReason("退回");
				}else{
					path.setDeleteReason(act.getDeleteReason());
				}
				if(act.getEndTime() != null){
					path.setEndTime(sdf.format(act.getEndTime()));
				}
				path.setStartTime(sdf.format(act.getStartTime()));
				data.add(path);
			}
			response.setData(data);

			return response;

		} catch (Exception e) {
			log.info("", e);
			return BaseResponse.errResponse(e.getMessage(), "系统出错请联系管理员");
		}
	}

}
