package unq.CryptoExchange.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Crypto Exchange")
                .version("0.0.1")
                .description("TP Desarrollo de aplicaciones 2024 S2"));
    }

}
