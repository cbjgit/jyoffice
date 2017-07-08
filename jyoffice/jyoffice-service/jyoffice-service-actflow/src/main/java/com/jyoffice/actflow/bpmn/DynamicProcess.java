package com.jyoffice.actflow.bpmn;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.springframework.util.FileCopyUtils;

import com.jyoffice.actflow.model.ActDefNode;
import com.jyoffice.actflow.model.ActDefProcess;
import com.jyoffice.actflow.model.ActDefSequence;

public class DynamicProcess {

	public static BpmnModel createBpmn(ActDefProcess actprocess, List<ActDefNode> nodeList,
			List<ActDefSequence> seqList) throws Exception {

		Process process = new Process();
		process.setId(actprocess.getProcessKey());
		process.setName(actprocess.getProcessName());

		process.addFlowElement(createStartEvent(seqList));
		for (ActDefNode node : nodeList) {
			if (NodeType.TYPE_USERTASK.equals(node.getNodeTypes())) {
				process.addFlowElement(createUserTask(node, seqList));

			} else if (NodeType.TYPE_EXCLUSIVE_GATEWAY.equals(node.getNodeTypes())) {
				process.addFlowElement(createExclusiveGateway(node, seqList));
				
			} else if (NodeType.TYPE_PARALLEL_GATEWAY.equals(node.getNodeTypes())) {
				process.addFlowElement(createParallelGateway(node, seqList));
			} else {

			}
		}
		process.addFlowElement(createEndEvent());
		for (ActDefSequence seq : seqList) {
			process.addFlowElement(createSequenceFlow(seq));
		}

		BpmnModel bpmnModel = new BpmnModel();
		bpmnModel.addProcess(process);

		String msg = validate(bpmnModel);

		if (msg == null || msg.length() == 0) {
			autoLayout(bpmnModel);

			return bpmnModel;
		} else {
			throw new Exception(msg);
		}
	}

	private static void autoLayout(BpmnModel bpmnModel) {
		BpmnAutoLayout layout = new BpmnAutoLayout(bpmnModel);
		layout.setTaskWidth(160);
		layout.execute();
	}

	public static void convertToDiagram(BpmnModel bpmnModel, OutputStream fos) {

		ProcessDiagramGenerator diagram = new DefaultProcessDiagramGenerator();
		InputStream inpt = diagram.generateDiagram(bpmnModel, "png", "宋体", "宋体", "宋体", null);
		try {
			FileCopyUtils.copy(inpt, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String convertToXML(BpmnModel bpmnModel) {
		if (bpmnModel == null)
			return "";

		String xml = new String(new BpmnXMLConverter().convertToXML(bpmnModel));
		return xml;
	}

	private static String validate(BpmnModel bpmnModel) {
		ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
		ProcessValidator defaultProcessValidator = processValidatorFactory
				.createDefaultProcessValidator();
		// 验证失败信息的封装ValidationError
		List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);
		StringBuffer bf = new StringBuffer();
		for (ValidationError v : validate) {
			bf.append("ActivityId：" + v.getActivityId() + ",Description:" + v.getDefaultDescription());
		}

		return bf.toString();
	}

	protected static UserTask createUserTask(ActDefNode node, List<ActDefSequence> seqList) {
		UserTask userTask = new UserTask();
		userTask.setId(node.getNodeId());
		userTask.setName(node.getNodeNames());
		userTask.setAssignee("${ag_" + node.getId() + "}");
		if (node.getMulti() == 1) {
			MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
			String condition = "${nrOfInstances == approvedNum || rejectNum > 0}";
			if (node.getCompletionCondition() == 2) {
				condition = "${approvedNum / nrOfInstances * 100 > " + node.getRate() + "}";
			}
			loopCharacteristics.setCompletionCondition(condition);
			loopCharacteristics.setSequential(false);
			loopCharacteristics.setElementVariable("ag_" + node.getId());
			loopCharacteristics.setInputDataItem("agList_" + node.getId());
			userTask.setLoopCharacteristics(loopCharacteristics);
		}

		List<ActivitiListener> executionListeners = new ArrayList<ActivitiListener>();
		ActivitiListener listener = new ActivitiListener();
		listener.setEvent("create");
		listener.setImplementation("com.jyoffice.actflow.listeners.JyTaskListener");
		listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
		executionListeners.add(listener);

		listener = new ActivitiListener();
		listener.setEvent("complete");
		listener.setImplementation("com.jyoffice.actflow.listeners.JyTaskListener");
		listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
		executionListeners.add(listener);
		userTask.setTaskListeners(executionListeners);

		userTask.setOutgoingFlows(getOutFlow(node.getNodeId(), seqList));
		return userTask;
	}

	protected static ExclusiveGateway createExclusiveGateway(ActDefNode node,
			List<ActDefSequence> seqList) {
		ExclusiveGateway gateway = new ExclusiveGateway();
		gateway.setId(node.getNodeId());
		gateway.setName(node.getNodeNames());
		gateway.setAsynchronous(false);
		gateway.setOutgoingFlows(getOutFlow(node.getNodeId(), seqList));
		return gateway;
	}

	protected static ParallelGateway createParallelGateway(ActDefNode node, List<ActDefSequence> seqList) {
		ParallelGateway gateway = new ParallelGateway();
		gateway.setId(node.getNodeId());
		gateway.setName(node.getNodeNames());
		gateway.setAsynchronous(false);
		gateway.setOutgoingFlows(getOutFlow(node.getNodeId(), seqList));
		return gateway;
	}

	private static List<SequenceFlow> getOutFlow(String nodeId, List<ActDefSequence> seqList) {
		List<SequenceFlow> list = new ArrayList<SequenceFlow>();
		for (ActDefSequence seq : seqList) {
			if (seq.getNodeId().equals(nodeId)) {
				SequenceFlow f = new SequenceFlow(seq.getNodeId(), seq.getToNodeId());
				f.setId("s" + seq.getId());
				f.setConditionExpression(seq.getConditionExpression());
				list.add(f);
			}
		}
		return list;
	}

	protected static SequenceFlow createSequenceFlow(ActDefSequence seq) {
		SequenceFlow flow = new SequenceFlow();
		flow.setSourceRef(seq.getNodeId());
		flow.setTargetRef(seq.getToNodeId());
		flow.setId("s" + seq.getId());
		flow.setConditionExpression(seq.getConditionExpression());
		return flow;
	}

	protected static StartEvent createStartEvent(List<ActDefSequence> seqList) {
		StartEvent startEvent = new StartEvent();
		startEvent.setId(NodeType.TYPE_STARTEVENT);
		startEvent.setOutgoingFlows(getOutFlow(NodeType.TYPE_STARTEVENT, seqList));
		return startEvent;
	}

	protected static EndEvent createEndEvent() {
		EndEvent endEvent = new EndEvent();
		endEvent.setId(NodeType.TYPE_ENDEVENT);
		return endEvent;
	}

}
