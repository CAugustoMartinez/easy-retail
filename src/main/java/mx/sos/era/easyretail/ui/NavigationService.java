package mx.sos.era.easyretail.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mx.sos.era.easyretail.master.service.AuthService;
import mx.sos.era.easyretail.master.service.EmpresaService;
import mx.sos.era.easyretail.ui.controller.CatalogoEmpresasController;
import mx.sos.era.easyretail.ui.controller.CrearEmpresaController;
import mx.sos.era.easyretail.ui.controller.LoginController;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class NavigationService {

    private final FxWindowManager windowManager;
    private final ApplicationContext ctx;

    // Mantener referencia al catálogo abierto para refrescarlo después de crear empresa
    private CatalogoEmpresasController currentCatalogController;

    public NavigationService(FxWindowManager windowManager, ApplicationContext ctx) {
        this.windowManager = windowManager;
        this.ctx = ctx;
    }

    // Abrir login en el primaryStage (reemplaza la lógica de wiring en MainApp)
    public void openLogin(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(View.LOGIN.getPath()));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setAuthService(ctx.getBean(AuthService.class));

        // Listener: cuando login es exitoso abrimos catálogo
        controller.setLoginListener(usuario -> {
            try {
                openCatalogoEmpresas(new Stage());
                // cerrar la ventana de login
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    // Abrir catálogo de empresas en una nueva Stage
    public void openCatalogoEmpresas(Stage stage) throws Exception {
        // Abrir usando WindowManager (devuelve el controller)
        CatalogoEmpresasController catalogoController = windowManager.open(
                View.CATALOGO_EMPRESAS,
                stage,
                CatalogoEmpresasController.class
        );

        // Inyectar servicio y cargar empresas
        catalogoController.setEmpresaService(ctx.getBean(EmpresaService.class));
        catalogoController.cargarEmpresas();

        // Guardar referencia para refrescar después de crear empresa
        this.currentCatalogController = catalogoController;

        // Conectar el callback del tile "+" para abrir la ventana de crear empresa
        catalogoController.setOnCrearEmpresa(() -> {
            try {
                openCrearEmpresa(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Abrir ventana de crear empresa
    public void openCrearEmpresa(Stage stage) throws Exception {
        CrearEmpresaController crearCtrl = windowManager.open(
                View.CREAR_EMPRESA,
                stage,
                CrearEmpresaController.class
        );

        crearCtrl.setEmpresaService(ctx.getBean(EmpresaService.class));

        // Al guardar, refrescar catálogo si está abierto
        crearCtrl.setOnEmpresaGuardada(() -> {
            if (currentCatalogController != null) {
                currentCatalogController.cargarEmpresas();
            }
        });
    }
}