package com.server.endpoint;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class EndPoint extends SimpleChannelUpstreamHandler {

	 	protected Channel channel;
	    protected Connection connection;
	    protected String endPointName;
	    protected String exchangeName;
	    protected String routingKey;
	 
	    public EndPoint(String endpointName, String exchangeName, String routingKey) throws IOException, TimeoutException{
	         this.endPointName = endpointName;
	  
	         //Create a connection factory
	         ConnectionFactory factory = new ConnectionFactory();
	     
	         //hostname of your rabbitmq server
	         factory.setUsername("guest");
	 		factory.setPassword("guest");
	 		factory.setVirtualHost("/");
	 		factory.setHost("127.0.0.1");
	 		factory.setPort(5672);
	  
	         //getting a connection
	         connection = factory.newConnection();
	     
	         //creating a channel
	         channel = connection.createChannel();
	    
	         //declaring a queue for this channel. If queue does not exist,
	         //it will be created on the server.
	         channel.exchangeDeclare(exchangeName, "direct", true);
	         channel.queueDeclare(endpointName, true, false, false, null);
	         channel.queueBind(endpointName, exchangeName, routingKey);
	    }
	 
	 
	    /**
	     * Close channel and connection. Not necessary as it happens implicitly any way. 
	     * @throws IOException
	     * @throws TimeoutException 
	     */
	     public void close() throws IOException, TimeoutException{
	         this.channel.close();
	         this.connection.close();
	     }
	
}
