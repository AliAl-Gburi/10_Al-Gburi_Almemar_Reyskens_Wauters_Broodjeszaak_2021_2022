package view;

import controller.KitchenViewController;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class KitchenView {
	private Button volgendeBestelling;
	private Button bestellingAfgewerkt;
	private Label wachtRij;
	private Label bestellingBeschrijving;
	
	private Stage stage = new Stage();		
	
	public KitchenView(KitchenViewController controller){
		stage.setTitle("KITCHEN VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(680);
		stage.setY(470);
		Pane root = createNodeHierarchy();
		Scene scene = new Scene(root, 650, 200);			
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();
		controller.setView(this);
	}

	private Pane createNodeHierarchy() {
		VBox mainBox = new VBox(8);
		VBox wachtrijBox = new VBox(8);
		HBox bestellingBeschrijvingBox = new HBox(8);
		HBox knopenBox = new HBox(8);

		wachtRij = new Label("Aantal Bestellingen in de wachtrij: 0");
		bestellingBeschrijving = new Label("Er is momenteel geen bestelling in het keuken\n\n\n");
		volgendeBestelling = new Button("Volgende Bestelling");
		volgendeBestelling.setDisable(true);
		bestellingAfgewerkt = new Button("Bestelling afgewerkt");
		bestellingAfgewerkt.setDisable(true);
		wachtrijBox.setPadding(new Insets(0,0,10,0));
		wachtrijBox.getChildren().add(wachtRij);


		knopenBox.getChildren().addAll(volgendeBestelling, bestellingAfgewerkt);
		knopenBox.setPadding(new Insets(110, 0,0,90));
		bestellingBeschrijvingBox.getChildren().addAll(bestellingBeschrijving,knopenBox );


		mainBox.getChildren().addAll(wachtrijBox,bestellingBeschrijvingBox);
		mainBox.setPadding(new Insets(15));

		return mainBox;
	}

	public Label getWachtRij() {
		return this.wachtRij;
	}

	public Label getBestellingBeschrijving() {
		return this.bestellingBeschrijving;
	}

	public Button getVolgendeBestelling() {
		return this.volgendeBestelling;
	}

	public Button getBestellingAfgewerkt() {
		return this.bestellingAfgewerkt;
	}
}
