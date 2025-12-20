package mx.sos.era.easyretail.ui.viewmodel;

import javafx.beans.property.*;
import mx.sos.era.easyretail.master.entity.Empresa;

public class OldLoginViewModel {
    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private StringProperty nombreEmpresa = new SimpleStringProperty();
    private StringProperty usuario = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private BooleanProperty showPassword = new SimpleBooleanProperty();
    private BooleanProperty crearEmpresasVisible = new SimpleBooleanProperty();

    public OldLoginViewModel(){
        empresa.addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                nombreEmpresa.set(newValue.getNombre());
            }else{
                nombreEmpresa.set(oldValue.getNombre());
            }
        });
    }
    public ObjectProperty<Empresa> empresaProperty(){
        return empresa;
    }

    public StringProperty nombreEmpresaProperty(){ return nombreEmpresa; }

    public StringProperty usuarioProperty() {
        return usuario;
    }

    public StringProperty passwordProperty(){
        return password;
    }

    public BooleanProperty showPasswordProperty(){ return showPassword; }

    public BooleanProperty getShowEmpresasProperty(){ return crearEmpresasVisible; }

    public Empresa getEmpresa(){ return empresa.get(); }

    public void setEmpresa(Empresa empresa){
        this.empresa.set(empresa);
    }

    public String getNombreEmpresa(){ return nombreEmpresa.get(); }

    public String getUsuario(){
        return usuario.get();
    }

    public void setUsuario(String usuario){
        this.usuario.set(usuario);
    }

    public String getPassword(){ return password.get(); }

    public void setPassword(String password){
        this.password.set(password);
    }

    public boolean isPasswordShown(){ return showPassword.get(); }

    public void setShowPassword(boolean showPassword){ this.showPassword.set(showPassword); }

    public Boolean isCrearEmpresasVisible(){ return  crearEmpresasVisible.get(); }

    public void setCrearEmpresasVisible(Boolean visible){ this.crearEmpresasVisible.set(visible); }
}