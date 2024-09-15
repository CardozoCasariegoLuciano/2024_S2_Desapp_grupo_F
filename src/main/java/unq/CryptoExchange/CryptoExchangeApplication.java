package unq.CryptoExchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class CryptoExchangeApplication {

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

            e.printStackTrace();
        }
    }

    private static void openDDBBUI() {
        try {

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {

                Desktop.getDesktop().browse(new URI("http://localhost:8080/h2-console/"));
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
