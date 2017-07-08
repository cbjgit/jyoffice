package com.jyoffice.rabit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueOneLitener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		Map map = (HashMap) SerializationUtils.deserialize(message.getBody());
		System.out.println("Message Number " + map.toString() + " received.");
	}
}
