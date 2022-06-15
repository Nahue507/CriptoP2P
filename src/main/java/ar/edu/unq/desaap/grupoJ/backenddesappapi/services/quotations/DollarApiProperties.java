package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.quotations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "dollar.quotation.api")
@Configuration("dollarApiProperties")
public class DollarApiProperties {

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
