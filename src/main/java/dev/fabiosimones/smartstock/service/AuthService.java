package dev.fabiosimones.smartstock.service;

import dev.fabiosimones.smartstock.client.AuthClient;
import dev.fabiosimones.smartstock.client.dto.AuthRequest;
import dev.fabiosimones.smartstock.config.AppConfig;
import dev.fabiosimones.smartstock.exception.SmartStockException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private static final String GRANT_TYPE = "client_credentials";
    private static String token;
    private static LocalDateTime expiresIn;


    private final AuthClient authClient;
    private final AppConfig appConfig;

    public AuthService(AuthClient authClient, AppConfig appConfig) {
        this.authClient = authClient;
        this.appConfig = appConfig;
    }

    public String getToken(){
        if(token == null){
            generateToken();
        } else if(expiresIn.isBefore(LocalDateTime.now())){
            generateToken();
        }

        return token;
    }

    private void generateToken() {

        var request = new AuthRequest(
                GRANT_TYPE,
                appConfig.getClientId(),
                appConfig.getClientSecret()
        );

        var response = authClient.authenticate(request);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new SmartStockException("Cannot generate token. " +
                    "Status: " + response.getStatusCode() +
                    " Response: " + response.getBody());
        }

        token = response.getBody().accessToken();
        expiresIn = LocalDateTime.now().plusSeconds(response.getBody().expiresIn());
    }
}
