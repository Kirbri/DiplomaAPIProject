package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:properties/credentials.properties"
})

public interface CredentialsConfig extends Config {
    @Key("username")
    String geUsername();

    @Key("password")
    String getPassword();

    @Key("authorization")
    String getAuthorization();
}