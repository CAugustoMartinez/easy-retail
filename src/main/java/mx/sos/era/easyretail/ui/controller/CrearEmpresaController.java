package mx.sos.era.easyretail.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.sos.era.easyretail.master.entity.Empresa;
import mx.sos.era.easyretail.master.service.EmpresaService;
import mx.sos.era.easyretail.config.InstallerConfig;
import org.springframework.lang.NonNull;

import java.io.File;
import java.util.List;

public class CrearEmpresaController {

    @FXML private TextField txtPassword;
    @FXML private TextField txtNombre;
    @FXML private TextField txtRazonSocial;
    @FXML private TextField txtRfc;
    @FXML private ComboBox<String> cmbRegimenFiscal;

    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtPaginaWeb;

    @FXML private TextField txtHost;
    @FXML private TextField txtPort;
    @FXML private TextField txtDatabase;
    @FXML private TextField txtDbUser;
    @FXML private PasswordField txtDbPass;

    @FXML private TextField txtLogo;
    @FXML private TextField txtCertificado;
    @FXML private TextField txtSello;

    private EmpresaService empresaService;
    private Runnable onEmpresaGuardada;

    public void setEmpresaService(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    public void setOnEmpresaGuardada(Runnable callback) {
        this.onEmpresaGuardada = callback;
    }

    /**
     * Inyecta las opciones de régimen fiscal (provenientes de catálogo).
     */
    public void setRegimenFiscalOptions(List<String> opciones) {
        cmbRegimenFiscal.getItems().clear();
        if (opciones != null) cmbRegimenFiscal.getItems().addAll(opciones);
    }

    /**
     * Prefill de los datos de conexión usando InstallerConfig y deshabilita edición.
     */
    public void setInstallerConfig(InstallerConfig cfg) {
        if (cfg == null) return;
        txtHost.setText(cfg.getHost());
        txtPort.setText(String.valueOf(cfg.getPort()));
        txtDatabase.setText(cfg.getDatabase() != null ? cfg.getDatabase() : "");
        txtDbUser.setText(cfg.getUser());
        txtDbPass.setText(cfg.getPassword());
        // Los campos ya están marcados como disable="true" en FXML
    }

    @FXML
    private void onSeleccionarLogo() {
        seleccionarArchivo(txtLogo, "Seleccionar logo", "*.png", "*.jpg", "*.jpeg");
    }

    @FXML
    private void onSeleccionarCertificado() {
        seleccionarArchivo(txtCertificado, "Seleccionar certificado", "*.cer");
    }

    @FXML
    private void onSeleccionarSello() {
        seleccionarArchivo(txtSello, "Seleccionar sello digital", "*.key");
    }

    private void seleccionarArchivo(TextField target, String titulo, String... filtros) {
        FileChooser fc = new FileChooser();
        fc.setTitle(titulo);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos", filtros));
        File f = fc.showOpenDialog(getStage());
        if (f != null) target.setText(f.getAbsolutePath());
    }

    private Stage getStage() {
        return (Stage) txtRazonSocial.getScene().getWindow();
    }

    @FXML
    private void onGuardar() {
        // Validaciones mínimas
        String razon = txtRazonSocial.getText();
        if (razon == null || razon.trim().isEmpty()) {
            showAlert("Razón social requerida", "La razón social es obligatoria.");
            return;
        }

        Empresa e = getEmpresa(razon);

        // Guardar usando el servicio
        empresaService.crear(e);

        if (onEmpresaGuardada != null) onEmpresaGuardada.run();

        getStage().close();
    }

    @NonNull
    private Empresa getEmpresa(String razon) {
        Empresa e = new Empresa();
        e.setRazonSocial(razon.trim());
        // Nombre comercial opcional
        String nombre = txtNombre.getText();
        e.setNombre((nombre == null || nombre.trim().isEmpty()) ? null : nombre.trim());

        e.setRfc(txtRfc.getText());
        e.setRegimenFiscal(cmbRegimenFiscal.getValue());

        e.setEmailContacto(txtEmail.getText());
        e.setTelefonoContacto(txtTelefono.getText());
        e.setPaginaWeb(txtPaginaWeb.getText());

        e.setLogoPath(txtLogo.getText());
        e.setCertificadoPath(txtCertificado.getText());
        e.setSelloDigitalPath(txtSello.getText());
        return e;
    }

    @FXML
    private void onCancelar() {
        getStage().close();
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.initOwner(getStage());
        a.showAndWait();
    }

    public void onBuscarCodigoPostal(ActionEvent actionEvent) {

    }

    @FXML
    private void onProbarContrasena(ActionEvent ignoredActionEvent){

    }
}