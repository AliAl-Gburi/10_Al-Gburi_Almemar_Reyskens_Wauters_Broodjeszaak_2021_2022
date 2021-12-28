package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Bestellijn;
import model.Bestelling;
import model.Broodje;


import java.awt.*;
import java.util.List;
import java.util.Map;

public class KitchenView {
	
	private Stage stage = new Stage();
	private Button volgendeBestelling;
	private Button bestellingAfgewerken;
	private List<Bestelling> bestellingen;

	
	public KitchenView(){			
		stage.setTitle("KITCHEN VIEW");
		stage.initStyle(StageStyle.UTILITY);
		stage.setX(680);
		stage.setY(470);
		Group root = new Group();
		Scene scene = new Scene(root, 650, 200);			
		stage.setScene(scene);
		stage.sizeToScene();			
		stage.show();		
	}

	public Pane createNodeHierarchyKitchenView() {
		VBox mainBox = new VBox(8);
		volgendeBestelling = new Button("Volgende bestelling");
		volgendeBestelling.setDisable(true);

		bestellingAfgewerken = new Button("Bestelling afwerken");
		bestellingAfgewerken.setDisable(false);

		return mainBox;
	}

	public Stage getStage() {
		return stage;
	}

	public Button getVolgendeBestelling() {
		return volgendeBestelling;
	}

	public Button getBestellingAfgewerken() {
		return bestellingAfgewerken;
	}

	public List<Bestelling> getBestellingen() {
		return bestellingen;
	}
}
