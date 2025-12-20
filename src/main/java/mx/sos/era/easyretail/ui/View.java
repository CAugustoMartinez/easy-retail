package mx.sos.era.easyretail.ui;

public enum View {
    LOGIN("/mx/sos/era/easyretail/login.fxml"),
    CATALOGO_EMPRESAS("/mx/sos/era/easyretail/company-tiles.fxml"),
    CREAR_EMPRESA("/mx/sos/era/easyretail/crear-empresa.fxml");

    private final String path;

    View(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}