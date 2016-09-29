package edu.ufmg.lab3.tetris.commandrouter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component
public class CommandRouter implements Runnable{
	
	private static Log log = LogFactoryUtil.getLog(CommandRouter.class);
	private Executor pool;
	private int PORT_NUMBER = 5648;
	private Thread serverListener;
	
	public CommandRouter(){
		pool = Executors.newFixedThreadPool(5);
	}
	
	@Activate
	public void activate(){
		serverListener = new Thread(this);
		serverListener.run();
	}
	
	@Deactivate
	public void deactive(){
		serverListener.interrupt();
	}
	
	public void run(){
		
		try(ServerSocket server = new ServerSocket(PORT_NUMBER);){
			
			while(true){
				Socket client = server.accept();
				log.debug("client connected: "+client.getRemoteSocketAddress());
				RequestHandler worker = new RequestHandler(client);
				pool.execute(worker);
			}
			
		} catch (IOException e) {
			log.error(e);
		}
	}

}