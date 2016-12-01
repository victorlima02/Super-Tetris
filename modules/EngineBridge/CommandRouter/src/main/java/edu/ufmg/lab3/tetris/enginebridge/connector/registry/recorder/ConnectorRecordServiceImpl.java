package edu.ufmg.lab3.tetris.enginebridge.connector.registry.recorder;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;

@Component(immediate = true, service = ConnectorRecordService.class)
public class ConnectorRecordServiceImpl implements ConnectorRecordService {

	private Set<Connector> connectors;

	@Activate
	void activate(final BundleContext bundleContext) {
		connectors = new ConcurrentHashSet<>();
	}

	@Override
	public void register(final Connector connector) {
		connectors.add(connector);
	}

	@Override
	public void unRegister(final Connector connector) {
		connectors.remove(connector);
	}

	@Override
	public boolean isRegistered(final Connector Connector) {
		return connectors.contains(Connector);
	}

	@Override
	public Set<Connector> getConnectors() {
		return connectors;
	}
}