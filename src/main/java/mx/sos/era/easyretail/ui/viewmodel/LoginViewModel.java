package mx.sos.era.easyretail.ui.viewmodel;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Objects;

public class LoginViewModel {
    private final StringProperty user = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final ObjectProperty<Image> eyeIcon = new SimpleObjectProperty<>();
    private final BooleanProperty passwordVisible = new SimpleBooleanProperty(false);
    private final Image eyeOpen = loadImage("/mx/sos/era/easyretail/imgs/ojo.png");
    private final Image eyeClose = loadImage("/mx/sos/era/easyretail/imgs/cerrar-ojo.png");


    public LoginViewModel() {
        passwordVisible.addListener((observable, oldValue, newValue) -> {
            eyeIcon.setValue(newValue ? eyeClose : eyeOpen);
        });
        eyeIcon.setValue(eyeOpen);
    }

    private Image loadImage(String path) {
        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
        }
        return image;
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Image getEyeIcon() {
        return eyeIcon.get();
    }

    public ObjectProperty<Image> eyeIconProperty() {
        return eyeIcon;
    }

    public boolean isPasswordVisible() {
        return passwordVisible.get();
    }

    public void setPasswordVisible(boolean visible) {this.passwordVisible.set(visible);}

    public BooleanProperty passwordVisibleProperty() {
        return passwordVisible;
    }

    public void tooglePasswordVisible(){ passwordVisible.set(!passwordVisible.get()); }
}