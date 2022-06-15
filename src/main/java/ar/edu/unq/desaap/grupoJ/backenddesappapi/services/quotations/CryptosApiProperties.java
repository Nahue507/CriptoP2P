package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.quotations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cryptos.quotation.api")
@Configuration("cryptosApiProperties")
public class CryptosApiProperties {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
