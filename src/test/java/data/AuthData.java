package data;

import config.CredentialsConfig;
import org.aeonbits.owner.ConfigFactory;

public class AuthData {

    CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    public String username = config.geUsername();
    public String password = config.getPassword();
    public String authorization = config.getAuthorization();
}
