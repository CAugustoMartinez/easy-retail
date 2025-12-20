package mx.sos.era.easyretail.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.sos.era.easyretail.ui.InstallerWizardController;
import mx.sos.era.easyretail.ui.MainApp;

public class InstallerWizard{

    public static InstallerConfig showWizard() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                InstallerWizard.class.getResource("/mx/sos/era/easyretail/installer_wizard.fxml")
        );
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();

        InstallerWizardController controller = loader.getController();
        controller.setStage(stage);

        stage.setTitle("Configuración Inicial del Sistema");
        stage.setScene(scene);
        stage.showAndWait();

        return controller.getResult();
    }
}