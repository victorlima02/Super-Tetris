package edu.ufmg.lab3.tetris.enginebridge.connector.registry;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;
import edu.ufmg.lab3.tetris.enginebridge.connector.registry.recorder.ConnectorRecordUtil;

public class ConnectorTrackerCustomizer implements ServiceTrackerCustomizer<Object, Object> {

	@Override
	public Object addingService(final ServiceReference<Object> serviceReference) {

		Registry registry = RegistryUtil.getRegistry();

		String id = (String) serviceReference.getProperty(ConnectorRegistryKeys.PROPERTY);
		Connector driver = (Connector) registry.getService(serviceReference);

		ConnectorRecordUtil.register(id, driver);

		return registry.getService(serviceReference);
	}

	@Override
	public void modifiedService(final ServiceReference<Object> serviceReference, final Object object) {
		
		String id = (String) serviceReference.getProperty(ConnectorRegistryKeys.PROPERTY);
		Connector driver = (Connector) object;
		ConnectorRecordUtil.replace(id, driver); 

	}

	@Override
	public void removedService(final ServiceReference<Object> serviceReference, final Object object) {
		String id = (String) serviceReference.getProperty(ConnectorRegistryKeys.PROPERTY);
		ConnectorRecordUtil.unRegister(id);
	}
	
	public Connector getConnector(String id){
		return ConnectorRecordUtil.getConnector(id);
	}
}