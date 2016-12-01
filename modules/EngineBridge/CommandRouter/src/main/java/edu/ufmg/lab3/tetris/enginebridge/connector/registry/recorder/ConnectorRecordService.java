package edu.ufmg.lab3.tetris.enginebridge.connector.registry.recorder;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;

@ProviderType
public interface ConnectorRecordService {

	public void register(final Connector origins);

	public void unRegister(final Connector origins);

	public boolean isRegistered(final Connector origin);

	public Set<Connector> getConnectors();
}