package edu.ufmg.lab3.tetris.enginebridge.commandReceiver.opencvconnector;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;
import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Message;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = Connector.class)
public class OpenCVConnector implements Connector{

	@Override
	public void process(Message msg) {
			
	}

}
