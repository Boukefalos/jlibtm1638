package test;

import java.util.Properties;

public class TestUdpImplementation {
    public static void main(String[] args) throws Exception {
        Properties localProperties = new Properties();
        localProperties.setProperty("implementation", "local");
        localProperties.setProperty("protocol", "udp");
        localProperties.setProperty("server", "true");
        localProperties.setProperty("server.direct", "false");
        localProperties.setProperty("server.port", "8883");
        localProperties.setProperty("server.protocol", "udp");

        Properties remoteProperties = new Properties();
        remoteProperties.setProperty("implementation", "remote");
        remoteProperties.setProperty("protocol", "udp");
        remoteProperties.setProperty("remote.host", "localhost");
        remoteProperties.setProperty("remote.port", "8883");

        TestRemoteImplementation.main(localProperties, remoteProperties);
    }
}
