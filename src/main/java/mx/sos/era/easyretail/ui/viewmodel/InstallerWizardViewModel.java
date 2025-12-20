package mx.sos.era.easyretail.ui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstallerWizardViewModel {
    private final StringProperty servidor = new SimpleStringProperty("");
    private final StringProperty puerto = new SimpleStringProperty("");
    private final StringProperty baseDatos = new SimpleStringProperty("");
    private final StringProperty usuario = new SimpleStringProperty("");
    private final StringProperty contraseña = new SimpleStringProperty("");

    private final StringProperty sucursal = new SimpleStringProperty("");
    private final StringProperty caja = new SimpleStringProperty("");

    private final StringProperty rutaAplicacion = new SimpleStringProperty("");
    public String getServidor() {
        return servidor.get();
    }

    public StringProperty servidorProperty() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor.set(servidor);
    }

    public String getPuerto() {
        return puerto.get();
    }

    public StringProperty puertoProperty() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto.set(puerto);
    }

    public String getBaseDatos() {
        return baseDatos.get();
    }

    public StringProperty baseDatosProperty() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos.set(baseDatos);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public StringProperty usuarioProperty() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getContraseña() {
        return contraseña.get();
    }

    public StringProperty contraseñaProperty() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña.set(contraseña);
    }

    public String getSucursal() {
        return sucursal.get();
    }

    public StringProperty sucursalProperty() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal.set(sucursal);
    }

    public String getCaja() {
        return caja.get();
    }

    public StringProperty cajaProperty() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja.set(caja);
    }

    public String getRutaAplicacion() {
        return rutaAplicacion.get();
    }

    public StringProperty rutaAplicacionProperty() {
        return rutaAplicacion;
    }

    public void setRutaAplicacion(String rutaAplicacion) {
        this.rutaAplicacion.set(rutaAplicacion);
    }
}