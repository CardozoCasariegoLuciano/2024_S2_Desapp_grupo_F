package unq.cryptoexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class CryptoExchangeApplication {

    private static final Logger logger = LoggerFactory.getLogger(CryptoExchangeApplication.class);

    public static void main(String[] args) {

        openSwaggerUI();

        openDDBBUI();

        SpringApplication.run(CryptoExchangeApplication.class, args);

    }

    private static void openSwaggerUI() {
        try {

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {

                Desktop.getDesktop().browse(new URI("http://localhost:8080/swagger-ui/index.html"));
            }
        } catch (Exception e) {

            logger.error("Error opening Swagger UI", e);
        }
    }

    private static void openDDBBUI() {
        try {

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {

                Desktop.getDesktop().browse(new URI("http://localhost:8080/h2-console/"));
            }
        } catch (Exception e) {

            logger.error("Error opening H2 Database UI", e);
        }
    }

}
