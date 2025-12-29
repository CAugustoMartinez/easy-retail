package mx.sos.era.easyretail.ui.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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

    @FXML
    public TextField usuarioField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField visiblePasswordField;
    @FXML
    public Button btnTogglePassword;
    @FXML
    public Button loginButton;
    @FXML
    public Label lblMessage;
    private ImageView eyeView;
    private final LoginViewModel vmLogin = new LoginViewModel();
    private AuthService authService;
    private LoginListener loginListener;

    private Validator validator;

    @FXML
    private void initialize() {
        setupEyeView();
        setBindings();
        setupValidator();
        setupEvents();

        vmLogin.setUser("SUPERVISOR");
        vmLogin.setPassword("");


        Platform.runLater(() -> passwordField.requestFocus());

    }

    private void setupEyeView() {
        eyeView = new ImageView();
        eyeView.setFitWidth(18);
        eyeView.setFitHeight(18);
        btnTogglePassword.setGraphic(eyeView);
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
                        c.error("El usuario es requerido.");
                    }
                })
                .decorates(usuarioField)
                .immediate();

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String p = c.get("password");
                    if (p == null || p.trim().isEmpty()) {
                        c.error("La contraseña es requerida.");
                    }
                })
                .decorates(passwordField)
                .immediate();
    }

    // ---------------------------
    // EVENTOS Y TECLAS
    // ---------------------------
    private void setupEvents() {
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
        } else {
            limpiarError();
        }
    }

    // ---------------------------
    // BINDINGS
    // ---------------------------
    private void setBindings() {
        usuarioField.textProperty().bindBidirectional(vmLogin.userProperty());
        passwordField.textProperty().bindBidirectional(vmLogin.passwordProperty());
        passwordField.visibleProperty().bind(vmLogin.passwordVisibleProperty().not());
        passwordField.managedProperty().bind(vmLogin.passwordVisibleProperty().not());
        visiblePasswordField.textProperty().bindBidirectional(vmLogin.passwordProperty());
        visiblePasswordField.prefWidthProperty().bind(passwordField.prefWidthProperty());
        visiblePasswordField.visibleProperty().bind(vmLogin.passwordVisibleProperty());
        visiblePasswordField.managedProperty().bind(vmLogin.passwordVisibleProperty());
        eyeView.imageProperty().bindBidirectional(vmLogin.eyeIconProperty());
    }

    // ---------------------------
    // LOGIN
    // ---------------------------
    public void handleLogin() {
        loginButton.setDisable(true);
        lblMessage.getStyleClass().remove("label-error");

        if (!validator.validate()) {
            var error = validator.getValidationResult().getMessages().getFirst();
            mostrarError(error.getText());
            loginButton.setDisable(false);
            return;
        }

        Usuario usuario = authService.login(vmLogin.getUser(), vmLogin.getPassword());

        if (usuario == null) {
            lblMessage.getStyleClass().add("label-error");
            mostrarError("Usuario o contraseña incorrectos");

            Platform.runLater(() -> {
                if (vmLogin.isPasswordVisible()) {
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

        loginListener.onLoginSuccess(usuario);
    }

    // ---------------------------
    // UI FEEDBACK
    // ---------------------------
    private void mostrarError(String mensaje) {
        log.error(mensaje);

        if (!lblMessage.getStyleClass().contains("label-error")) {
            lblMessage.getStyleClass().add("label-error");
        }

        Platform.runLater(() -> lblMessage.setText(mensaje));

        FadeTransition ft = new FadeTransition(Duration.millis(200), lblMessage);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void limpiarError() {
        log.error("Limpiar mensaje de error");
        lblMessage.getStyleClass().remove("label-error");
        //lblMessage.setText("¡Nos alegra tenerte de vuelta!");
    }

    // ---------------------------
    // OJO DE CONTRASEÑA
    // ---------------------------
    public void onTogglePassword() {
        vmLogin.tooglePasswordVisible();

        if (vmLogin.isPasswordVisible()) {
            visiblePasswordField.requestFocus();
            visiblePasswordField.positionCaret(visiblePasswordField.getText().length());
        } else {
            passwordField.requestFocus();
            passwordField.positionCaret(passwordField.getText().length());
        }
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

    public void handleMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Label) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleClose(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Label) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}