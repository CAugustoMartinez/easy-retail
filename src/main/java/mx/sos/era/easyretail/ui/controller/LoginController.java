package mx.sos.era.easyretail.ui.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import mx.sos.era.easyretail.master.entity.Usuario;
import mx.sos.era.easyretail.master.service.AuthService;
import mx.sos.era.easyretail.ui.listeners.LoginListener;
import mx.sos.era.easyretail.ui.viewmodel.LoginViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.synedra.validatorfx.Validator;

public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @FXML public TextField usuarioField;
    @FXML public PasswordField passwordField;
    @FXML public TextField visiblePasswordField;
    @FXML public Button btnTogglePassword;
    @FXML public Button loginButton;
    @FXML public Label lblMessage;

    private final LoginViewModel vmLogin = new LoginViewModel();
    private AuthService authService;
    private LoginListener loginListener;

    private boolean passwordVisible = false;

    private Validator validator;

    @FXML
    private void initialize() {
        setBindings();
        setupValidator();
        setupEvents();

        vmLogin.setUsuario("SUPERVISOR");

        Platform.runLater(() -> passwordField.requestFocus());
        updateEyeButton();
    }

    // ---------------------------
    // VALIDATOR FX
    // ---------------------------
    private void setupValidator() {
        validator = new Validator();

        validator.createCheck()
                .dependsOn("usuario", usuarioField.textProperty())
                .withMethod(c -> {
                    String u = c.get("usuario");
                    if (u == null || u.trim().isEmpty()) {
                        c.error("El usuario es requerido");
                    }
                })
                .decorates(usuarioField)
                .immediate();

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String p = c.get("password");
                    if (p == null || p.trim().isEmpty()) {
                        c.error("La contraseña es requerida");
                    }
                })
                .decorates(passwordField)
                .immediate();
    }

    // ---------------------------
    // EVENTOS Y TECLAS
    // ---------------------------
    private void setupEvents() {
        usuarioField.setOnKeyTyped(e -> limpiarError());
        passwordField.setOnKeyTyped(e -> limpiarError());
        visiblePasswordField.setOnKeyTyped(e -> limpiarError());

        usuarioField.setOnAction(e -> handleLogin());
        passwordField.setOnAction(e -> handleLogin());
        visiblePasswordField.setOnAction(e -> handleLogin());

        usuarioField.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        passwordField.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        visiblePasswordField.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
    }

    private void onKeyPressed(KeyEvent ev) {
        if (ev.getCode() == KeyCode.ENTER) {
            ev.consume();
            handleLogin();
        }
    }

    // ---------------------------
    // BINDINGS
    // ---------------------------
    private void setBindings() {
        usuarioField.textProperty().bindBidirectional(vmLogin.usuarioProperty());
        passwordField.textProperty().bindBidirectional(vmLogin.contraseñaProperty());
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
        visiblePasswordField.prefWidthProperty().bind(passwordField.prefWidthProperty());
    }

    // ---------------------------
    // LOGIN
    // ---------------------------
    public void handleLogin() {
        loginButton.setDisable(true);
        lblMessage.getStyleClass().remove("label-error");

        if (!validator.validate()) {
            mostrarError("Completa los campos requeridos");
            loginButton.setDisable(false);
            return;
        }

        Usuario usuario = authService.login(vmLogin.getUsuario(), vmLogin.getContraseña());

        if (usuario == null) {
            lblMessage.getStyleClass().add("label-error");
            mostrarError("Usuario o contraseña incorrectos");

            Platform.runLater(() -> {
                if (passwordVisible) {
                    visiblePasswordField.requestFocus();
                    visiblePasswordField.selectAll();
                } else {
                    passwordField.requestFocus();
                    passwordField.selectAll();
                }
            });

            loginButton.setDisable(false);
            return;
        }

        if (loginListener != null) {
            loginListener.onLoginSuccess(usuario);
        }
    }

    // ---------------------------
    // UI FEEDBACK
    // ---------------------------
    private void mostrarError(String mensaje) {

        if (lblMessage.getStyleClass().contains("label-error")){
            lblMessage.getStyleClass().add("label-error");
        }

        Platform.runLater(() -> {});
        lblMessage.setText(mensaje);

        FadeTransition ft = new FadeTransition(Duration.millis(200), lblMessage);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void limpiarError() {
        lblMessage.getStyleClass().remove("label-error");
        //lblMessage.setText("¡Nos alegra tenerte de vuelta!");
    }

    // ---------------------------
    // OJO DE CONTRASEÑA
    // ---------------------------
    public void onTogglePassword(ActionEvent actionEvent) {
        passwordVisible = !passwordVisible;

        visiblePasswordField.setVisible(passwordVisible);
        visiblePasswordField.setManaged(passwordVisible);

        passwordField.setVisible(!passwordVisible);
        passwordField.setManaged(!passwordVisible);

        if (passwordVisible) {
            visiblePasswordField.requestFocus();
            visiblePasswordField.positionCaret(visiblePasswordField.getText().length());
        } else {
            passwordField.requestFocus();
            passwordField.positionCaret(passwordField.getText().length());
        }

        updateEyeButton();
    }

    private void updateEyeButton() {
        btnTogglePassword.setText(passwordVisible ? "Ocultar" : "Mostrar");
        btnTogglePassword.setAccessibleText(passwordVisible ? "Ocultar contraseña" : "Mostrar contraseña");
    }

    // ---------------------------
    // SETTERS
    // ---------------------------
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
}