package com.server.listener;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyNIOListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Ravi :: ServletContextListener started");
		try {
			ServerBootstrap bootstrap = new ServerBootstrap(
					new NioServerSocketChannelFactory(Executors
							.newCachedThreadPool(), Executors.newCachedThreadPool()));
			
			// Configure the pipeline factory.
			String exchangeName = "newExchange";
			String routingKey = "newTestRoute";
			String QueueName = "newQueue";
			bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				public ChannelPipeline getPipeline() throws Exception {
					return Channels.pipeline(new NettyTcpServerHandler(QueueName, exchangeName, routingKey));
				}
			});

			bootstrap.setOption("broadcast", true);
			bootstrap.setOption("child.tcpNoDelay", true);

			bootstrap.setOption("receiveBufferSizePredictorFactory",
					new FixedReceiveBufferSizePredictorFactory(4096));
			
			bootstrap.bind(new InetSocketAddress(Integer.parseInt("8008")));
			System.out.println("BroadCasting");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

}
