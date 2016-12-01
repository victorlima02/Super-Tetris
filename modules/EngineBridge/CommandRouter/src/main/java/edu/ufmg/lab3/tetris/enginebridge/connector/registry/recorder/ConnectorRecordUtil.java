package edu.ufmg.lab3.tetris.enginebridge.connector.registry.recorder;

import org.osgi.service.component.annotations.Reference;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;

public class ConnectorRecordUtil {
	private static ConnectorRecordService service;

	public static void register(String id, final Connector origins) {
		getService().register(id, origins);
	}

	public static void replace(String id, Connector connector) {
		getService().replace(id, connector);
	}

	public static void unRegister(String id) {
		getService().unRegister(id);
	}

	public static boolean isRegistered(String id) {
		return getService().isRegistered(id);
	}

	public static Connector getConnector(String id) {
		return getService().getConnector(id);
	}

	@Reference(unbind = "-")
	public static void setRegisteredOrigins(final ConnectorRecordService connectorRecord) {
		ConnectorRecordUtil.service = connectorRecord;
	}

	public static ConnectorRecordService getService() {

		return service;
	}
}
