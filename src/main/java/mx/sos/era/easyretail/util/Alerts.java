package mx.sos.era.easyretail.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Alerts {

    public static void showErrorAlert(String mensaje){
        showErrorAlert(mensaje, "Error");
    }
    public static void showErrorAlert(String mensaje, String titulo){
        Alert alert = constuctTextAreaAlert(mensaje, titulo, "Se ha producido un error", Alert.AlertType.ERROR);
        alert.showAndWait();
    }

    public static void showInformationAlert(String mensaje, String titulo, String header){
        Alert alert = constuctAlert(mensaje,titulo,header, Alert.AlertType.INFORMATION);

        alert.showAndWait();
    }

    public static void showInformationAlert(String mensaje, String titulo) {
        Alert alert = constuctAlert(mensaje, titulo,null, Alert.AlertType.INFORMATION);

        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmationMessage(String mensaje, String titulo, String encabezado){
        var si = new ButtonType("Sí", ButtonType.YES.getButtonData());
        var no = new ButtonType("No", ButtonType.NO.getButtonData());
        return showConfirmationMessage(mensaje, titulo, encabezado, si, no);
    }

    public static Optional<ButtonType> showConfirmationMessage(String mensaje, String titulo, String encabezado, ButtonType ...botones){
        Alert alert = constuctAlert(mensaje, titulo, encabezado, Alert.AlertType.CONFIRMATION);

        alert.getButtonTypes().setAll(botones);
        return alert.showAndWait();
    }

    public static void showWarningAlert(String mensaje, String titulo){
        Alert alert = constuctAlert(mensaje, titulo, null, Alert.AlertType.WARNING);

        alert.showAndWait();
    }

    private static Alert constuctAlert(String message, String title, String header, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        var stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //IconsUtils.setIcon(stage);
        var scene = alert.getDialogPane().getScene();
        //WindowLoader.applyStyles(scene);
        stage.setScene(scene);
        return alert;
    }

    private static Alert constuctTextAreaAlert(String message, String title, String header, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);

        if (message != null){
            TextArea textArea = new TextArea(message);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefSize(400, 150);

            VBox vbo = new VBox(textArea);
            alert.getDialogPane().setContent(vbo);
        }

        var stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //IconsUtils.setIcon(stage);
        var scene = alert.getDialogPane().getScene();
        //WindowLoader.applyStyles(scene);
        stage.setScene(scene);
        return alert;
    }
}