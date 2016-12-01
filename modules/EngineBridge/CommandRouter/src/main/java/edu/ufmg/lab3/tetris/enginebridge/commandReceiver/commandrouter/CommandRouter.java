package edu.ufmg.lab3.tetris.enginebridge.commandReceiver.commandrouter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@Component(immediate = true, service = Object.class)
public class CommandRouter extends Thread {

	private static Log log = LogFactoryUtil.getLog(CommandRouter.class);
	private Executor pool;
	private int PORT_NUMBER = 8070;
	private volatile ServerSocket serverSocket;
	private Socket client;

	public CommandRouter() {
		pool = Executors.newFixedThreadPool(5);
	}

	@Activate
	public void activate() {
		setDaemon(true);
		start();
		log.info("CommandRouter loaded");
	}

	@Deactivate
	public void deactive() throws InterruptedException {
		interrupt();
		try {
			serverSocket.close();
			
		} catch (Exception e) {
			log.error(e);
		}
		join(5000);
		log.info("CommandRouter stopped");
	}

	@Override
	public void run() {
		while (!isInterrupted())
			try {

				doServer();

			} catch (Exception e) {
				log.error(e);
				if (!isInterrupted())
					try {
						Thread.sleep(5000);
					} catch (Exception ex) {
					}
			}
	}

	private void doServer() throws Exception {
		serverSocket = new ServerSocket(PORT_NUMBER);
		try {
			while (!isInterrupted())
				doConnection(serverSocket);
		} finally {
			serverSocket.close();
		}
	}

	private void doConnection(ServerSocket server) throws Exception {
		
		try {
			client = serverSocket.accept();
			log.info("client connected: " + client.getRemoteSocketAddress());
			RequestHandler worker = new RequestHandler(client);
			pool.execute(worker);

		} catch (Exception e) {
			log.info("client disconnected: " + client.getRemoteSocketAddress());
		} finally {
			client = null;
		}

	}

}