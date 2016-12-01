package edu.ufmg.lab3.tetris.enginebridge.connector.registry.recorder;

import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;

@Component(immediate = true, service = ConnectorRecordService.class)
public class ConnectorRecordServiceImpl implements ConnectorRecordService {

	private ConcurrentHashMap<String, Connector> connectors;

	@Activate
	void activate(final BundleContext bundleContext) {
		connectors = new ConcurrentHashMap<>();
	}

	@Override
	public void register(String id, final Connector connector) {
		connectors.put(id, connector);
	}

	@Override
	public void unRegister(String id) {
		connectors.remove(id);
	}

	@Override
	public boolean isRegistered(String id) {
		return connectors.contains(id);
	}

	@Override
	public Connector getConnector(String id){
		return connectors.get(id);
	}

	@Override
	public void replace(String id, Connector connector) {
		connectors.replace(id, connector);
	}
}