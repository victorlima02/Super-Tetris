package edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Connector {
	void process(Message msg);
}
