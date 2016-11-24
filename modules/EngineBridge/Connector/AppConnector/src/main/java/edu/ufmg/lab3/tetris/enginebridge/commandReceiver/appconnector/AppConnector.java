package edu.ufmg.lab3.tetris.enginebridge.commandReceiver.appconnector;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;
import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Message;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = Object.class)
public class AppConnector implements Connector {

	@Override
	public void process(Message msg) {
		
		
	}

}
