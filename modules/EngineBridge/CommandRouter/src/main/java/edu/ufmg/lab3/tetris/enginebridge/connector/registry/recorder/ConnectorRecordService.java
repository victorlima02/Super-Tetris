package edu.ufmg.lab3.tetris.enginebridge.connector.registry.recorder;

import org.osgi.annotation.versioning.ProviderType;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;

@ProviderType
public interface ConnectorRecordService {

	public void register(String id, final Connector origins);
	
	public void replace(String id, final Connector origins);

	public void unRegister(String id);

	public boolean isRegistered(String id);

	public Connector getConnector(String id);
}