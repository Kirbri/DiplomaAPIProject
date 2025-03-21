package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/credentials.properties",
        "system:properties"
})

public interface CredentialsConfig extends Config {
    @Key("username")
    String geUsername();

    @Key("password")
    String getPassword();

    @Key("authorization")
    String getAuthorization();
}