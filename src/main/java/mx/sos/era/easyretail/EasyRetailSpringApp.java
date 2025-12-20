package mx.sos.era.easyretail;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "mx.sos.era.easyretail", exclude = {org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class}
)
public class EasyRetailSpringApp {
}