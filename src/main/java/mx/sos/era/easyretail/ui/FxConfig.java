package mx.sos.era.easyretail.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FxConfig {

    @Bean
    public FxWindowManager fxWindowManager(ApplicationContext ctx){
        return new FxWindowManager(ctx);
    }
}