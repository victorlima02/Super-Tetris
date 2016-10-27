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
		run();
		log.info("CommandRouter loaded");
	}

	@Deactivate
	public void deactive() {
		interrupt();
		try {
			serverSocket.close();
			join(5000);
		} catch (Exception e) {
			log.error(e);
		}
		log.info("CommandRouter stopped");
	}

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
		client = serverSocket.accept();
		try {

			log.info("client connected: " + client.getRemoteSocketAddress());
			RequestHandler worker = new RequestHandler(client);
			pool.execute(worker);

		} catch (Exception e) {
			log.error(e);
		} finally {
			client = null;
		}

	}

}