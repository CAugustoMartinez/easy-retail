package mx.sos.era.easyretail.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class IconsUtils {

    public static Image loadImage(String path){
        return new Image(IconsUtils.class.getResourceAsStream(path));
    }

    public static void setIcon(Stage stage){
        Image icon = loadImage("/mx/com/sos/era/imgs/logo.png");
        stage.getIcons().add(icon);
    }

    public static ImageView loadImageView(String path){
        Image image = loadImage(path);
        return new ImageView(image);
    }

}