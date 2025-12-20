package mx.sos.era.easyretail.ui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import mx.sos.era.easyretail.master.entity.Empresa;
import mx.sos.era.easyretail.master.service.EmpresaService;

import java.util.List;
import java.util.function.Consumer;

public class CatalogoEmpresasController {

    @FXML private FlowPane flowEmpresas;

    private EmpresaService empresaService;
    private Consumer<Empresa> onEmpresaSeleccionada;
    private Runnable onCrearEmpresa;

    public void setEmpresaService(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    public void setOnEmpresaSeleccionada(Consumer<Empresa> listener) {
        this.onEmpresaSeleccionada = listener;
    }

    public void setOnCrearEmpresa(Runnable listener) {
        this.onCrearEmpresa = listener;
    }

    public void cargarEmpresas() {
        flowEmpresas.getChildren().clear();

        List<Empresa> empresas = empresaService.listar();

        // ✅ Siempre agregar el tile de crear empresa
        flowEmpresas.getChildren().add(crearTileAgregar());

        // ✅ Si no hay empresas, solo mostramos el tile "+"
        if (empresas.isEmpty()) {
            return;
        }

        // ✅ Agregar tiles de empresas
        for (Empresa e : empresas) {
            flowEmpresas.getChildren().add(crearTileEmpresa(e));
        }
    }

    private VBox crearTileAgregar() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getStyleClass().add("tile-add");

        Label plus = new Label("+");
        plus.getStyleClass().add("tile-add-plus");

        Label lbl = new Label("Crear empresa");
        lbl.getStyleClass().add("tile-add-label");

        box.getChildren().addAll(plus, lbl);

        box.setOnMouseClicked(e -> {
            if (onCrearEmpresa != null) {
                onCrearEmpresa.run();
            }
        });

        return box;
    }

    private VBox crearTileEmpresa(Empresa empresa) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getStyleClass().add("empresa-tile");

        ImageView logo = new ImageView();
        logo.setFitWidth(80);
        logo.setFitHeight(80);

        if (empresa.getLogoPath() != null) {
            logo.setImage(new Image(empresa.getLogoPath()));
        }

        Label lbl = new Label(empresa.getNombre());
        lbl.getStyleClass().add("empresa-nombre");

        box.getChildren().addAll(logo, lbl);

        box.setOnMouseClicked(e -> {
            if (onEmpresaSeleccionada != null) {
                onEmpresaSeleccionada.accept(empresa);
            }
        });

        return box;
    }
}