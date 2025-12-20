package mx.sos.era.easyretail.ui.util;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class FxWindowUtils {

    private FxWindowUtils() { /* utilitario estático */ }

    /**
     * Ajusta tamaño y posición del stage para que quepa en el visualBounds
     * de la pantalla donde está, dejando un margen en píxeles.
     */
    public static void fitStageToVisualBounds(Stage stage, double marginPx) {
        Platform.runLater(() -> {
            // Determinar la pantalla que contiene (o más cercana a) la ventana
            Rectangle2D visual = Screen.getScreensForRectangle(
                            stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()
                    ).stream()
                    .findFirst()
                    .map(Screen::getVisualBounds)
                    .orElse(Screen.getPrimary().getVisualBounds());

            double maxHeight = Math.max(0, visual.getHeight() - marginPx);
            double maxWidth  = Math.max(0, visual.getWidth() - marginPx);

            if (stage.getHeight() > maxHeight) stage.setHeight(maxHeight);
            if (stage.getWidth()  > maxWidth)  stage.setWidth(maxWidth);

            double newX = stage.getX();
            double newY = stage.getY();

            if (newX < visual.getMinX()) newX = visual.getMinX();
            if (newY < visual.getMinY()) newY = visual.getMinY();
            if (newX + stage.getWidth() > visual.getMaxX()) newX = visual.getMaxX() - stage.getWidth();
            if (newY + stage.getHeight() > visual.getMaxY()) newY = visual.getMaxY() - stage.getHeight();

            stage.setX(newX);
            stage.setY(newY);
        });
    }
}