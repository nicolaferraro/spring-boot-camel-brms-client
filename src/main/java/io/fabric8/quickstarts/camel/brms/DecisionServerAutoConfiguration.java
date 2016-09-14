package io.fabric8.quickstarts.camel.brms;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "decisionserver")
public class DecisionServerAutoConfiguration {

    /**
     * The decision server host name.
     */
    private String host;

    /**
     * The decision server port.
     */
    private Integer port = 80;

    /**
     * The base path of the decision server REST API.
     */
    private String basePath = "/kie-server/services/rest/server";

    /**
     * The username to use when connecting to the decision server.
     */
    private String username;

    /**
     * The password to use when connecting to the decision server.
     */
    private String password;


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public RuleServicesClient ruleServicesClient() {
        String url = "http://" + host + ":" + port + basePath;
        logger.info("Connecting to decision server URL: {}", url);

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
        config.setMarshallingFormat(MarshallingFormat.XSTREAM);

        return KieServicesFactory.newKieServicesClient(config).getServicesClient(RuleServicesClient.class);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
