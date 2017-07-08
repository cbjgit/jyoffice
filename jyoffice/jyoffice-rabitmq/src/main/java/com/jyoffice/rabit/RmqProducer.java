package com.jyoffice.rabit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RmqProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 发送信息
	 * 
	 * @param msg
	 */
	public void sendMessage(RabbitMessage msg) {
		try {
			System.out.println(rabbitTemplate.getConnectionFactory().getHost());
			System.out.println(rabbitTemplate.getConnectionFactory().getPort());
			// 发送信息
			rabbitTemplate.convertAndSend(msg.getExchange(), msg.getRouteKey(), msg.getObject());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
