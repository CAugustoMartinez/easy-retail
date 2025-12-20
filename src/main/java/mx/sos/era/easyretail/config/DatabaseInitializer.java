package mx.sos.era.easyretail.config;

import jakarta.annotation.PostConstruct;
import mx.sos.era.easyretail.context.DataSourceManager;
import mx.sos.era.easyretail.context.TenantKeys;
import mx.sos.era.easyretail.context.TenantRoutingDataSource;
import mx.sos.era.easyretail.context.model.TenantInfo;
import mx.sos.era.easyretail.util.MysqlDataSourceFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class DatabaseInitializer implements ApplicationRunner {
    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
            // Inicializa Flyway manualmente
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations("classpath:mx/sos/era/easyretail/db/migrations/master")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();
            System.out.println("✅ Migraciones aplicadas correctamente");
    }

}