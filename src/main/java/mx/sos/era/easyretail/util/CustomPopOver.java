package mx.sos.era.easyretail.util;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.controlsfx.control.PopOver;

public class CustomPopOver {

    public void showPopover(String header, Control parent, ObservableList<String> data){
        PopOver popover = new PopOver();
        Label label = new Label();
        label.setText(header);
        label.setTextFill(Color.rgb(0, 102,0));
        label.setFont(Font.font("System", FontWeight.BOLD, 14.0));
        ListView listView = new ListView();
        listView.getItems().clear();
        listView.setPrefSize(250,150);
        listView.setEditable(false);
        listView.setFocusTraversable(false);
        listView.getItems().addAll(data);
        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(5);
        layout.getChildren().addAll(label,listView);
        popover.setContentNode(layout);
        popover.setHideOnEscape(true);
        popover.show(parent);
    }

}