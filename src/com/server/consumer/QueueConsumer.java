package com.server.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.server.endpoint.EndPoint;

public class QueueConsumer extends EndPoint implements Runnable, Consumer{
	
	public QueueConsumer(String endPointName, String exchangeName, String routingKey) throws IOException, TimeoutException{
		super(endPointName,exchangeName,routingKey);		
	}
	
	public void run() {
		try {
			//start consuming messages. Auto acknowledge messages.
			channel.basicConsume(endPointName, true,this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer "+consumerTag +" registered");		
	}

	/**
	 * Called when new message is available.
	 */
	public void handleDelivery(String consumerTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		//Map map = (HashMap)SerializationUtils.deserialize(body);
		String map = (String)SerializationUtils.deserialize(body);
	    System.out.println("Message Number :"+ map + ", received.");
		
	}

	public void handleCancel(String consumerTag) {}
	public void handleCancelOk(String consumerTag) {}
	public void handleRecoverOk(String consumerTag) {}
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}