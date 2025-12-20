package mx.sos.era.easyretail.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.sos.era.easyretail.ui.util.FxWindowUtils;
import org.springframework.context.ApplicationContext;

public class FxWindowManager {

    private final ApplicationContext springContext;

    public FxWindowManager(ApplicationContext springContext) {
        this.springContext = springContext;
    }

    // ✅ Abrir ventana SIN controlador (solo FXML)
    public void open(View view, Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getPath()));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    // ✅ Abrir ventana CON controlador (inyectando servicios Spring)
    public <T> T open(View view, Stage stage, Class<T> controllerClass) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getPath()));
        Parent root = loader.load();

        // Obtener el controlador
        T controller = loader.getController();

        // Inyectar dependencias desde Spring (si existen)
        springContext.getAutowireCapableBeanFactory().autowireBean(controller);

        stage.setScene(new Scene(root));
        stage.show();

        FxWindowUtils.fitStageToVisualBounds(stage, 10);

        return controller;
    }
}