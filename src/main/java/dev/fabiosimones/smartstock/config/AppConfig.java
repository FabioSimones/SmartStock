package dev.fabiosimones.smartstock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.client-id}")
    private String clientId;

    @Value("{$app.client-secret}")
    private String clientSecret;

    public String getClientSecret() {
        return clientSecret;
    }

    public String getClientId() {
        return clientId;
    }
}
