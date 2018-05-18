package com.server.listener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.apache.commons.lang.SerializationUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import com.rabbitmq.client.MessageProperties;
import com.server.endpoint.EndPoint;

public class NettyTcpServerHandler extends EndPoint {

	public static Logger log = Logger.getLogger("NettyTcpServerHandler");
	
	private String channelName;
	private String exchangeName;
	private String routingKey;

	public NettyTcpServerHandler(String channelName, String exchangeName, String routingKey) throws IOException, TimeoutException{
		super(channelName, exchangeName, routingKey);
		this.channelName=channelName;
		this.exchangeName=exchangeName;
		this.routingKey=routingKey;
	}

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		try {
			ChannelBuffer buffer=(ChannelBuffer)e.getMessage();
			byte[] datas =buffer.readBytes(buffer.readableBytes()).array();
			String msg=new String(datas , "UTF-8");
			log.info("message received : "+new String(msg));
			
			if (msg != null) {
				channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(msg));
			}
				
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		finally {
			if(channel.isOpen())
			{
				try {
					channel.close();
				} catch (IOException | TimeoutException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}


}
