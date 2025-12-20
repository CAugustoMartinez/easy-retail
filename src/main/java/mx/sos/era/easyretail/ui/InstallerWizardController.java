package mx.sos.era.easyretail.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.sos.era.easyretail.config.InstallerConfig;
import mx.sos.era.easyretail.context.model.TenantInfo;
import mx.sos.era.easyretail.util.Alerts;
import mx.sos.era.easyretail.util.CustomPopOver;
import mx.sos.era.easyretail.util.MysqlDataSourceFactory;
import mx.sos.era.easyretail.ui.viewmodel.InstallerWizardViewModel;
import net.synedra.validatorfx.ValidationMessage;
import net.synedra.validatorfx.Validator;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class InstallerWizardController implements Initializable{

    @FXML
    private Button btnProbarCon;
    @FXML
    private Label lblInicial;
    @FXML
    private Label lblServidor;
    @FXML
    private Label lblPuerto;
    @FXML
    private Label lblBaseDatos;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblContrasena;
    @FXML
    private TextField txtServidor;
    @FXML
    private TextField txtPuerto;
    @FXML
    private TextField txtBaseDatos;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private Label lblSucursal;
    @FXML
    private Label lblCaja;
    @FXML
    private Label lblRutaAp;
    @FXML
    private TextField txtSucursal;
    @FXML
    private TextField txtCaja;
    @FXML
    private TextField txtRutaAp;
    @FXML
    private Button btnRutaAp;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;
    private DirectoryChooser dirChooser;
    private InstallerWizardViewModel installerWizardViewModel;
    private Validator validator;
    private Validator validator2;
    private final File defaultDirectory = new File("C:\\Program Files (x86)\\SOS Software\\Easy Retail Admin\\Easy Retail Admin");
    private Stage stage;
    private InstallerConfig result;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //Platform.runLater(() -> txtUsuario.requestFocus());
        dirChooser = new DirectoryChooser();
        //var ivwSave = IconsUtils.loadImageView("/mx/com/sos/era/imgs/save.png");
        //var ivwExit = IconsUtils.loadImageView("/mx/com/sos/era/imgs/sal.png");
        //btnGuardar.setGraphic(ivwSave);
        //btnSalir.setGraphic(ivwExit);
        setBindings();
        addValidations();
        addStyleClasses();
        installerWizardViewModel.setBaseDatos("DBEMPRESAS");
        installerWizardViewModel.setRutaAplicacion(defaultDirectory.getAbsolutePath());
    }

    private void setBindings(){
        installerWizardViewModel = new InstallerWizardViewModel();
        txtServidor.textProperty().bindBidirectional(installerWizardViewModel.servidorProperty());
        txtPuerto.textProperty().bindBidirectional(installerWizardViewModel.puertoProperty());
        txtBaseDatos.textProperty().bindBidirectional(installerWizardViewModel.baseDatosProperty());
        txtUsuario.textProperty().bindBidirectional(installerWizardViewModel.usuarioProperty());
        txtContrasena.textProperty().bindBidirectional(installerWizardViewModel.contraseñaProperty());
        txtSucursal.textProperty().bindBidirectional(installerWizardViewModel.sucursalProperty());
        txtCaja.textProperty().bindBidirectional(installerWizardViewModel.cajaProperty());
        txtRutaAp.textProperty().bindBidirectional(installerWizardViewModel.rutaAplicacionProperty());
    }

    private void addValidations() {
        validator = new Validator();
        validator2 = new Validator();
        validator.createCheck()
                .dependsOn("servidor", txtServidor.textProperty())
                .withMethod(c -> {
                    String servidor = c.get("servidor");
                    if (servidor.isBlank() || servidor.isEmpty()) {
                        c.error("El servidor es requerido.");
                    }
                })
                .decorates(txtServidor)
                .immediate();
        validator.createCheck()
                .dependsOn("puerto", txtPuerto.textProperty())
                .withMethod(c -> {
                    String puerto = c.get("puerto");
                    if (puerto.isBlank() || puerto.isEmpty()) {
                        c.error("El puerto es requerido");
                    } else if (!puerto.matches("\\d+")) {
                        c.error("El puerto debe ser numerico.");
                    }
                })
                .decorates(txtPuerto)
                .immediate();
        validator.createCheck()
                .dependsOn("usuario", txtUsuario.textProperty())
                .withMethod(c -> {
                    String user = c.get("usuario");
                    if (user.isBlank() || user.isEmpty()) {
                        c.error("El usuario es requerido");
                    }
                })
                .decorates(txtUsuario)
                .immediate();
        validator.createCheck()
                .dependsOn("contraseña", txtContrasena.textProperty())
                .withMethod(c -> {
                    String contrasena = c.get("contraseña");
                    if (contrasena.isBlank() || contrasena.isEmpty()) {
                        c.error("La contraseña es requerida");
                    }
                })
                .decorates(txtContrasena)
                .immediate();
        validator2.createCheck()
                .dependsOn("sucursal", txtSucursal.textProperty())
                .withMethod(c -> {
                    String sucursal = c.get("sucursal");
                    if (sucursal.isBlank() || sucursal.isEmpty()) {
                        c.error("La sucursal es requerida");
                    }
                })
                .decorates(txtSucursal)
                .immediate();
        validator2.createCheck()
                .dependsOn("caja", txtCaja.textProperty())
                .withMethod(c -> {
                    String caja = c.get("caja");
                    if (caja.isBlank() || caja.isEmpty()) {
                        c.error("El número de caja es requerido");
                    }
                })
                .decorates(txtCaja)
                .immediate();
        validator2.createCheck()
                .dependsOn("ruta", txtRutaAp.textProperty())
                .withMethod(c -> {
                    String ruta = c.get("ruta");
                    if (ruta.isBlank() || ruta.isEmpty()) {
                        c.error("La ruta de aplicación es requerida");
                    }
                })
                .decorates(txtRutaAp)
                .immediate();
    }

    private void addStyleClasses(){
        btnProbarCon.getStyleClass().add("flat-button");
        btnRutaAp.getStyleClass().add("flat-button");
        btnGuardar.getStyleClass().add("flat-button");
        btnSalir.getStyleClass().add("flat-button");
    }

    @FXML
    private void handleActionTestConection(ActionEvent event) {
        if (validator.containsErrors()){
            var list = FXCollections.observableList(validator.getValidationResult().getMessages());
            ObservableList<String> data = list.stream().map(ValidationMessage::getText)
                    .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
            CustomPopOver popOver = new CustomPopOver();
            popOver.showPopover("Por favor llene los datos de conexión faltantes: ", btnProbarCon, data);
            return;
        }

        TenantInfo temporal = new TenantInfo(
                "TEST",
                installerWizardViewModel.getServidor(),
                Integer.parseInt(installerWizardViewModel.getPuerto()),
                installerWizardViewModel.getBaseDatos(),
                installerWizardViewModel.getUsuario(),
                installerWizardViewModel.getContraseña()
        );

        try{
            DataSource ds = MysqlDataSourceFactory.create(temporal);
            try(Connection conn = ds.getConnection()){
                Alerts.showInformationAlert("Conexión con la base de datos establecida.", "Base de datos");
            }
        }catch(Exception ex){
            Alerts.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void handleActionSelectDirAp(ActionEvent event) {
        try{
            dirChooser.setInitialDirectory(defaultDirectory);
            File selectedDirectory = dirChooser.showDialog(btnRutaAp.getScene().getWindow());

            if (selectedDirectory == null){
                selectedDirectory = defaultDirectory;
                installerWizardViewModel.setRutaAplicacion(selectedDirectory.getAbsolutePath());
            }
        }catch (Exception ex){
            Alerts.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void handleActionSaveSettings(ActionEvent event) {
        var respuesta = Alerts.showConfirmationMessage("Aceptar para guardar", "Configuracion", "¿Estas seguro que la información es correcta?");

        if (respuesta.isEmpty() || respuesta.get() == ButtonType.CANCEL ){
            return;
        }

        if (validator.containsErrors() || validator2.containsErrors()){
            var list1 = FXCollections.observableList(validator.getValidationResult().getMessages());
            var list2 = FXCollections.observableList(validator2.getValidationResult().getMessages());

            var combinedMessages = new ArrayList<ValidationMessage>();
            combinedMessages.addAll(list1);
            combinedMessages.addAll(list2);

            if (!combinedMessages.isEmpty()){
                ObservableList<String> data = combinedMessages.stream().map(ValidationMessage::getText)
                        .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);

                CustomPopOver popOver = new CustomPopOver();
                popOver.showPopover("Por favor llene los datos faltantes: ", btnGuardar, data);
                return;
            }
        }

        result = new InstallerConfig(
            installerWizardViewModel.getServidor(),
            Integer.parseInt(installerWizardViewModel.getPuerto()),
            installerWizardViewModel.getBaseDatos(),
            installerWizardViewModel.getUsuario(),
            installerWizardViewModel.getContraseña(),
            installerWizardViewModel.getRutaAplicacion(),
            installerWizardViewModel.getSucursal(),
            installerWizardViewModel.getCaja()
        );

        Alerts.showInformationAlert("Guardado con éxito.", "Configuración Base Datos.");

        stage.close();
    }

    @FXML
    private void handleActionClose(ActionEvent event) {
        stage.close();
    }

    public void setConfigurationData(Properties properties) {
        installerWizardViewModel.setServidor(properties.getProperty("servidor"));
        installerWizardViewModel.setPuerto(properties.getProperty("puerto"));
        installerWizardViewModel.setBaseDatos(properties.getProperty("baseDatos"));
        installerWizardViewModel.setUsuario(properties.getProperty("usuario"));
        installerWizardViewModel.setContraseña(properties.getProperty("contraseña"));
        installerWizardViewModel.setSucursal(properties.getProperty("sucursal"));
        installerWizardViewModel.setCaja(properties.getProperty("caja"));
        installerWizardViewModel.setRutaAplicacion(properties.getProperty("rutaAplicacion"));
    }

    public InstallerConfig getResult(){
        return result;
    }
}