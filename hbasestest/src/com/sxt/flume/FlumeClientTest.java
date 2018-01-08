package com.sxt.flume;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;
import java.nio.charset.Charset;

// 官方例程
/*
 * As of Flume 1.4.0, Avro is the default RPC protocol. 
 * The NettyAvroRpcClient and ThriftRpcClient implement the RpcClient interface. 
 * The client needs to create this object with the host and port of the target Flume agent, 
 * and can then use the RpcClient to send data into the agent. 
 * The following example shows how to use the Flume Client SDK API within a user’s data-generating application:
 */
public class FlumeClientTest {
	public static void main(String[] args) throws Exception{
		MyRpcClientFacade client = new MyRpcClientFacade();
		// Initialize client with the remote Flume agent's host and port
		client.init("node5", 41414);

		// Send 10 events to the remote Flume agent. That agent should be
		// configured to listen with an AvroSource.
		 String sampleData = "ERROR";
//		String sampleData = "Hello Flume!";
		System.out.println("发送数据");

		for (int i = 0; i < 50; i++) {
			Thread.sleep(1000);
			client.sendDataToFlume(sampleData + i);
		}

		client.cleanUp();
	}
}

class MyRpcClientFacade {
	private RpcClient client;
	private String hostname;
	private int port;

	public void init(String hostname, int port) {
		// Setup the RPC connection
		this.hostname = hostname;
		this.port = port;
		this.client = RpcClientFactory.getDefaultInstance(hostname, port);
		System.out.println("建立连接");
		// Use the following method to create a thrift client (instead of the
		// above line):
		// this.client = RpcClientFactory.getThriftInstance(hostname, port);
	}

	public void sendDataToFlume(String data) {
		// Create a Flume Event object that encapsulates the sample data
		Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));

		// Send the event
		try {
			client.append(event);
		} catch (EventDeliveryException e) {
			// clean up and recreate the client
			client.close();
			client = null;
			client = RpcClientFactory.getDefaultInstance(hostname, port);
			// Use the following method to create a thrift client (instead of
			// the above line):
			// this.client = RpcClientFactory.getThriftInstance(hostname, port);
		}
	}

	public void cleanUp() {
		// Close the RPC connection
		System.out.println("断开连接");
		client.close();
	}

}