package edu.ufmg.lab3.tetris.enginebridge.connector.registry;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;

public class ConnectorTrackerCustomizer implements ServiceTrackerCustomizer<Object, Object> {

	@Override
	public Object addingService(final ServiceReference<Object> serviceReference) {

		final Registry registry = RegistryUtil.getRegistry();

		return registry.getService(serviceReference);
	}

	@Override
	public void modifiedService(final ServiceReference<Object> serviceReference, final Object object) {
	}

	@Override
	public void removedService(final ServiceReference<Object> serviceReference, final Object object) {

	}
}