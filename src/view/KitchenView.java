package view;

import controller.KitchenViewController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.control.Label;


public class KitchenView {

    private Stage stage = new Stage();
    private Button volgendeBestelling;
    private Button bestellingAfgewerkt;
    private Label orderDescription;
    private Label queue;


    public KitchenView(KitchenViewController kvc) {
        stage.setTitle("KITCHEN VIEW");
        stage.initStyle(StageStyle.UTILITY);
        stage.setX(680);
        stage.setY(470);
        Pane root = createNodeHierarchyKitchenView();
        Scene scene = new Scene(root, 650, 200);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        kvc.setView(this);
    }

    public Pane createNodeHierarchyKitchenView() {
        Pane mainBox = new VBox(8);
        Pane orderDescriptionBox = new VBox(8);
        Pane queueBox = new VBox(8);
        Pane buttons = new VBox(8);

        orderDescription = new Label(" ");
        queue = new Label("Aantal bestellingen in wachtrij:");

        volgendeBestelling = new Button("Volgende bestelling");
        volgendeBestelling.setDisable(true);

        bestellingAfgewerkt = new Button("Bestelling afwerken");
        bestellingAfgewerkt.setDisable(true);
        buttons.getChildren().addAll(volgendeBestelling, bestellingAfgewerkt);

        queueBox.getChildren().add(queue);

        orderDescriptionBox.getChildren().addAll(orderDescription, buttons);

        mainBox.getChildren().addAll(orderDescriptionBox, queueBox);

        return mainBox;
    }

    public Stage getStage() {
        return stage;
    }

    public Button getVolgendeBestelling() {
        return volgendeBestelling;
    }

    public Button getBestellingAfgewerkt() {
        return bestellingAfgewerkt;
    }

    public Label getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(Label orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Label getQueue() {
        return queue;
    }

    public void setQueue(Label queue) {
        this.queue = queue;
    }
}
