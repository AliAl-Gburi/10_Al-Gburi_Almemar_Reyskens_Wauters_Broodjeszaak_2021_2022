package view.panels;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SettingsPane extends GridPane {
    private ChoiceBox<String> kortings;
    private ComboBox<String> fileFormat;
    private Button saveBtn;
    private Label saveLabel;

    public SettingsPane() {
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setVgap(5);
        this.setHgap(5);

        VBox mainBox = new VBox(20);
        HBox kortingsBox = new HBox(8);
        HBox fileFormatBox = new HBox(8);
        HBox saveBox = new HBox(8);

        Label kortingslabel = new Label("Default Kortings Strategy: ");
        kortings = new ChoiceBox<>();
        kortings.getItems().addAll("Goedkoopste broodje gratis", "50% aan alle bestellingen");
        kortingsBox.getChildren().addAll(kortingslabel, kortings);

        Label fileFormatLabel = new Label("File format: ");
        fileFormat = new ComboBox<>();
        fileFormat.setPadding(new Insets(0, 0, 0, 10));
        fileFormat.getItems().addAll("text", "excel");
        fileFormatBox.getChildren().addAll(fileFormatLabel, fileFormat);

        saveBtn = new Button("Save");
        saveBtn.setPadding(new Insets(5, 20, 5, 20));
        saveBox.setPadding(new Insets(100, 0, 0, 0));
        saveLabel = new Label("");
        saveBox.getChildren().addAll(saveBtn,saveLabel);


        mainBox.getChildren().addAll(kortingsBox, fileFormatBox, saveBox);
        mainBox.setPadding(new Insets(20));
        this.add(mainBox, 0, 1);

    }



    public ChoiceBox<String> getKortingsList() {
        return this.kortings;
    }

    public ComboBox<String> getFileFormat() {
        return this.fileFormat;
    }

    public Button getSaveBtn() {
        return this.saveBtn;
    }

    public Label getSaveLabel() {
        return this.saveLabel;
    }
}
