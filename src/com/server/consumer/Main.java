package com.server.consumer;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
	public Main() throws Exception{
		String exchangeName = "newExchange";
		String routingKey = "newTestRoute";
		QueueConsumer consumer = new QueueConsumer("newQueue", exchangeName, routingKey);
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception{
	  new Main();
	}
}
