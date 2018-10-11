package revolut.moneyTransfer.integration;

import java.util.Properties;

import org.apache.openejb.OpenEjbContainer;

public class BaseTest {

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        properties.setProperty("cxf.jaxrs.providers", "org.codehaus.jackson.jaxrs.JacksonJsonProvider");
		properties.setProperty(OpenEjbContainer.OPENEJB_EMBEDDED_REMOTABLE, "true");
        return properties;
    }
}
