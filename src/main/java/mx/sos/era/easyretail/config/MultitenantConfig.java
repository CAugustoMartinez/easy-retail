package mx.sos.era.easyretail.config;

import mx.sos.era.easyretail.context.DataSourceManager;
import mx.sos.era.easyretail.context.TenantKeys;
import mx.sos.era.easyretail.context.TenantRoutingDataSource;
import mx.sos.era.easyretail.context.model.TenantInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultitenantConfig {

    @Bean
    @Primary
    public DataSource routingDataSource(InstallerConfig installerConfig,
                                        DataSourceManager manager) {
        TenantRoutingDataSource routing = new TenantRoutingDataSource();

        // Crear el DataSource master a partir de InstallerConfig
        TenantInfo masterInfo = new TenantInfo(
                TenantKeys.MASTER,
                installerConfig.getHost(),
                installerConfig.getPort(),
                installerConfig.getDatabase(),
                installerConfig.getUser(),
                installerConfig.getPassword()
        );
        DataSource master = manager.getOrCreate(masterInfo);

        // Configurar el mapa inicial con el master
        Map<Object, Object> targets = new HashMap<>();
        targets.put(TenantKeys.MASTER, master);

        routing.setDefaultTargetDataSource(master);
        routing.setTargetDataSources(targets);

        // Inicializar el routing
        routing.afterPropertiesSet();

        return routing;
    }
}