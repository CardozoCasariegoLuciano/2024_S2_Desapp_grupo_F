package unq.cryptoexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class CryptoExchangeApplication {

    private static final Logger logger = LoggerFactory.getLogger(CryptoExchangeApplication.class);

    @Value("${swagger.ui.url}")
    private static String swaggerUrl;

    @Value("${h2.console.url}")
    private static String h2ConsoleUrl;

    public static void main(String[] args) {
        openSwaggerUI();
        openDDBBUI();
        SpringApplication.run(CryptoExchangeApplication.class, args);
    }

    private static void openSwaggerUI() {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(swaggerUrl));
            }
        } catch (Exception e) {
            logger.error("Error opening Swagger UI", e);
        }
    }

    private static void openDDBBUI() {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(h2ConsoleUrl));
            }
        } catch (Exception e) {
            logger.error("Error opening H2 Database UI", e);
        }
    }
}
