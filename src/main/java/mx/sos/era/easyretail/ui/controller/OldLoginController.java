package mx.sos.era.easyretail.ui.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mx.sos.era.easyretail.master.entity.Empresa;
import mx.sos.era.easyretail.master.service.AuthService;
import mx.sos.era.easyretail.ui.listeners.LoginListener;
import mx.sos.era.easyretail.ui.viewmodel.OldLoginViewModel;
import net.synedra.validatorfx.ValidationMessage;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class OldLoginController implements Initializable {
    @FXML
    public Button btnCrearEmpresa;
    @FXML
    public TextField txtEmpresa;
    @FXML
    public Button btnAbrirEmpresas;
    @FXML
    public TextField txtUsuario;
    @FXML
    public PasswordField txtContrasena;
    @FXML
    public TextField txtPasswordMost;
    @FXML
    public CheckBox chkMostrarContrasena;
    @FXML
    public Button btnEntrar;
    @FXML
    public Button btnSalir;

    private final ObservableList<Empresa> empresas = FXCollections.observableArrayList();
    private final OldLoginViewModel oldLoginViewModel = new OldLoginViewModel();
    private final Validator validator = new Validator();
    private final AtomicInteger attemptCounter = new AtomicInteger(0);
    //private final ServicioAutenticacion authService = new ServicioAutenticacion();
    private Stage primaryStage;
    private AuthService authService;
    private LoginListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmpresa.setOnKeyPressed(this::handleKeyEvents);
        txtUsuario.setOnKeyPressed(this::handleKeyEvents);
        txtContrasena.setOnKeyPressed(this::handleKeyEvents);
        txtPasswordMost.setOnKeyPressed(this::handleKeyEvents);
        txtEmpresa.setFocusTraversable(false);


        //var lista = empresasDAO.ListaEmpresas();
        //empresas.setAll(lista);
        oldLoginViewModel.setUsuario("SUP");
        oldLoginViewModel.setCrearEmpresasVisible(true);
        setBindings();
        setValidations();
        setListeners();
        loadIcons();
        oldLoginViewModel.showPasswordProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue){
                txtPasswordMost.setVisible(true);
                txtContrasena.setVisible(false);
            }else{
                txtPasswordMost.setVisible(false);
                txtContrasena.setVisible(true);
            }
        });
    }

    public void setAuthService(AuthService authService){
        this.authService = authService;
    }

    private void setListeners(){
        Platform.runLater(() -> {
            Scene scene = btnAbrirEmpresas.getScene();
            scene.setOnKeyPressed(this::handleKeyEvents);
        });
        oldLoginViewModel.empresaProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null){
                //Sesion.getInstance().setEmpresaActual(newValue);
                //ConnectionData.IDEMPRESA = newValue.getCodigoEmpresa();
                //ConnectionData.DATABASE = newValue.getNombreDB();
            }
        });
    }

    private void setBindings(){
        txtPasswordMost.textProperty().bindBidirectional(txtContrasena.textProperty());
        txtEmpresa.textProperty().bindBidirectional(oldLoginViewModel.nombreEmpresaProperty());
        txtUsuario.textProperty().bindBidirectional(oldLoginViewModel.usuarioProperty());
        txtContrasena.textProperty().bindBidirectional(oldLoginViewModel.passwordProperty());
        chkMostrarContrasena.selectedProperty().bindBidirectional(oldLoginViewModel.showPasswordProperty());
        btnCrearEmpresa.visibleProperty().bind(oldLoginViewModel.getShowEmpresasProperty());
    }

    private void setValidations(){
        validator.createCheck()
                .dependsOn("empresa", oldLoginViewModel.empresaProperty())
                .withMethod(c -> {
                    Empresa empresa = c.get("empresa");
                    if (empresa == null){
                        c.error("Seleccione una empresa para iniciar");
                    }
                }).decorates(txtEmpresa);
        validator.createCheck()
                .dependsOn("usuario", txtUsuario.textProperty())
                .withMethod(c -> {
                    String usuario = c.get("usuario");
                    if (usuario.isBlank() || usuario.isEmpty()){
                        c.error("El usuario es requerido");
                    }
                }).decorates(txtUsuario);
        validator.createCheck()
                .dependsOn("password", txtContrasena.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    if (password == null || password.isEmpty() || password.isBlank()){
                        c.error("La contaseña es requerida");
                    }
                }).decorates(txtContrasena);
    }

    private void loadIcons(){
        //var ivEntrar = IconsUtils.loadImageView("/mx/com/sos/era/imgs/ent.png");
        //var ivSalir = IconsUtils.loadImageView("/mx/com/sos/era/imgs/sal.png");
        //btnEntrar.setGraphic(ivEntrar);
        //btnSalir.setGraphic(ivSalir);
        btnEntrar.getStyleClass().add("flat-button");
        btnSalir.getStyleClass().add("flat-button");
        btnAbrirEmpresas.getStyleClass().add("flat-button");
        btnCrearEmpresa.getStyleClass().add("flat-button");
    }

    @FXML
    public void handleActionCrearEmpresa(ActionEvent event){
        //frmEmpresas empresas = new frmEmpresas();
        //empresas.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        //empresas.setVisible(true);
    }

    @FXML
    public void handleActionAbrirEmpresas(ActionEvent actionEvent) {
        showListadoEmpresas();
    }

    @FXML
    public void handleActionEntrar(ActionEvent actionEvent) {
        handleEntrar();
    }

    private void handleEntrar(){
        if (validator.validate()){
            boolean authenticated = true; //authService.authenticate(loginViewModel.getUsuario(), loginViewModel.getPassword());
            if (authenticated){
                //Star.uso_decimales = Star.get_uso_decimales_db();
                //Star.iRegUsr("0");

                var dateTime = ZonedDateTime.now();
                var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                //Sesion.getInstance().setFechaInicioSesion(dateTime.format(formatter));
                //Sesion.getInstance().setEmpresaActual(loginViewModel.getEmpresa());
                //ConnectionData.DATABASE = loginViewModel.getEmpresa().getNombreDB();
                //try {
                    //ConnectionPool.getInstance().close();
                //} catch (SQLException e) {
                    //Alerts.showErrorAlert(e.getMessage());
                //}
                //ConnectionPool.getInstance().changeDatabase(ConnectionData.DATABASE);

                //try {
                    //primaryStage.close();
                    //WindowLoader.startMainApp(primaryStage);
                    //((Stage)btnEntrar.getScene().getWindow()).close();
                //} catch (IOException e) {
                    //Alerts.showErrorAlert(e.getMessage());
                //}
            }else{
                int attempts = attemptCounter.incrementAndGet();
                if (attempts >= 3){
                    //Alerts.showInformationAlert("Se han excedido los 3 intentos de inicio de sesión se cerrara el sistema.", "Inicio de sesión");
                    Platform.exit();
                }else{
                    //Alerts.showErrorAlert("Inicio de sesión incorrecto intente de nuevo.");
                }
            }
        }else{
            //CustomPopOver popOver = new CustomPopOver();
            ObservableList<String> data = validator.getValidationResult().getMessages().stream()
                    .map(ValidationMessage::getText)
                    .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
            //popOver.showPopover("Errores", btnEntrar, data);
        }
    }

    @FXML
    public void handleActionSalir(ActionEvent actionEvent) {
        handleSalir();
    }

    private void handleSalir(){
        Platform.exit();
    }

    private void showListadoEmpresas(){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            //loader.setLocation(LoginController.class.getResource(ViewUtil.LISTADO_GENERAL_VIEW));
//            VBox root = loader.load();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Elige una empresa");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(null);
//            //IconsUtils.setIcon(dialogStage);
//            Scene scene = new Scene(root);
//            //WindowLoader.applyStyles(scene);
//            dialogStage.setScene(scene);
//
//            //ListadoGeneralController controller = loader.getController();
//            //controller.setEmpresas(empresas);
//
//            dialogStage.showAndWait();
//
////            if (controller.isOkClicked()){
////                EmpresaPrincipal empresaSeleccionada = controller.getEmpresaSeleccionada();
////                loginViewModel.setEmpresa(empresaSeleccionada);
////                ConnectionPool.getInstance().close();
////                ConnectionPool.getInstance().changeDatabase(empresaSeleccionada.getNombreDB());
////            }
//
//        }catch (IOException | SQLException ex){
////            Alerts.showErrorAlert(ex.getMessage());
//        }
    }

    private void handleShowPassword(){
        System.out.println("Checkbox was: " + chkMostrarContrasena.isSelected());
        chkMostrarContrasena.setSelected(!chkMostrarContrasena.isSelected());
        System.out.println("Checkbox now: " + chkMostrarContrasena.isSelected());
    }

    private void handleKeyEvents(KeyEvent event){
        switch (event.getCode()){
            case ENTER -> {
                handleEntrar();
                event.consume();
            }
            case ESCAPE -> {
                handleSalir();
                event.consume();
            }
            case F2 -> {
                System.out.println("F12 pressed");
                handleShowPassword();
                event.consume();
            }
        }
    }

    public void setCrearEmpresasVisible(Boolean hideCrearEmpresas){
        oldLoginViewModel.setCrearEmpresasVisible(hideCrearEmpresas);
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }
}