package edu.ufmg.lab3.tetris.enginebridge.connector.registry;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

@Component(immediate = true, service = Object.class)
public class ConnectorRegistry {
	private static final String FILTER = "(&(" + ConnectorRegistryKeys.PROPERTY + "=*)(objectClass=java.lang.Object))";

	private ServiceTracker<Object, Object> serviceTracker;

	@Activate
	void activate(final BundleContext bundleContext) {

		final Registry registry = RegistryUtil.getRegistry();
		final ConnectorTrackerCustomizer trackerCustomizer = new ConnectorTrackerCustomizer();

		serviceTracker = registry.trackServices(registry.getFilter(FILTER), trackerCustomizer);

		serviceTracker.open();
	}

	@Deactivate
	public void deactivate() {

		serviceTracker.close();
	}
}
