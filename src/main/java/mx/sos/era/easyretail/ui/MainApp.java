package mx.sos.era.easyretail.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mx.sos.era.easyretail.EasyRetailSpringApp;
import mx.sos.era.easyretail.config.InstallerConfig;
import mx.sos.era.easyretail.config.InstallerConfigLoader;
import mx.sos.era.easyretail.config.InstallerWizard;
import mx.sos.era.easyretail.master.service.AuthService;
import mx.sos.era.easyretail.master.service.EmpresaService;
import mx.sos.era.easyretail.ui.controller.CatalogoEmpresasController;
import mx.sos.era.easyretail.ui.controller.LoginController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Revisar si existe install.properties
        Path path = Paths.get("config/installer.properties");

        if (!Files.exists(path)) {
            InstallerConfig cfg = InstallerWizard.showWizard();
            InstallerConfigLoader.saveToFile(cfg);
        }

        // Revisar si existe la base dbempresas y crearla si no
        InstallerConfig cfg = InstallerConfigLoader.loadFromFile();

        String url = String.format(
                "jdbc:mysql://%s:%d/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                cfg.getHost(), cfg.getPort()
        );

        try (Connection conn = DriverManager.getConnection(url, cfg.getUser(), cfg.getPassword())) {
            conn.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS dbempresas");
        }

        // Arrancar Spring
        springContext = new SpringApplicationBuilder(EasyRetailSpringApp.class)
                .initializers(ctx -> ctx.getBeanFactory().registerSingleton("installerConfig", cfg))
                .run();

        // Obtener NavigationService desde Spring y abrir login
        NavigationService nav = springContext.getBean(NavigationService.class);

        // Delegar la apertura del login al NavigationService
        nav.openLogin(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}